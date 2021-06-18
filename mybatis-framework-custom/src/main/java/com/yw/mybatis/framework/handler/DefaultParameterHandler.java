package com.yw.mybatis.framework.handler;

import com.yw.mybatis.framework.BoundSql;
import com.yw.mybatis.framework.ParameterMapping;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author yangwei
 */
public class DefaultParameterHandler implements ParameterHandler {
    @Override
    public void setParameters(Object param, PreparedStatement ps, BoundSql boundSql) throws SQLException {
        if (param instanceof Integer || param instanceof String) {
            ps.setObject(1, param);
        } else if (param instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) param;
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                ps.setObject(i + 1, map.get(parameterMapping.getName()));
            }
        } else {
            // TODO
        }
    }
}