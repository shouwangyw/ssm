package com.yw.springboot.example.service;

import com.yw.springboot.example.dao.po.Student;

/**
 * @author yangwei
 */
public interface StudentService {
    boolean addStudent(Student student);

    Student findStudentById(int id);
    Integer findStudentsCount();
}