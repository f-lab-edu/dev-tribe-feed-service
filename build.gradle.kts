plugins {
    id("java")
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.4"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

ext["springCloudVersion"] = "2024.0.0"
ext["queryDslVersion"] = "5.0.0"

allprojects {

    group = "com.devtribe"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {

    apply {
        plugin("java")
        plugin("groovy")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("java-library")
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    dependencies {

        implementation("com.querydsl:querydsl-jpa:${property("queryDslVersion")}:jakarta")
        annotationProcessor("com.querydsl:querydsl-apt:${property("queryDslVersion")}:jakarta")
        annotationProcessor("jakarta.annotation:jakarta.annotation-api")
        annotationProcessor("jakarta.persistence:jakarta.persistence-api")

        // Lombok
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        // Test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation(platform("org.testcontainers:testcontainers-bom:1.20.6"))
        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("org.assertj:assertj-core:3.26.3")
        testImplementation("org.spockframework:spock-core:2.4-M4-groovy-4.0")
        testImplementation("org.spockframework:spock-spring:2.4-M4-groovy-4.0")
        testImplementation("org.testcontainers:spock")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    tasks.test {
        useJUnitPlatform()
    }
}