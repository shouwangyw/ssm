package com.yw.springboot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yangwei
 */
@EnableCaching      // 开启缓存
@EnableTransactionManagement    // 开启事务
@SpringBootApplication
public class UseRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(UseRedisApplication.class, args);
    }
}