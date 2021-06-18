package com.yw.mybatis.framework;

import com.yw.mybatis.framework.executor.CachingExecutor;
import com.yw.mybatis.framework.executor.Executor;
import com.yw.mybatis.framework.executor.SimpleExecutor;
import com.yw.mybatis.framework.handler.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class Configuration {
    private DataSource dataSource;
    private Map<String, MappedStatement> mappedStatements = new HashMap<>();
    private boolean useCache = false;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public MappedStatement getMappedStatementById(String statementId) {
        return mappedStatements.get(statementId);
    }

    public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
        this.mappedStatements.put(statementId, mappedStatement);
    }

    public Executor newExecutor(String type) {
        type = (type == null || "".equals(type)) ? "simple" : type;

        Executor executor = null;
        if ("simple".equals(type)) {
            executor = new SimpleExecutor();
        } // ...
        if (useCache) {
            executor = new CachingExecutor(executor);
        }

        return executor;
    }

    public StatementHandler newStatementHandler(String statementType) {
        statementType = (statementType == null || "".equals(statementType)) ? "prepare" : statementType;
        // RoutingStatementHandler
        return new RoutingStatementHandler(statementType, this);
    }

    public ParameterHandler newParameterHandler() {
        return new DefaultParameterHandler();
    }

    public ResultSetHandler newResultSetHandler() {
        return new DefaultResultSetHandler();
    }
}
