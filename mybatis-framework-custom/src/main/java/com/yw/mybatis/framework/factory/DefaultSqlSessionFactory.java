package com.yw.mybatis.framework.factory;

import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.sqlsession.DefaultSqlSession;
import com.yw.mybatis.framework.sqlsession.SqlSession;

/**
 * @author yangwei
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}