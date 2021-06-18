package com.yw.spring.example.aop.service;

/**
 * @author yangwei
 */
public interface UserService {
    void saveUser();

    void saveUser(String name);

    void updateUser();
}
