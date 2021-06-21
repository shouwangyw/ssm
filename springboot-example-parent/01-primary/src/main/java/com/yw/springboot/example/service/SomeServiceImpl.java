package com.yw.springboot.example.service;

import org.springframework.stereotype.Service;

/**
 * @author yangwei
 */
@Service
public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome() {
        System.out.println("SomeServiceImpl的doSome()");
    }
}