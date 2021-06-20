package com.yw.spring.framework.registry;

/**
 * 1、实现类是封装了Spring容器创建出来的所有的单例Bean信息
 * 2、接口类提供了对于其封装的数据进行操作的接口功能(获取Bean、添加bean)
 *
 * @author yangwei
 */
public interface SingletonBeanRegistry {
    /**
     * 获取单例bean
     */
    Object getSingleton(String beanName);

    /**
     * 添加单例BEan到集合缓存中
     */
    void addSingleton(String name, Object bean);
}