package com.yw.mybatis.framework.sqlnode;

import com.yw.mybatis.framework.DynamicContext;

import java.util.List;

/**
 * 封装了一组SqlNode
 *
 * @author yangwei
 */
public class MixedSqlNode implements SqlNode {
    private List<SqlNode> sqlNodes;

    public MixedSqlNode(List<SqlNode> sqlNodes) {
        this.sqlNodes = sqlNodes;
    }

    @Override
    public void apply(DynamicContext context) {
        for (SqlNode sqlNode : sqlNodes) {
            sqlNode.apply(context);
        }
    }
}