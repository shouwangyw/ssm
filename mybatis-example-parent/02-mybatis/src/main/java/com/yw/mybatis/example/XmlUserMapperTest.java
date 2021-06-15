package com.yw.mybatis.example;

import com.yw.mybatis.example.mapper.xml.UserMapper;
import com.yw.mybatis.example.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author yangwei
 */
public class XmlUserMapperTest {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() {
        try {
            // 指定全局配置文件路径
            String resource = "SqlMapConfig.xml";
            // 加载资源文件（全局配置文件和映射文件）
            InputStream inputStream = Resources.getResourceAsStream(resource);
            // 用构建者模式，去创建SqlSessionFactory对象
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindUserById() throws Exception {
        // 构造UserMapper对象（sqlSession）
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 需要传的参数就是被代理的Mapper接口
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 调用UserMapper对象的findUserById
        User user = userMapper.findUserById(1);
        System.out.println(user);
    }
}