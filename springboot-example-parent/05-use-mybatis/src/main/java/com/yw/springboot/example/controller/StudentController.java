package com.yw.springboot.example.controller;

import com.yw.springboot.example.dao.po.Student;
import com.yw.springboot.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangwei
 */
@RestController
@RequestMapping("stu")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registerHandle(@RequestBody Student student) {
        boolean result = studentService.addStudent(student);
        return "注册" + (result ? "成功" : "失败");
    }
}