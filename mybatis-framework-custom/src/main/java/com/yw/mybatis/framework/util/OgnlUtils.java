package com.yw.mybatis.framework.util;

import ognl.Ognl;
import ognl.OgnlContext;

import java.math.BigDecimal;

/**
 * @author yangwei
 */
public class OgnlUtils {
    /**
     * 根据Ongl表达式，获取指定对象的参数值
     */
    public static Object getValue(String expression, Object paramObject) {
        try {
            OgnlContext context = new OgnlContext();
            context.setRoot(paramObject);

            //mybatis中的动态标签使用的是ognl表达式
            //mybatis中的${}使用的是ognl表达式
            // 构建Ognl表达式
            Object ognlExpression = Ognl.parseExpression(expression);
            // 解析表达式
            return Ognl.getValue(ognlExpression, context, context.getRoot());
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 通过Ognl表达式，去计算boolean类型的结果
     */
    public static boolean evaluateBoolean(String expression, Object parameterObject) {
        Object value = OgnlUtils.getValue(expression, parameterObject);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Number) {
            return new BigDecimal(String.valueOf(value)).compareTo(BigDecimal.ZERO) != 0;
        }
        return value != null;
    }
}