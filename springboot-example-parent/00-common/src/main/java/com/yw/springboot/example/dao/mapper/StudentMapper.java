package com.yw.springboot.example.dao.mapper;

import com.yw.springboot.example.dao.po.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author yangwei
 */
@Mapper
@Repository
public interface StudentMapper {
    int insertStudent(Student student);

    Student selectStudentById(int id);
    Integer selectStudentsCount();
}
