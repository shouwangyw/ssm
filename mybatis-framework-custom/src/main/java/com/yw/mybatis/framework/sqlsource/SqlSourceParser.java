package com.yw.mybatis.framework.sqlsource;

import com.yw.mybatis.framework.util.GenericTokenParser;
import com.yw.mybatis.framework.util.ParameterMappingTokenHandler;

/**
 * @author yangwei
 */
public class SqlSourceParser {
    public SqlSource parse(String sqlText) {
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        String sql = tokenParser.parse(sqlText);

        return new StaticSqlSource(sql, tokenHandler.getParameterMappings());
    }
}