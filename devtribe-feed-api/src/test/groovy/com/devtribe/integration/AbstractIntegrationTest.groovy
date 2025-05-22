package com.devtribe.integration


import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@Testcontainers
abstract class AbstractIntegrationTest extends Specification {

    static final MYSQL_IMAGE = "mysql:8.0"

    static final container = new MySQLContainer(MYSQL_IMAGE)
            .withDatabaseName("devtribe-feed")

    static {
        container.start()
    }

    @DynamicPropertySource
    static void setDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl)
        registry.add("spring.datasource.username", container::getUsername)
        registry.add("spring.datasource.password", container::getPassword)
    }
}
