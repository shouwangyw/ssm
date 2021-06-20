package com.yw.spring.example.po;

import lombok.Data;

import java.util.Date;

/**
 * @author yangwei
 */
@Data
public class User {
    private Integer id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;
}