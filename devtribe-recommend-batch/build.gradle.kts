dependencies {
    implementation(project(mapOf("path" to ":devtribe-recommend-core")))
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.batch:spring-batch-test")

    runtimeOnly("com.mysql:mysql-connector-j")
}
