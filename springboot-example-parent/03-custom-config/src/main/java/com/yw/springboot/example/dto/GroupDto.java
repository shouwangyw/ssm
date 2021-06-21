package com.yw.springboot.example.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yangwei
 */
@Component
@PropertySource(value = "classpath:custom.properties", encoding = "utf8")
@ConfigurationProperties("group")
@Data
public class GroupDto {
    private List<User> users;
}
