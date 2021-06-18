package com.yw.mybatis.framework.sqlsource;

import com.yw.mybatis.framework.BoundSql;
import com.yw.mybatis.framework.DynamicContext;
import com.yw.mybatis.framework.sqlnode.SqlNode;

/**
 * 封装了${}和动态标签的SQL信息，并提供对他们的处理接口
 * 注意事项：
 * 每一次处理${}或者动态标签，都要根据入参对象，重新去生成新的SQL语句，所以说每一次都要调用getBoundSql方法
 * select * from user where id = ${id}
 * select * from user where id = 1
 * select * from user where id = 2
 *
 * @author yangwei
 */
public class DynamicSqlSource implements SqlSource {
    /**
     * 封装了带有${}或者动态标签的SQL脚本（树状结构）
     */
    private SqlNode mixedSqlNode;

    public DynamicSqlSource(SqlNode mixedSqlNode) {
        this.mixedSqlNode = mixedSqlNode;
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        // 1、处理所有的SQL节点，获取合并之后的完整的SQL语句（可能还带有#{}）
        DynamicContext context = new DynamicContext(param);
        mixedSqlNode.apply(context);
        String sqlText = context.getSql();
        // 2、调用SqlSource的处理逻辑，对于#{}进行处理
        SqlSourceParser parser = new SqlSourceParser();
        SqlSource sqlSource = parser.parse(sqlText);

        return sqlSource.getBoundSql(param);
    }
}