package com.yw.spring.example;

import com.yw.spring.example.service.UserService;
import com.yw.spring.framework.factory.support.DefaultListableBeanFactory;
import com.yw.spring.framework.reader.XmlBeanDefinitionReader;
import com.yw.spring.framework.resource.ClasspathResource;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用面向对象思维和配置文件的方式去实现容器化管理Bean
 *
 * @author yangwei
 */
public class SpringV2 {
    private DefaultListableBeanFactory beanFactory;

    @Before
    public void before() {
        // 完成XML解析，其实就是完成BeanDefinition的注册
        // XML解析出的结果，放入到beanDefinitions中
        String location = "beans.xml";
        // 获取流对象(使用了策略模式)
        InputStream inputStream = new ClasspathResource(location).getResource();

        // 按照spring定义的标签语义去解析Document文档
        beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions(inputStream);
    }

    @Test
    public void test() {
        // A程序员其实只想使用业务对象去调用对应的服务
        // B程序员编写了一段代码给A程序员提供对象
        UserService userService = (UserService) beanFactory.getBean("userService");

        Map<String, Object> param = new HashMap<>(2);
        param.put("username", "张三");
        System.out.println(userService.queryUsers(param));
    }
}
