package com.yw.mybatis.framework.sqlsource;

import com.yw.mybatis.framework.BoundSql;
import com.yw.mybatis.framework.ParameterMapping;

import java.util.List;

/**
 * 静态的SqlSource
 *
 * @author yangwei
 */
public class StaticSqlSource implements SqlSource {
    private String sql;
    private List<ParameterMapping> parameterMappings;

    public StaticSqlSource(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return new BoundSql(sql, parameterMappings);
    }
}