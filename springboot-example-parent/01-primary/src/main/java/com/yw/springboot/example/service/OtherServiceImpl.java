package com.yw.springboot.example.service;

import org.springframework.stereotype.Service;

/**
 * @author yangwei
 */
@Service
public class OtherServiceImpl implements SomeService {
    @Override
    public void doSome() {
        System.out.println("OtherServiceImpl的doSome()");
    }
}