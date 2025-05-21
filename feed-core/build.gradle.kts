val queryDslVersion = "5.0.0"

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")

    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")

    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    runtimeOnly("com.mysql:mysql-connector-j")
}
