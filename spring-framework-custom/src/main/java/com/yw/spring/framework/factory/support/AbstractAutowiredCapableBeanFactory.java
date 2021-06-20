package com.yw.spring.framework.factory.support;

import com.yw.spring.framework.aware.Aware;
import com.yw.spring.framework.aware.BeanFactoryAware;
import com.yw.spring.framework.config.BeanDefinition;
import com.yw.spring.framework.config.PropertyValue;
import com.yw.spring.framework.init.InitializingBean;
import com.yw.spring.framework.resolver.BeanDefinitionValueResolver;
import com.yw.spring.framework.util.ReflectUtils;

import java.util.List;

/**
 * 完成Bean的创建和依赖装配
 *
 * @author yangwei
 */
public abstract class AbstractAutowiredCapableBeanFactory extends AbstractBeanFactory {
    @Override
    protected Object createBean(BeanDefinition beanDefinition) {
        // 1、Bean的实例化
        Object bean = createBeanByConstructor(beanDefinition);

        // TODO 处理循环依赖

        // 2、Bean的属性填充(依赖注入)
        populateBean(bean, beanDefinition);
        // 3、Bean的初始化
        initializeBean(bean, beanDefinition);

        return bean;
    }

    private Object createBeanByConstructor(BeanDefinition beanDefinition) {
        // TODO 静态工厂方法、工厂实例方法

        // 构造器方式去创建Bean实例
        return ReflectUtils.newInstance(beanDefinition.getClazzType());
    }

    private void populateBean(Object bean, BeanDefinition beanDefinition) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue pv : propertyValues) {
            String name = pv.getName();
            Object value = pv.getValue();

            BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
            Object valueToUse = resolver.resolveValue(value);

            ReflectUtils.setField(bean, name, valueToUse);
        }
    }

    private void initializeBean(Object bean, BeanDefinition beanDefinition) {
        // 需要针对Aware接口标记的类进行特殊处理
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
        }
        // 可以进行InitializeBean接口的处理
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        ReflectUtils.invokeMethod(bean, beanDefinition.getInitMethod());
    }
}