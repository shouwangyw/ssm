package com.yw.springboot.example.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 该类的对象是由系统自动创建，所以无需将其将给 Spring 容器管理
 * @author yangwei
 */
@Data
@ConfigurationProperties("wrapper.service")
public class WrapperProperties {
    /**
     * 对应配置文件中的wrapper.service.prefix属性
     */
    private String prefix;
    /**
     * 对应配置文件中的wrapper.service.suffix属性
     */
    private String suffix;
}
