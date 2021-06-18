package com.yw.mybatis.framework.sqlsession;

import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.MappedStatement;
import com.yw.mybatis.framework.executor.Executor;

import java.util.List;

/**
 * @author yangwei
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statementId, Object param) {
        List<Object> list = this.selectList(statementId, param);
        if (list != null && list.size() >= 1) {
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object param) {
        MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);

        // Executor有多种类型：SimpleExecutor、ReuseExecutor、BatchExecutor
        // 可以通过全局配置文件的settings去指定（该信息存储到Configuration对象中）
        // 可以通过创建SqlSession时指定
        Executor executor = configuration.newExecutor(null);

        return executor.query(configuration, mappedStatement, param);
    }
}