dependencies {
    implementation(project(mapOf("path" to ":devtribe-feed-core")))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
}
