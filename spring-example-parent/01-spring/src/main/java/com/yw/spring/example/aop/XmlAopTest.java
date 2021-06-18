package com.yw.spring.example.aop;

import com.yw.spring.example.aop.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yangwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-aop.xml")
public class XmlAopTest {
    @Autowired
    private UserService userService;

    @Test
    public void test() {
        userService.saveUser();
        System.out.println("===============");
        userService.saveUser("balala");
        System.out.println("===============");
        userService.updateUser();
    }
}
