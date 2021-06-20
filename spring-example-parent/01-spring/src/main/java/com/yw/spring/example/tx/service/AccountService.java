package com.yw.spring.example.tx.service;

/**
 * @author yangwei
 */
public interface AccountService {
    void transfer(String from, String to, double money);
}