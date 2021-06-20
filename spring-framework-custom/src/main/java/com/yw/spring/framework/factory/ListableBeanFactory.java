package com.yw.spring.framework.factory;

import java.util.List;

/**
 * 对于Bean容器中的Bean可以进行集合操作或者说叫批量操作
 *
 * @author yangwei
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * 可以根据指定类型获取它或者它实现类的对象
     */
    <T> List<T> getBeansByType(Class type);
}