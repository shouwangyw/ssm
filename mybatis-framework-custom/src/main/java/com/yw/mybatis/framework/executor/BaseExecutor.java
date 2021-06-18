package com.yw.mybatis.framework.executor;

import com.yw.mybatis.framework.BoundSql;
import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.MappedStatement;
import com.yw.mybatis.framework.sqlsource.SqlSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SimpleExecutor、ReuseExecutor、BatchExecutor这三个类都会去走该类的一级缓存处理流程
 *
 * @author yangwei
 */
public abstract class BaseExecutor implements Executor {
    /**
     * 一级缓存
     */
    private Map<String, List<Object>> oneLevelCache = new HashMap<>(128);

    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object param) {
        // 获取缓存key
        SqlSource sqlSource = mappedStatement.getSqlSource();
        BoundSql boundSql = sqlSource.getBoundSql(param);
        String cacheKey = getCacheKey(boundSql);
        List<Object> list = oneLevelCache.get(cacheKey);

        if (list != null) {
            return (List<T>) list;

        }
        list = queryFromDatabase(configuration, mappedStatement, boundSql, param);

        oneLevelCache.put(cacheKey, list);
        return (List<T>) list;
    }

    protected abstract List<Object> queryFromDatabase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param);

    protected String getCacheKey(BoundSql boundSql) {
        //TODO cacheKey要做特殊处理
        return boundSql.getSql();
    }
}