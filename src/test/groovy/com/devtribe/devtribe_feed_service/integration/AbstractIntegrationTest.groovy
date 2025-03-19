package com.devtribe.devtribe_feed_service.integration


import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

@ActiveProfiles("integration-test")
class AbstractIntegrationTest extends Specification {

    static mysql = new GenericContainer<>(DockerImageName.parse("mysql:8.0.26"))
            .withEnv("MYSQL_DATABASE", "devtribe")
            .withEnv("MYSQL_ROOT_PASSWORD", "root")
            .withExposedPorts(3306)

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        mysql.start()
        registry.add("spring.datasource.url", {
            "jdbc:mysql://${mysql.host}:${mysql.getMappedPort(3306)}/testdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
        })
        registry.add("spring.datasource.username", { "root" })
        registry.add("spring.datasource.password", { "root" })
    }
}
