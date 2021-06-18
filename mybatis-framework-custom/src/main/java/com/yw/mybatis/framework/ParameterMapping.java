package com.yw.mybatis.framework;

/**
 * 参数名称和参数类型
 *
 * @author yangwei
 */
public class ParameterMapping {
    private String name;
    private Class<?> parameterTypeClass;

    public ParameterMapping(String name) {
        this.name = name;
        this.parameterTypeClass = parameterTypeClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getParameterTypeClass() {
        return parameterTypeClass;
    }

    public void setParameterTypeClass(Class<?> parameterTypeClass) {
        this.parameterTypeClass = parameterTypeClass;
    }
}
