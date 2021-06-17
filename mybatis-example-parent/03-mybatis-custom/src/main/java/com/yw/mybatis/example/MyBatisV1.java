package com.yw.mybatis.example;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

/**
 * 自定义MyBatis框架V1版本
 * 解决硬编码问题：properties文件
 *
 * @author yangwei
 */
public class MyBatisV1 {
    private Properties properties = new Properties();

    @Test
    public void test() {
        // 加载配置文件
        loadProperties("mybatis.properties");

        // 测试
        System.out.println("selectUserById");
        System.out.println(selectList("db.sql.selectUserById", 1));

        System.out.println("selectUserByUsername");
        System.out.println(selectList("db.sql.selectUserByUsername", "李四"));

        System.out.println("selectUserByCriteria");
        Map<String, Object> param = new HashMap<>(2);
        param.put("username", "张三");
        param.put("sex", "男");
        System.out.println(selectList("db.sql.selectUserByCriteria", param));
    }

    private void loadProperties(String location) {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(location)) {
            properties.load(is);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private <T> List<T> selectList(String statementId, Object param) {
        List<T> results = new ArrayList<>();
        try {
//            // 1、加载驱动
//            Class.forName(JDBC_DRIVER);

            // 解决连接获取时的硬编码问题和频繁连接的问题
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("db.driver"));
            dataSource.setUrl(properties.getProperty("db.url"));
            dataSource.setUsername(properties.getProperty("db.username"));
            dataSource.setPassword(properties.getProperty("db.password"));

            // 2、得到连接
//            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            try (Connection conn = dataSource.getConnection();
                 // 3、获取预处理statement
                 PreparedStatement ps = conn.prepareStatement(properties.getProperty(statementId))) {
                // 4、设置参数
//                ps.setObject(1, param);
                if (param instanceof Integer || param instanceof String) {
                    ps.setObject(1, param);
                } else if (param instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) param;
                    String columnNames = properties.getProperty(statementId + ".columnNames");
                    String[] nameArray = columnNames.split(",");
                    for (int i = 0; i < nameArray.length; i++) {
                        ps.setObject(i + 1, map.get(nameArray[i]));
                    }
                } else {
                    // TODO
                }

                // 5、执行SQL
                try (ResultSet rs = ps.executeQuery()) {
                    // 6、遍历结果集
                    String resultType = properties.getProperty(statementId + ".resultType");
                    Class<?> resultTypeClazz = Class.forName(resultType);
                    while (rs.next()) {
                        Object result = resultTypeClazz.newInstance();
                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        for (int i = 0; i < columnCount; i++) {
                            String columnName = metaData.getColumnName(i + 1);
                            Field field = resultTypeClazz.getDeclaredField(columnName);
                            field.setAccessible(true);
                            field.set(result, rs.getObject(columnName));
                        }
                        results.add((T) result);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return results;
    }
}
