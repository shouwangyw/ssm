package com.yw.mybatis.framework.handler;

import com.yw.mybatis.framework.BoundSql;
import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.MappedStatement;

import java.sql.*;
import java.util.List;

/**
 * @author yangwei
 */
public class PreparedStatementHandler implements StatementHandler {
    private ParameterHandler parameterHandler;
    private ResultSetHandler resultSetHandler;

    public PreparedStatementHandler(Configuration configuration) {
        parameterHandler = configuration.newParameterHandler();
        resultSetHandler = configuration.newResultSetHandler();
    }

    @Override
    public Statement prepare(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    @Override
    public void parameterize(Object param, Statement statement, BoundSql boundSql) throws SQLException {
        PreparedStatement ps = (PreparedStatement) statement;

        parameterHandler.setParameters(param, ps, boundSql);
    }

    @Override
    public List<Object> query(Statement statement, MappedStatement mappedStatement) throws Exception {
        PreparedStatement ps = (PreparedStatement) statement;
        ResultSet rs = ps.executeQuery();

        return resultSetHandler.handleResultSet(ps, mappedStatement, rs);
    }
}