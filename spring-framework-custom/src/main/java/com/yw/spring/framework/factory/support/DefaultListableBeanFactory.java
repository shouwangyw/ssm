package com.yw.spring.framework.factory.support;

import com.yw.spring.framework.config.BeanDefinition;
import com.yw.spring.framework.factory.ListableBeanFactory;
import com.yw.spring.framework.registry.BeanDefinitionRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 既是spring中的真正管理Bean实例的容器工厂
 * 同时又是管理BeanDefinition的BeanDefinition注册器
 *
 * @author yangwei
 */
public class DefaultListableBeanFactory extends AbstractAutowiredCapableBeanFactory
        implements BeanDefinitionRegistry, ListableBeanFactory {
    /**
     * 存储BeanDefinition的容器
     */
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(128);

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitions.get(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitions.put(beanName, beanDefinition);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions() {
        return new ArrayList<>(beanDefinitions.values());
    }

    @Override
    public <T> List<T> getBeansByType(Class type) {
        List<T> results = new ArrayList<>();
        // 获取容器中所有的BeanDefinition，遍历每个BeanDefinition，取出来它的类型
        for (BeanDefinition bd : beanDefinitions.values()) {
            Class<?> clazzType = bd.getClazzType();
            // type如果是clazzType的父类型，则返回true
            if (type.isAssignableFrom(clazzType)) {
                results.add((T) getBean(bd.getBeanName()));
            }
        }
        return results;
    }
}