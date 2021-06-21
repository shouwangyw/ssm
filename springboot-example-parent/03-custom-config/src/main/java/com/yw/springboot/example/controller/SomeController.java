package com.yw.springboot.example.controller;

import com.yw.springboot.example.dto.CountryDto;
import com.yw.springboot.example.dto.GroupDto;
import com.yw.springboot.example.dto.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author yangwei
 */
@RestController
@PropertySource(value = "classpath:custom.properties", encoding = "UTF-8")
public class SomeController {

    @Value("${company.name}")
    private String companyName;

    @Value("${city.name}")
    private String cityName;

    @Autowired
    private StudentDto student;

    @Autowired
    private CountryDto country;

    @Autowired
    private GroupDto group;

    @GetMapping("/some")
    public String someHandle() {
        return companyName;
    }

    @GetMapping("/custom")
    public String customHandle() {
        return Arrays.asList(cityName, student.toString(), country.toString(), group.toString()).toString();
    }
}