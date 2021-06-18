package com.yw.mybatis.framework.handler;

import com.yw.mybatis.framework.MappedStatement;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class DefaultResultSetHandler implements ResultSetHandler {
    @Override
    public List<Object> handleResultSet(PreparedStatement ps, MappedStatement mappedStatement, ResultSet rs) throws Exception {
        List<Object> results = new ArrayList<>();
        Class<?> resultTypeClass = mappedStatement.getResultTypeClass();
        while (rs.next()) {
            Object result = resultTypeClass.newInstance();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i + 1);
                Field field = resultTypeClass.getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(result, rs.getObject(columnName));
            }
            results.add(result);
        }
        return results;
    }
}