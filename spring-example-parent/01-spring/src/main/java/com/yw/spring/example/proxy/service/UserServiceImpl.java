package com.yw.spring.example.proxy.service;

/**
 * 目标对象
 *
 * @author yangwei
 */
public class UserServiceImpl implements UserService {
    @Override
    public void saveUser() {
        System.out.println("添加用户");
    }
}