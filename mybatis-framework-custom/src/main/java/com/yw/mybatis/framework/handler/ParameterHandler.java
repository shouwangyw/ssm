package com.yw.mybatis.framework.handler;

import com.yw.mybatis.framework.BoundSql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author yangwei
 */
public interface ParameterHandler {
    void setParameters(Object param, PreparedStatement ps, BoundSql boundSql) throws SQLException;
}