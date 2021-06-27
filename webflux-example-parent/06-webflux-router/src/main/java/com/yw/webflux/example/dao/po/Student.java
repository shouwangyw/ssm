package com.yw.webflux.example.dao.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author yangwei
 */
@Data
// 指定在MongoDB中生成的表
@Document(collection = "t_student")
public class Student {
    @Id
    private String id; // MongoDB表中的id一般为String类型
    private String name;
    private int age;
}
