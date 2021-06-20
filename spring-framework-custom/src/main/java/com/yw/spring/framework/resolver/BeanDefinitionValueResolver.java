package com.yw.spring.framework.resolver;

import com.yw.spring.framework.config.RuntimeBeanReference;
import com.yw.spring.framework.config.TypedStringValue;
import com.yw.spring.framework.factory.BeanFactory;

/**
 * 专门负责BeanDefinition中存储的value的转换
 *
 * @author yangwei
 */
public class BeanDefinitionValueResolver {
    private BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object resolveValue(Object value) {
        if (value instanceof TypedStringValue) {
            TypedStringValue typedStringValue = (TypedStringValue) value;
            Class<?> targetType = typedStringValue.getTargetType();
            String stringValue = typedStringValue.getValue();
            if (targetType == Integer.class) {
                return Integer.parseInt(stringValue);
            } else if (targetType == String.class) {
                return stringValue;
            }// TODO其它类型
        } else if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference beanReference = (RuntimeBeanReference) value;
            String ref = beanReference.getRef();
            // 递归调用
            return beanFactory.getBean(ref);
        }
        return null;
    }
}