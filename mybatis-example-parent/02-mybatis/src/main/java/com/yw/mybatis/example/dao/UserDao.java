package com.yw.mybatis.example.dao;

import com.yw.mybatis.example.po.User;

/**
 * @author yangwei
 */
public interface UserDao {
    User findUserById(int id);
}
