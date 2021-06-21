package com.yw.springmvc.framework.mapping.impl;

import com.yw.spring.framework.aware.BeanFactoryAware;
import com.yw.spring.framework.config.BeanDefinition;
import com.yw.spring.framework.factory.BeanFactory;
import com.yw.spring.framework.factory.support.DefaultListableBeanFactory;
import com.yw.spring.framework.init.InitializingBean;
import com.yw.springmvc.framework.mapping.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <bean name="/queryUser" class="QueryUserHandler的全路径"></bean>
 *
 * @author yangwei
 */
public class BeanNameUrlHandlerMapping implements HandlerMapping, InitializingBean, BeanFactoryAware {
    private Map<String, Object> urlHandlers = new HashMap<>();

    private DefaultListableBeanFactory beanFactory;

    public BeanNameUrlHandlerMapping() {
    }

    @Override
    public Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();

        return this.urlHandlers.get(uri);
    }

    @Override
    public void afterPropertiesSet() {
        List<BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();
        for (BeanDefinition bd : beanDefinitions) {
            String beanName = bd.getBeanName();
            if (beanName.startsWith("/")) {
                this.urlHandlers.put(beanName, beanFactory.getBean(beanName));
            }
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }
}