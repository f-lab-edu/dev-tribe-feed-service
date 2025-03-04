package com.devtribe.devtribe_feed_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DevtribeFeedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevtribeFeedServiceApplication.class, args);
	}

}
