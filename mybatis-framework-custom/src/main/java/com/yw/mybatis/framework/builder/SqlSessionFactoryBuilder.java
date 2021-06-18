package com.yw.mybatis.framework.builder;

import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.factory.DefaultSqlSessionFactory;
import com.yw.mybatis.framework.factory.SqlSessionFactory;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author yangwei
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream inputStream) {
        // 获取Configuration对象
        XMLConfigBuilder configBuilder = new XMLConfigBuilder();

        Configuration configuration = configBuilder.parse(inputStream);
        // 创建SqlSessionFactory对象
        return build(configuration);
    }

    public SqlSessionFactory build(Reader reader) {
        return null;
    }

    private SqlSessionFactory build(Configuration configuration) {
        return new DefaultSqlSessionFactory(configuration);
    }
}