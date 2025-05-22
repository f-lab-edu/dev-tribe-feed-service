dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(platform("org.testcontainers:testcontainers-bom:1.20.6"))
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    runtimeOnly("com.mysql:mysql-connector-j")
}
