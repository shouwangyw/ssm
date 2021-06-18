package com.yw.mybatis.framework.handler;

import com.yw.mybatis.framework.BoundSql;
import com.yw.mybatis.framework.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author yangwei
 */
public interface StatementHandler {
    Statement prepare(Connection connection, String sql) throws SQLException;

    void parameterize(Object param, Statement statement, BoundSql boundSql) throws SQLException;

    List<Object> query(Statement statement, MappedStatement mappedStatement) throws Exception;
}