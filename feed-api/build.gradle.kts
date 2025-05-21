dependencies {
    implementation(project(mapOf("path" to ":feed-core")))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation(platform("org.testcontainers:testcontainers-bom:1.20.6"))
    implementation("org.testcontainers:mysql")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:spock")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}
