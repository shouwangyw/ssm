package com.yw.mybatis.framework.sqlsession;

import java.util.List;

/**
 * @author yangwei
 */
public interface SqlSession {
    <T> T selectOne(String statementId, Object param);

    <T> List<T> selectList(String statementId, Object param);
}