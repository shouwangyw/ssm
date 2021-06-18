package com.yw.mybatis.framework.handler;

import com.yw.mybatis.framework.BoundSql;
import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author yangwei
 */
public class RoutingStatementHandler implements StatementHandler {
    private StatementHandler statementHandler;

    public RoutingStatementHandler(String statementType, Configuration configuration) {
        if ("prepared".equals(statementType)) {
            statementHandler = new PreparedStatementHandler(configuration);
        }
    }

    @Override
    public Statement prepare(Connection connection, String sql) throws SQLException {
        return statementHandler.prepare(connection, sql);
    }

    @Override
    public void parameterize(Object param, Statement statement, BoundSql boundSql) throws SQLException {
        statementHandler.parameterize(param, statement, boundSql);
    }

    @Override
    public List<Object> query(Statement statement, MappedStatement mappedStatement) throws Exception {
        return statementHandler.query(statement, mappedStatement);
    }
}