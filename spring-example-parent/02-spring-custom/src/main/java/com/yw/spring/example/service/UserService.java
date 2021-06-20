package com.yw.spring.example.service;

import com.yw.spring.example.po.User;

import java.util.List;
import java.util.Map;

/**
 * @author yangwei
 */
public interface UserService {
    List<User> queryUsers(Map<String, Object> param);
}
