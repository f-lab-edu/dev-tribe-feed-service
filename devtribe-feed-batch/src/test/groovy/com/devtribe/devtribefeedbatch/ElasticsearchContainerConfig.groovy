package com.devtribe.devtribefeedbatch

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy
import org.testcontainers.elasticsearch.ElasticsearchContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Subject

@Testcontainers
@TestConfiguration
@DataElasticsearchTest
class ElasticsearchContainerConfig {

    private static final String DOCKER_IMAGE = "docker.elastic.co/elasticsearch/elasticsearch:8.7.1"

    @Subject.Container
    static ElasticsearchContainer container = new ElasticsearchContainer(
            DockerImageName.parse(DOCKER_IMAGE))
            .withEnv("discovery.type", "single-node")
            .withEnv("xpack.security.enabled", "false")
            .withEnv("xpack.security.http.ssl.enabled", "false")
            .withExposedPorts(9200)
            .waitingFor(new HttpWaitStrategy()
                    .forPort(9200)
                    .forPath("/")
                    .forStatusCodeMatching(status -> status >= 200 && status < 300))

    @DynamicPropertySource
    static void overrideElasticsearchProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.elasticsearch.uris", () -> container.getHttpHostAddress())
    }

    static {
        container.start()
    }

    @Bean
    RestClient restClient() {
        return RestClient.builder(HttpHost.create(container.getHttpHostAddress())).build();
    }

}
