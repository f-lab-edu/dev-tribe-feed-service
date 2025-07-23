tasks.bootJar{
    enabled = false
}

tasks.jar {
    enabled = true

}

dependencies {
    // Feed Core
    implementation(project(mapOf("path" to ":devtribe-feed-core")))

    // Spring
    implementation ("org.springframework.boot:spring-boot-starter-batch")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    // Test
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

}
