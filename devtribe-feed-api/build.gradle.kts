dependencies {
    implementation(project(mapOf("path" to ":devtribe-feed-core")))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
    implementation("org.testcontainers:mysql")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    implementation("com.google.guava:guava:33.4.0-jre")
    implementation("io.micrometer:micrometer-registry-prometheus")

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:spock")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
