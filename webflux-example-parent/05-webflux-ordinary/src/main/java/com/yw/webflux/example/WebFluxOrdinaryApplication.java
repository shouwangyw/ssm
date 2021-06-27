package com.yw.webflux.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * @author yangwei
 */
@EnableReactiveMongoRepositories    // 开启MongoDB的Spring-data-jpa
@SpringBootApplication
public class WebFluxOrdinaryApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebFluxOrdinaryApplication.class, args);
    }
}
