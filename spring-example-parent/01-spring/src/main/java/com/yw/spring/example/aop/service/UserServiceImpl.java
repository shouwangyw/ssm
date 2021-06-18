package com.yw.spring.example.aop.service;

import org.springframework.stereotype.Service;

/**
 * AOP的目标对象
 *
 * @author yangwei
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void saveUser() {
        System.out.println("添加用户");
    }

    @Override
    public void saveUser(String name) {
        System.out.println("添加用户name：" + name);
    }

    @Override
    public void updateUser() {
        System.out.println("修改用户");
    }
}