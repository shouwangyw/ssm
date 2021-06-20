package com.yw.spring.example;

import com.yw.spring.example.dao.UserDaoImpl;
import com.yw.spring.example.service.UserServiceImpl;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class SpringV0 {
    // A程序员其实只想使用业务对象去调用对应的服务
    // 但是现在A程序员还需要进行业务对象的构建
    // A程序员也不了解业务对象的构造细节
    @Test
    public void test() {
        // A程序员其实只想使用业务对象去调用对应的服务
        // 但是现在A程序员还需要进行业务对象的构建
        // A程序员也不了解业务对象的构造细节

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.254.128/ssm?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setDataSource(dataSource);

        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao);

        Map<String, Object> param = new HashMap<>(2);
        param.put("username", "张三");
        System.out.println(userService.queryUsers(param));
    }
}
