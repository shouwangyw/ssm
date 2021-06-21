package com.yw.springboot.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yangwei
 */
@Controller
@RequestMapping("/test")
public class SomeController {
    @RequestMapping("/register")
    public String registerHandle(String name, int age, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "jsp/welcome";
    }
}
