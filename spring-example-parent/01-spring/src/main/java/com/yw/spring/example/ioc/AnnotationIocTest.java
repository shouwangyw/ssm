package com.yw.spring.example.ioc;

import com.yw.spring.example.ioc.annotation.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yangwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-ioc-anno.xml")
public class AnnotationIocTest {
    @Autowired
    private Student student;

    @Test
    public void testInitApplicationContext() {
        System.out.println(student);
    }

}
