package com.yw.spring.framework.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yangwei
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    /**
     * 存储单例Bean实例的Map容器(线程安全的进行单例管理)
     */
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>(128);

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public void addSingleton(String name, Object bean) {
        // TODO 可以使用双重检查锁进行安全处理
        singletonObjects.put(name, bean);
    }
}