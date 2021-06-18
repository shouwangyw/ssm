package com.yw.mybatis.framework.executor;

import com.yw.mybatis.framework.BoundSql;
import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.MappedStatement;
import com.yw.mybatis.framework.handler.StatementHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class SimpleExecutor extends BaseExecutor {
    @Override
    protected List<Object> queryFromDatabase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param) {
        List<Object> results = new ArrayList<>();

        // 获取数据库连接
        try (Connection connection = getConnection(configuration)) {
            // 获取SQL
            String sql = boundSql.getSql();
            System.out.println(sql);
            // 创建Statement
            // StatementHandler用于处理Statement操作(Statement、PreparedStatement、CallableStatement)
            // 通过StatementHandler去屏蔽不同Statement的处理逻辑
            StatementHandler statementHandler = configuration.newStatementHandler(mappedStatement.getStatementType());
            try (Statement statement = statementHandler.prepare(connection, sql)) {
                // 设置参数
                statementHandler.parameterize(param, statement, boundSql);
                // 执行Statement，并处理结果
                results = statementHandler.query(statement, mappedStatement);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return results;
    }

    private Connection getConnection(Configuration configuration) throws Exception {
        DataSource dataSource = configuration.getDataSource();
        return dataSource.getConnection();
    }
}