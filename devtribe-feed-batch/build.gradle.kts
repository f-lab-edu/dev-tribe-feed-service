tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true

}

dependencies {
    // Module
    implementation(project(":devtribe-feed-core"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")

    // DB
    runtimeOnly("com.h2database:h2")

    // Test
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:elasticsearch")

}
