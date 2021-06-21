package com.yw.springboot.example.service;

import com.yw.springboot.example.dao.mapper.StudentMapper;
import com.yw.springboot.example.dao.po.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangwei
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public boolean addStudent(Student student) {
        int result = studentMapper.insertStudent(student);
        if (result >= 1) {
            return true;
        } else {
            return false;
        }
    }
}