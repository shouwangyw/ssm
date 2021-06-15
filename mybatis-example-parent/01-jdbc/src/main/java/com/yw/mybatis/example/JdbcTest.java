package com.yw.mybatis.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author yangwei
 */
public class JdbcTest {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://192.168.254.128:3306/kaikeba?characterEncoding=utf-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    private static final String TEST_SQL = "select * from user where id = ?";

    public static void main(String[] args) {
        findUserById(1);
    }

    private static void findUserById(int id) {
        try {
            // 1、加载驱动
            Class.forName(JDBC_DRIVER);
            // 2、得到连接
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 // 3、获取预处理statement
                 PreparedStatement ps = conn.prepareStatement(TEST_SQL)) {
                // 4、设置参数
                ps.setObject(1, id);
                // 5、执行SQL
                try (ResultSet rs = ps.executeQuery()) {
                    // 6、遍历结果集
                    while (rs.next()) {
                        String result = String.format("{id = %d, username = %s, sex = %s}",
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("sex"));

                        System.out.println(result);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
