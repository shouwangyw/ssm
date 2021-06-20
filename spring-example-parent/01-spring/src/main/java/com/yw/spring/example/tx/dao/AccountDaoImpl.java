package com.yw.spring.example.tx.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JdbcDaoSupport内部封装了JdbcTemplate
 *
 * @author yangwei
 */
@Component
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {
    @Autowired
    public AccountDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public void update(String name, double money) {
        Object[] args = {money, name};
        this.getJdbcTemplate().update("update account set money = ? where name = ?", args);
    }

    @Override
    public double queryMoney(String name) {
        return this.getJdbcTemplate().queryForObject(
                "select money from account where name = ?",
                new DoubleMapper(), name);
    }

    // 结果映射器
    class DoubleMapper implements RowMapper<Double> {
        @Override
        public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getDouble("money");
        }
    }
}