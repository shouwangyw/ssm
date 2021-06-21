package com.yw.springboot.example;

import com.yw.springboot.example.service.SomeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    /**
     * 若接口只有一个实现类，则可以使用byType方式自动注入
     * 但现在有两个实现类，只能使用byName方式自动注入
     */
    @Autowired
    @Qualifier("someServiceImpl")
    private SomeService someService;

    @Autowired
    @Qualifier("otherServiceImpl")
    private SomeService otherService;

    @Test
    public void test01() {
        someService.doSome();
        otherService.doSome();
    }
}