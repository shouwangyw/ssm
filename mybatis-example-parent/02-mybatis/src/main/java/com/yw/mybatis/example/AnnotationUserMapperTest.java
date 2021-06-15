package com.yw.mybatis.example;

import com.yw.mybatis.example.mapper.annotation.AnnotationUserMapper;
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
public class AnnotationUserMapperTest {
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
    public void testFindUserById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AnnotationUserMapper AnnotationUserMapper = sqlSession.getMapper(AnnotationUserMapper.class);
        User user = AnnotationUserMapper.findUserById(1);
        System.out.println(user);
    }
}