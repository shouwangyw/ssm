package com.yw.springboot.example.dao.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangwei
 */
@Data
public class Student implements Serializable {
    private Integer id;
    private String name;
    private int age;
}
