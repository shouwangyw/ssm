package com.yw.mybatis.framework;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class Configuration {
    private DataSource dataSource;
    private Map<String, MappedStatement> mappedStatements = new HashMap<>();

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
}
