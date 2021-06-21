package com.yw.springboot.example.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 自定义属性对象
 *
 * @author yangwei
 */
@Component
@PropertySource(value = "classpath:custom.properties", encoding = "utf8")
@ConfigurationProperties("student")
@Data
public class StudentDto {
    private String name;
    private int age;
    private double score;
}