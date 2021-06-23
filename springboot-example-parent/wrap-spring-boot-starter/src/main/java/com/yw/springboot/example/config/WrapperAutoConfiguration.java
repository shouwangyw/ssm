package com.yw.springboot.example.config;

import com.yw.springboot.example.service.Wrapper;
import com.yw.springboot.example.service.WrapperProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author yangwei
 */
@Configuration
// 自由当前路径下存在SomeService类时，才会启用当前的JavaConfig配置类
@ConditionalOnClass(Wrapper.class)
// 指定配置文件中指定属性的封装类
@EnableConfigurationProperties(WrapperProperties.class)
public class WrapperAutoConfiguration {
    @Resource
    private WrapperProperties properties;

    /**
     * 以下两个方法的顺序不能颠倒
     */
    @Bean
    @ConditionalOnProperty(name = "wrapper.service.enable", havingValue = "true", matchIfMissing = true)
    public Wrapper wrapper() {
        return new Wrapper(properties.getPrefix(), properties.getSuffix());
    }

    @Bean
    @ConditionalOnMissingBean
    public Wrapper defaultWrapper() {
        return new Wrapper("", "");
    }
}
