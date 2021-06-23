package com.yw.springboot.example.controller;

import com.yw.springboot.example.service.Wrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yangwei
 */
@RestController
public class WrapController {
    /**
     * byType方式的自动注入
     */
    @Resource
    private Wrapper wrapper;

    @RequestMapping("/wrap/{param}")
    public String wrap(@PathVariable("param") String param) {
        return wrapper.wrap(param);
    }
}
