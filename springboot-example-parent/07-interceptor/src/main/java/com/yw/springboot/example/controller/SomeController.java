package com.yw.springboot.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangwei
 */
@RestController
public class SomeController {
    @RequestMapping("/first/some")
    public String firstHandle() {
        return "/first/some";
    }

    @RequestMapping("/second/some")
    public String secondHandle() {
        return "/second/some";
    }
}