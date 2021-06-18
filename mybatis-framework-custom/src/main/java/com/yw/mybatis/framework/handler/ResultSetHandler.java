package com.yw.mybatis.framework.handler;

import com.yw.mybatis.framework.MappedStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author yangwei
 */
public interface ResultSetHandler {
    List<Object> handleResultSet(PreparedStatement ps, MappedStatement mappedStatement, ResultSet rs) throws Exception;
}