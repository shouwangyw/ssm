package com.yw.mybatis.framework.factory;

import com.yw.mybatis.framework.sqlsession.SqlSession;

/**
 * @author yangwei
 */
public interface SqlSessionFactory {
    SqlSession openSession();
}