package com.yw.spring.framework.factory.support;

import com.yw.spring.framework.config.BeanDefinition;
import com.yw.spring.framework.factory.BeanFactory;
import com.yw.spring.framework.registry.DefaultSingletonBeanRegistry;

/**
 * 主要是完成getBean操作的共性部分的实现
 * 将特性部分的实现，让子类去完成（抽象模板方法）
 *
 * @author yangwei
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String beanName) {
        // 1、首先从singletonObjects集合中获取对应的beanName的实例
        Object singletonObject = getSingleton(beanName);
        // 2、如果有对象，则直接返回
        if (singletonObject != null) {
            return singletonObject;
        }
        // 3、如果没有该对象，则获取对应的beanDefinition信息
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        if (beanDefinition == null) {
            return null;
        }
        // 4、判断是单例还是多例，如果是单例，则走单例创建Bean流程
        if (beanDefinition.isSingleton()) {
            // 5、单例流程中，需要将创建出来的Bean放入singletonObjects集合中
            singletonObject = createBean(beanDefinition);
            addSingleton(beanName, singletonObject);
        } else if (beanDefinition.isPrototype()) {
            // 6、如果是多例，走多例的创建Bean流程
            singletonObject = createBean(beanDefinition);
        }
        return singletonObject;
    }

    /**
     * 需要延迟到DefaultListableBeanFactory去实现
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);

    /**
     * 需要延迟到AbstractAutowiredCapableBeanFactory去实现
     */
    protected abstract Object createBean(BeanDefinition beanDefinition);
}