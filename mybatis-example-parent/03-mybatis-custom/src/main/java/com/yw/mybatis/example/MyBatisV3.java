package com.yw.mybatis.example;

import com.yw.mybatis.framework.builder.SqlSessionFactoryBuilder;
import com.yw.mybatis.framework.factory.SqlSessionFactory;
import com.yw.mybatis.framework.sqlsession.SqlSession;
import com.yw.mybatis.framework.util.Resources;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义MyBatis框架V3版本
 * 1、以面向对象的思维去改造mybatis手写框架
 * 2、将手写的mybatis的代码封装成一个通用的框架（java工程）给程序员使用
 *
 * @author yangwei
 */
public class MyBatisV3 {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() {
        String location = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(location);
        // 创建SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test() {
        Map<String, Object> param = new HashMap<>(2);
        param.put("username", "张三");
        param.put("sex", "男");

        SqlSession sqlSession = sqlSessionFactory.openSession();
        System.out.println(sqlSession.selectList("test.selectUserByCriteria", param));
    }
}
