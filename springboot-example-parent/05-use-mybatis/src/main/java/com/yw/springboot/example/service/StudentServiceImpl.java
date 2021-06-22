package com.yw.springboot.example.service;

import com.yw.springboot.example.dao.mapper.StudentMapper;
import com.yw.springboot.example.dao.po.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yangwei
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    // 采用spring默认的事务提交方式: 发生运行时异常回滚
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addStudent(Student student) {
        int result = studentMapper.insertStudent(student);
        int i = 3 / 0;
        return result >= 1;
    }
}