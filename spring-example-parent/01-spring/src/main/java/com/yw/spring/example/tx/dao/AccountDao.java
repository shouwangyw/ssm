package com.yw.spring.example.tx.dao;

/**
 * @author yangwei
 */
public interface AccountDao {
    void update(String name, double money);

    double queryMoney(String name);
}