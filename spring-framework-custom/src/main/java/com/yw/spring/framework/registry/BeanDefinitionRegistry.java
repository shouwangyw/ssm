package com.yw.spring.framework.registry;

import com.yw.spring.framework.config.BeanDefinition;

import java.util.List;

/**
 * 1、实现类是封装了BeanDefinition集合信息
 * 2、接口类是提供对于其封装的BeanDefinition信息进行添加和获取功能
 *
 * @author yangwei
 */
public interface BeanDefinitionRegistry {
    /**
     * 获取BeanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 注册BeanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 获取BeanDefinition集合
     */
    List<BeanDefinition> getBeanDefinitions();
}