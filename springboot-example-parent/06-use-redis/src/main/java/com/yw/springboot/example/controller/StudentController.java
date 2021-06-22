package com.yw.springboot.example.controller;

import com.yw.springboot.example.dao.po.Student;
import com.yw.springboot.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/find")
    public Student findHandle(int id) {
        return studentService.findStudentById(id);
    }

    @GetMapping("studentsCount")
    public Integer countHandle() {
        return studentService.findStudentsCount();
    }
}