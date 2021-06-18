package com.yw.spring.example.ioc;

import com.yw.spring.example.ioc.xml.Student;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yangwei
 */
public class XmlIocTest {
    @Test
    public void testInitApplicationContext() {
        // 创建IoC容器，并进行初始化
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-ioc.xml");
        // 获取Bean的实例，并验证Bean的实例是否是单例模式的
        Student student1 = (Student) context.getBean("student");
        Student student2 = (Student) context.getBean("student");
        Assert.assertEquals(student1, student2);
    }

}
