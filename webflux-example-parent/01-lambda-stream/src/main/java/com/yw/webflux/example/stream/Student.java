package com.yw.webflux.example.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yangwei
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private String school;
    private String gender;
    private int age;
    private double score;
}
