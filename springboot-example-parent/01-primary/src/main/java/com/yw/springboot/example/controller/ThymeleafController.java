package com.yw.springboot.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yangwei
 */
@Controller
public class ThymeleafController {
    @RequestMapping("/test/myindex")
    public String indexHandle(Model model) {
        model.addAttribute("welcome", "Hello Thymeleaf World");
        return "index";
    }
}