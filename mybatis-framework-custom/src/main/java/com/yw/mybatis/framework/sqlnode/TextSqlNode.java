package com.yw.mybatis.framework.sqlnode;

import com.yw.mybatis.framework.DynamicContext;
import com.yw.mybatis.framework.util.GenericTokenParser;
import com.yw.mybatis.framework.util.OgnlUtils;
import com.yw.mybatis.framework.util.SimpleTypeRegistry;
import com.yw.mybatis.framework.util.TokenHandler;

/**
 * 封装了带有${}的 SQL文本
 *
 * @author yangwei
 */
public class TextSqlNode implements SqlNode {
    private String sqlText;

    public TextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    @Override
    public void apply(DynamicContext context) {
        // 处理${}
        GenericTokenParser tokenParser = new GenericTokenParser("${", "}", new BindingTokenHandler(context));
        String sql = tokenParser.parse(sqlText);
        context.appendSql(sql);
    }

    public boolean isDynamic() {
        return sqlText.indexOf("${") > -1;
    }

    class BindingTokenHandler implements TokenHandler {
        // 为了获取入参对象
        private DynamicContext context;

        public BindingTokenHandler(DynamicContext context) {
            this.context = context;
        }

        @Override
        public String handleToken(String content) {
            Object parameter = context.getBindings().get("_parameter");
            if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
                return parameter.toString();
            }
            // 使用ONGL表达式获取值
            Object value = OgnlUtils.getValue(content, parameter);
            return value == null ? "" : value.toString();
        }
    }
}
