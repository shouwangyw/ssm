package com.yw.springboot.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangwei
 */
@RestController
public class SomeController {
    @RequestMapping("/some")
    public String someHandle() {
        return "Hello Spring Boot World! ";
    }
}
