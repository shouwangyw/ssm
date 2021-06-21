package com.yw.springboot.example.controller;

import com.yw.springboot.example.util.CommandUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangwei
 */
@RestController
public class SomeController {
    @RequestMapping("/some")
    public String someHandle() {
        int i = 3 / 0;
        return "Hello Spring Boot World! ";
    }

    @RequestMapping("/hostname")
    public String hostname() throws Exception {
        return CommandUtils.exec("hostname");
    }
}
