package com.yw.springboot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author yangwei
 */
@SpringBootApplication
@ServletComponentScan("com.yw.springboot.example.*") // 开启Servlet扫描
public class ServletAnnotationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServletAnnotationApplication.class, args);
    }
}
