package com.yw.mybatis.framework;

import com.yw.mybatis.framework.sqlsource.SqlSource;

/**
 * @author yangwei
 */
public class MappedStatement {
    private String statementId;
    private String statementType;
    private String resultType;
    private Class<?> resultTypeClass;
    private SqlSource sqlSource;

    public MappedStatement(String statementId, String statementType, String resultType, Class<?> resultTypeClass, SqlSource sqlSource) {
        this.statementId = statementId;
        this.statementType = statementType;
        this.resultType = resultType;
        this.resultTypeClass = resultTypeClass;
        this.sqlSource = sqlSource;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getStatementType() {
        return statementType;
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Class<?> getResultTypeClass() {
        return resultTypeClass;
    }

    public void setResultTypeClass(Class<?> resultTypeClass) {
        this.resultTypeClass = resultTypeClass;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }
}
