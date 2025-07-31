dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-data-redis")
    api("org.springframework.boot:spring-boot-starter-data-elasticsearch")

    implementation("com.fasterxml.jackson.core:jackson-annotations")
    runtimeOnly("com.mysql:mysql-connector-j")
}
