package com.yw.springboot.example.config;

import com.yw.springboot.example.filter.SomeFilter;
import com.yw.springboot.example.servlet.SomeServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 表示其为配置类，相当于applicationContext.xml文件
 *
 * @author yangwei
 */
@Configuration
public class MyApplicationContext {
    /**
     * 表示该方法返回的对象即为Spring容器中的Bean，方法名随意
     */
    @Bean
    public ServletRegistrationBean<SomeServlet> getServletBean() {
        // 创建Servlet
        SomeServlet servlet = new SomeServlet();
        // 注册Servlet
        return new ServletRegistrationBean<>(servlet, "/some");
    }

    @Bean
    public FilterRegistrationBean<SomeFilter> getFilterBean() {
        // 创建Filter
        SomeFilter filter = new SomeFilter();
        // 创建注册对象
        FilterRegistrationBean<SomeFilter> registrationBean = new FilterRegistrationBean<>(filter);
        // 添加过滤条件
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}