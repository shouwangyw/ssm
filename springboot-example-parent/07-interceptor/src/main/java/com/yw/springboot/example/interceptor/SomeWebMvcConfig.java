package com.yw.springboot.example.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 表示该文件充当配置文件
 *
 * @author yangwei
 */
@Configuration
public class SomeWebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        SomeInterceptor interceptor = new SomeInterceptor();
        registry.addInterceptor(interceptor)
                // 拦截first开头的路径
                .addPathPatterns("/first/**")
                // 不拦截second开头的路径
                .excludePathPatterns("/second/**");
    }
}