package com.devtribe.devtribefeedbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(value = {"com.devtribe.domain.post"})
public class DevtribeFeedBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevtribeFeedBatchApplication.class, args);
    }

}
