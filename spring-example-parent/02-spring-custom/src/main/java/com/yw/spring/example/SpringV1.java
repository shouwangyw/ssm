package com.yw.spring.example;

import com.yw.spring.example.ioc.BeanFactory;
import com.yw.spring.example.service.UserService;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用面向过程思维和配置文件的方式去实现容器化管理Bean
 *
 * @author yangwei
 */
public class SpringV1 {

    private BeanFactory beanFactory = new BeanFactory();

    @Test
    public void test() {
        // A程序员其实只想使用业务对象去调用对应的服务
        // B程序员编写了一段代码给A程序员提供对象
//        UserService userService = getUserService();
        UserService userService = (UserService) beanFactory.getBean("userService");

        Map<String, Object> param = new HashMap<>(2);
        param.put("username", "张三");
        System.out.println(userService.queryUsers(param));
    }

//    private UserService getUserService() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://192.168.254.128/ssm?characterEncoding=utf-8");
//        dataSource.setUsername("root");
//        dataSource.setPassword("123456");
//
//        UserDaoImpl userDao = new UserDaoImpl();
//        userDao.setDataSource(dataSource);
//
//        UserServiceImpl userService = new UserServiceImpl();
//        userService.setUserDao(userDao);
//
//        return userService;
//    }
//
//    // C程序员
//    public Object getBean(String beanName) {
//        if ("userService".equals(beanName)) {
//            BasicDataSource dataSource = new BasicDataSource();
//            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//            dataSource.setUrl("jdbc:mysql://192.168.254.128/ssm?characterEncoding=utf-8");
//            dataSource.setUsername("root");
//            dataSource.setPassword("123456");
//
//            UserDaoImpl userDao = new UserDaoImpl();
//            userDao.setDataSource(dataSource);
//
//            UserServiceImpl userService = new UserServiceImpl();
//            userService.setUserDao(userDao);
//
//            return userService;
//        } // ...
//        return null;
//    }
}
