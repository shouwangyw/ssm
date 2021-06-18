package com.yw.mybatis.framework.executor;

import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.MappedStatement;

import java.util.List;

/**
 * @author yangwei
 */
public interface Executor {
    <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object param);
}