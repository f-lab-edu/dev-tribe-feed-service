dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-data-redis")
    implementation(platform("org.testcontainers:testcontainers-bom:1.20.6"))
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    runtimeOnly("com.mysql:mysql-connector-j")
}
