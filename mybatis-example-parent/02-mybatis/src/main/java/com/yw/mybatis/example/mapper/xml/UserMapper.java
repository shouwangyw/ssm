package com.yw.mybatis.example.mapper.xml;

import com.yw.mybatis.example.po.User;

/**
 * @author yangwei
 */
public interface UserMapper {
    User findUserById(int id);
}
