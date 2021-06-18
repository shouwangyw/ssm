package com.yw.mybatis.framework.sqlnode;

import com.yw.mybatis.framework.DynamicContext;

/**
 * 封装了不带有${}的 SQL文本
 *
 * @author yangwei
 */
public class StaticTextSqlNode implements SqlNode {
    private String sqlText;

    public StaticTextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    @Override
    public void apply(DynamicContext context) {
        context.appendSql(sqlText);
    }
}