package com.yw.spring.example.ioc;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解决的是扩展性问题：通过配置的方式去解决扩展性问题
 *      使用XML配置文件进行Bean的创建
 *      1.管理要new出来的bean的class信息（要new几个对象，就需要配置几个class信息）
 *      <bean id="bean的唯一name" class="要new的对象的全路径"></bean>
 *      2.管理要new出来的bean的属性的依赖关系（如果A对象依赖了B对象，那么这两个对象都要配置class信息，并且要确定关系）
 *      <bean id="bean的唯一name" class="要new的对象的全路径">
 *           <property name="属性名称" ref="要建立关系的另一个bean的唯一name"/>
 *      </bean>
 *      3.读取静态的信息，去创建对象
 *      BeanDefinition类--->用来存储<bean>标签中的信息
 *      Map<String,BeanDefinition>
 *      4.利用反射从BeanDefinition中获取class信息，区创建对象
 *
 * @author yangwei
 */
public class BeanFactory {
    // 存储单例Bean实例的Map容器
    private Map<String, Object> singletonObjects = new HashMap<>();

    // 存储BeanDefinition的容器
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    public BeanFactory() {
        // 完成XML解析，其实就是完成BeanDefinition的注册
        // XML解析出的结果，放入到beanDefinitions中
        String location = "beans.xml";
        // 获取流对象
        InputStream inputStream = getInputStream(location);
        // 创建文档对象
        Document document = createDocument(inputStream);
        // 按照spring定义的标签语义去解析Document文档
        registerBeanDefinitions(document.getRootElement());
    }

    private InputStream getInputStream(String location) {
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }

    private Document createDocument(InputStream inputStream) {
        SAXReader reader = new SAXReader();
        try {
            return reader.read(inputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void registerBeanDefinitions(Element rootElement) {
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
            this.beanDefinitions.put(beanName, beanDefinition);
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
            Class<?> targetType = getTypeByFieldName(beanDefinition.getClazzName(), name);
            typedStringValue.setTargetType(targetType);

            pv = new PropertyValue(name, typedStringValue);
            beanDefinition.addPropertyValue(pv);
        } else if (null != ref && !"".equals(ref)) {
            RuntimeBeanReference reference = new RuntimeBeanReference(ref);
            pv = new PropertyValue(name, reference);
            beanDefinition.addPropertyValue(pv);
        } else {
            return;
        }
    }

    private Class<?> getTypeByFieldName(String beanClassName, String name) {
        try {
            Class<?> clazz = Class.forName(beanClassName);
            Field field = clazz.getDeclaredField(name);
            return field.getType();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void parseCustomElement(Element element) {
        // AOP、TX、MVC标签的解析，都是走该流程
    }

    public Object getBean(String beanName) {
        // 1、首先从singletonObjects集合中获取对应的beanName的实例
        Object singletonObject = this.singletonObjects.get(beanName);
        // 2、如果有对象，则直接返回
        if (singletonObject != null) {
            return singletonObject;
        }
        // 3、如果没有该对象，则获取对应的beanDefinition信息
        BeanDefinition beanDefinition = this.beanDefinitions.get(beanName);
        // 4、判断是单例还是多例，如果是单例，则走单例创建Bean流程
        if (beanDefinition.isSingleton()) {
            // 5、单例流程中，需要将创建出来的Bean放入singletonObjects集合中
            singletonObject = doCreateBean(beanDefinition);
            this.singletonObjects.put(beanName, singletonObject);
        } else if (beanDefinition.isPrototype()) {
            // 6、如果是多例，走多例的创建Bean流程
            singletonObject = doCreateBean(beanDefinition);
        }
        return singletonObject;
    }

    private Object doCreateBean(BeanDefinition beanDefinition) {
        // 1、Bean的实例化
        Object bean = createBeanByConstructor(beanDefinition);
        // 2、Bean的属性填充(依赖注入)
        populateBean(bean, beanDefinition);
        // 3、Bean的初始化
        initializeBean(bean, beanDefinition);

        return bean;
    }

    private Object createBeanByConstructor(BeanDefinition beanDefinition) {
        // TODO 静态工厂方法、工厂实例方法

        // 构造器方式去创建Bean实例
        try {
            Class<?> clazzType = beanDefinition.getClazzType();
            // 选择无参构造
            Constructor<?> constructor = clazzType.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void populateBean(Object bean, BeanDefinition beanDefinition) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue pv : propertyValues) {
            String name = pv.getName();
            Object value = pv.getValue();
            Object valueToUse = resolveValue(value);

            setProperty(bean, name, valueToUse);
        }
    }

    private Object resolveValue(Object value) {
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
            return getBean(ref);
        }
        return null;
    }

    private void setProperty(Object bean, String name, Object valueToUse) {
        try {
            Class<?> aClass = bean.getClass();
            Field field = aClass.getDeclaredField(name);
            field.setAccessible(true);
            field.set(bean, valueToUse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void initializeBean(Object bean, BeanDefinition beanDefinition) {
        try {
            String initMethod = beanDefinition.getInitMethod();
            if (initMethod == null) {
                return;
            }
            Class<?> clazzType = beanDefinition.getClazzType();
            Method method = clazzType.getDeclaredMethod(initMethod);
            method.setAccessible(true);
            method.invoke(bean);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
