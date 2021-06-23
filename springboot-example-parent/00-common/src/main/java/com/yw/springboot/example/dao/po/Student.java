package com.yw.springboot.example.dao.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yangwei
 */
@Data
@Accessors(chain = true)
public class Student implements Serializable {
    private static final long serialVersionUID = -25318027059645297L;

    private Integer id;
    private String name;
    private int age;
}
