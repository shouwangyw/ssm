package com.yw.spring.example.dao;

import com.yw.spring.example.po.User;

import java.util.List;
import java.util.Map;

/**
 * @author yangwei
 */
public interface UserDao {
    List<User> queryUserList(Map<String, Object> param);
}