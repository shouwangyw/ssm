package com.yw.springboot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yangwei
 */
@EnableTransactionManagement    // 开启事务
@SpringBootApplication
public class UseMybatisApplication {
    public static void main(String[] args) {
        SpringApplication.run(UseMybatisApplication.class, args);
    }
}