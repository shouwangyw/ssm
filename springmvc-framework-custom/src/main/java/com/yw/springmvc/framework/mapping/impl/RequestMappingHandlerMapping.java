package com.yw.springmvc.framework.mapping.impl;

import com.yw.spring.framework.aware.BeanFactoryAware;
import com.yw.spring.framework.config.BeanDefinition;
import com.yw.spring.framework.factory.BeanFactory;
import com.yw.spring.framework.factory.support.DefaultListableBeanFactory;
import com.yw.spring.framework.init.InitializingBean;
import com.yw.springmvc.framework.annotation.Controller;
import com.yw.springmvc.framework.annotation.RequestMapping;
import com.yw.springmvc.framework.mapping.HandlerMapping;
import com.yw.springmvc.framework.model.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangwei
 */
public class RequestMappingHandlerMapping implements HandlerMapping, InitializingBean, BeanFactoryAware {
    private Map<String, HandlerMethod> urlHandlers = new HashMap<>();

    private DefaultListableBeanFactory beanFactory;

    @Override
    public Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        return this.urlHandlers.get(uri);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() {
        List<BeanDefinition> beanDefinitions = this.beanFactory.getBeanDefinitions();
        for (BeanDefinition bd : beanDefinitions) {
            Class<?> clazzType = bd.getClazzType();
            // 如果类上带有@Controller或者@RequestMapping注解
            if (isHandler(clazzType)) {
                RequestMapping classMapping = clazzType.getAnnotation(RequestMapping.class);
                String classUrl = classMapping.value();

                Method[] methods = clazzType.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        // 建立URL和HandlerMethod对象的映射关系

                        StringBuilder sb = new StringBuilder();
                        if (!classUrl.startsWith("/")) {
                            sb.append("/");
                        }
                        sb.append(classUrl);

                        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
                        String methodUrl = methodMapping.value();
                        if (!methodUrl.startsWith("/")) {
                            sb.append("/");
                        }
                        sb.append(methodUrl);

                        // 获取HandlerMethod
                        HandlerMethod hm = new HandlerMethod(beanFactory.getBean(bd.getBeanName()), method);
                        this.urlHandlers.put(sb.toString(), hm);
                    }
                }
            }
        }
    }

    private boolean isHandler(Class<?> clazzType) {
        return (clazzType.isAnnotationPresent(Controller.class)
                || clazzType.isAnnotationPresent(RequestMapping.class));
    }
}