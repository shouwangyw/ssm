package com.yw.mybatis.example.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangwei
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;
}
