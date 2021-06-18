package com.yw.mybatis.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态上下文
 *
 * @author yangwei
 */
public class DynamicContext {
    private StringBuffer sb = new StringBuffer();
    private Map<String, Object> bindings = new HashMap<>(128);

    public DynamicContext(Object param) {
        // 为了处理${}时，需要用到入参对象
        this.bindings.put("_parameter", param);
    }

    public String getSql() {
        return sb.toString();
    }

    public void appendSql(String sqlText) {
        this.sb.append(sqlText).append(" ");
    }

    public Map<String, Object> getBindings() {
        return bindings;
    }
}