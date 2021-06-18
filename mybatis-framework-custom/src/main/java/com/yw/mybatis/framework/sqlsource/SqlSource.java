package com.yw.mybatis.framework.sqlsource;

import com.yw.mybatis.framework.BoundSql;

/**
 * @author yangwei
 */
public interface SqlSource {
    /**
     * 需要对SQL文本进行解析
     */
    BoundSql getBoundSql(Object param);
}
