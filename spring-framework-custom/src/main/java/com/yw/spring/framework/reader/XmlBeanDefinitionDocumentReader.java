package com.yw.spring.framework.reader;

import com.yw.spring.framework.config.BeanDefinition;
import com.yw.spring.framework.config.PropertyValue;
import com.yw.spring.framework.config.RuntimeBeanReference;
import com.yw.spring.framework.config.TypedStringValue;
import com.yw.spring.framework.registry.BeanDefinitionRegistry;
import com.yw.spring.framework.util.ReflectUtils;
import org.dom4j.Element;

import java.util.List;

/**
 * @author yangwei
 */
public class XmlBeanDefinitionDocumentReader {
    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionDocumentReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void registerBeanDefinitions(Element rootElement) {
        // 获取<bean>和自定义标签（比如mvc:interceptors）
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            // 获取标签名称
            String name = element.getName();
            if ("bean".equals(name)) {
                // 解析默认标签，其实就是bean标签
                parseDefaultElement(element);
            } else {
                // 解析自定义标签，比如aop:aspect标签
                parseCustomElement(element);
            }
        }
    }

    private void parseDefaultElement(Element beanElement) {
        if (beanElement == null) {
            return;
        }
        // 获取id属性
        String id = beanElement.attributeValue("id");
        // 获取name属性
        String name = beanElement.attributeValue("name");
        // 获取class属性
        String clazzName = beanElement.attributeValue("class");
        if (null == clazzName || "".equals(clazzName)) {
            return;
        }
        // 获取init-method属性
        String initMethod = beanElement.attributeValue("init-method");
        // 获取scope属性
        String scope = beanElement.attributeValue("scope");
        scope = scope != null && !"".equals(scope) ? scope : "singleton";
        // 获取beanName
        String beanName = id == null ? name : id;
        try {
            Class<?> clazzType = Class.forName(clazzName);
            beanName = beanName == null ? clazzType.getSimpleName() : beanName;
            // 创建BeanDefinition对象，此处可以使用构建者模式进行优化
            BeanDefinition beanDefinition = new BeanDefinition(clazzName, beanName);
            beanDefinition.setInitMethod(initMethod);
            beanDefinition.setScope(scope);
            // 获取property子标签集合
            List<Element> propertyElements = beanElement.elements();
            for (Element propertyElement : propertyElements) {
                parsePropertyElement(beanDefinition, propertyElement);
            }
            // 注册BeanDefinition信息
            registry.registerBeanDefinition(beanName, beanDefinition);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void parsePropertyElement(BeanDefinition beanDefinition, Element propertyElement) {
        if (propertyElement == null) {
            return;
        }
        // 获取name属性
        String name = propertyElement.attributeValue("name");
        // 获取value属性
        String value = propertyElement.attributeValue("value");
        // 获取ref属性
        String ref = propertyElement.attributeValue("ref");

        // 如果value和ref都有值，则返回
        if (null != value && !"".equals(value) && null != ref && !"".equals(ref)) {
            return;
        }
        PropertyValue pv;
        if (null != value && !"".equals(value)) {
            // 因为spring配置文件中的value是String类型，而对象中的属性值是各种各样的，所以需要存储类型
            TypedStringValue typedStringValue = new TypedStringValue(value);
            Class<?> targetType = ReflectUtils.getTypeByFieldName(beanDefinition.getClazzName(), name);
            typedStringValue.setTargetType(targetType);

            pv = new PropertyValue(name, typedStringValue);
            beanDefinition.addPropertyValue(pv);
        } else if (null != ref && !"".equals(ref)) {
            RuntimeBeanReference reference = new RuntimeBeanReference(ref);
            pv = new PropertyValue(name, reference);
            beanDefinition.addPropertyValue(pv);
        }
    }

    private void parseCustomElement(Element element) {
        // AOP、TX、MVC标签的解析，都是走该流程
    }
}