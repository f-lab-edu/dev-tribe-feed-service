package com.devtribe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DevtribeRecommendBatchApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DevtribeRecommendBatchApplication.class, args);
		int exitCode = SpringApplication.exit(context);
		System.exit(exitCode);
	}

}
