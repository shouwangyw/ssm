package com.yw.spring.framework.config;

/**
 * 封装<bean>标签中子标签<property>的ref属性值
 *
 * @author yangwei
 */
public class RuntimeBeanReference {

    // ref的属性值
    private String ref;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public RuntimeBeanReference(String ref) {
        super();
        this.ref = ref;
    }
}