package com.yw.mybatis.framework.executor;

import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.MappedStatement;

import java.util.List;

/**
 * @author yangwei
 */
public class CachingExecutor implements Executor {
    private Executor executor;

    public CachingExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object param) {
        // 先获取二级缓存对象

        // 二级缓存没有，则继续调用执行器的逻辑
        return executor.query(configuration, mappedStatement, param);
    }
}