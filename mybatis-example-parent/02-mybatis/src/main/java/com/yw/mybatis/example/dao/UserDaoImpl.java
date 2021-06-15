package com.yw.mybatis.example.dao;

import com.yw.mybatis.example.po.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author yangwei
 */
public class UserDaoImpl implements UserDao {
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 注入sqlSessionFactory
     *
     * @param sqlSessionFactory
     */
    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public User findUserById(int id) {
        // sqlSessionFactory工厂类去创建sqlSession会话
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // sqlSession接口，开发人员使用它对数据库进行增删改查操作
        return sqlSession.selectOne("test.findUserById", id);
    }
}