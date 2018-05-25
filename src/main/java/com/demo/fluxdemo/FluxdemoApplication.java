package com.demo.fluxdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class FluxdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FluxdemoApplication.class, args);
    }
}
