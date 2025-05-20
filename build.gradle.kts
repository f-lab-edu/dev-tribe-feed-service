plugins {
	id("java")
	id("org.springframework.boot") version "3.4.2"
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

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
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")

		implementation("com.google.guava:guava:33.4.0-jre")

		testImplementation("org.assertj:assertj-core:3.26.3")
		testImplementation(platform("org.junit:junit-bom:5.10.0"))
		testImplementation("org.junit.jupiter:junit-jupiter")
		testImplementation("org.spockframework:spock-core:2.4-M4-groovy-4.0")
		testImplementation("org.spockframework:spock-spring:2.4-M4-groovy-4.0")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
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