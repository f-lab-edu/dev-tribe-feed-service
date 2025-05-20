plugins {
    id("java")
    id("io.spring.dependency-management") version "1.1.7"
    id("java-library")
}

group = "com.devtribe"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}