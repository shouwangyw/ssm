package com.yw.mybatis.framework.executor;

import com.yw.mybatis.framework.BoundSql;
import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.MappedStatement;

import java.util.List;

/**
 * @author yangwei
 */
public class BatchExecutor extends BaseExecutor {

    @Override
    protected List<Object> queryFromDatabase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param) {
        return null;
    }
}