package com.yw.webflux.example.function.po;

import java.util.Comparator;

/**
 * Student比较
 * @author yangwei
 */
public class StudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.getAge() - s2.getAge();
    }
}
