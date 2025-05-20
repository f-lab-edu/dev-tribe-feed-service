dependencies {
    implementation(project(mapOf("path" to ":feed-common")))

    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.testcontainers:spock")
}
