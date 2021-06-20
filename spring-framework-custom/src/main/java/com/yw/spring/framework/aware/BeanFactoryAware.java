package com.yw.spring.framework.aware;

import com.yw.spring.framework.factory.BeanFactory;

/**
 * @author yangwei
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory);
}