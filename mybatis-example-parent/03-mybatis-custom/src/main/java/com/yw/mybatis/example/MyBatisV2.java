package com.yw.mybatis.example;

import com.yw.mybatis.framework.BoundSql;
import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.MappedStatement;
import com.yw.mybatis.framework.ParameterMapping;
import com.yw.mybatis.framework.sqlnode.*;
import com.yw.mybatis.framework.sqlsource.DynamicSqlSource;
import com.yw.mybatis.framework.sqlsource.RawSqlSource;
import com.yw.mybatis.framework.sqlsource.SqlSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * 自定义MyBatis框架V2版本
 * 解决硬编码问题：
 * 1、properties配置文件升级为XML配置文件
 * 2、使用面向过程思维去优化代码
 * 3、使用面向对象思维去理解配置文件封装的类的作用
 *
 * @author yangwei
 */
public class MyBatisV2 {
    private Configuration configuration = new Configuration();
    private boolean isDynamic = false;

    @Test
    public void test() {
        // 加载XML配置文件
        loadXML("mybatis-config.xml");

        Map<String, Object> param = new HashMap<>(2);
        param.put("username", "张三");
        param.put("sex", "男");
        System.out.println(selectList("test.selectUserByCriteria", param));
    }

    private void loadXML(String location) {
        try (InputStream is = getResourceAsStream(location)) {
            // 获取Document对象
            Document document = getDocument(is);
            // 根据xml语义进行解析
            parseConfiguration(document.getRootElement());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private InputStream getResourceAsStream(String location) {
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }

    private Document getDocument(InputStream is) {
        SAXReader reader = new SAXReader();
        try {
            return reader.read(is);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void parseConfiguration(Element rootElement) {
        Element environments = rootElement.element("environments");
        parseEnvironments(environments);

        Element mappers = rootElement.element("mappers");
        parseMappers(mappers);
    }

    private void parseEnvironments(Element environments) {
        String defaultEnv = environments.attributeValue("default");
        List<Element> elements = environments.elements("environment");
        for (Element element : elements) {
            String id = element.attributeValue("id");
            if (defaultEnv.equalsIgnoreCase(id)) {
                parseDataSource(element.element("dataSource"));
            }
        }
    }

    private void parseDataSource(Element dataSource) {
        String type = dataSource.attributeValue("type");
        if ("DBCP".equalsIgnoreCase(type)) {
            BasicDataSource ds = new BasicDataSource();
            Properties properties = parseProperties(dataSource);
            ds.setDriverClassName(properties.getProperty("db.driver"));
            ds.setUrl(properties.getProperty("db.url"));
            ds.setUsername(properties.getProperty("db.username"));
            ds.setPassword(properties.getProperty("db.password"));

            configuration.setDataSource(ds);
        }
    }

    private Properties parseProperties(Element dataSource) {
        Properties properties = new Properties();
        List<Element> list = dataSource.elements("property");
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.put(name, value);
        }
        return properties;
    }

    private void parseMappers(Element mappers) {
        List<Element> elements = mappers.elements("mapper");
        for (Element element : elements) {
            String resource = element.attributeValue("resource");
            InputStream is = getResourceAsStream(resource);
            Document document = getDocument(is);
            parseMapper(document.getRootElement());
        }
    }

    private void parseMapper(Element rootElement) {
        String namespace = rootElement.attributeValue("namespace");
        List<Element> selectElemets = rootElement.elements("select");
        for (Element element : selectElemets) {
            parseStatementElement(namespace, element);
        }
    }

    private void parseStatementElement(String namespace, Element element) {
        // 一个CURD标签对应一个MappedStatement对象
        // 一个MappedStatement对象由一个statementId来标识，所以保证唯一性
        String statementId = namespace + "." + element.attributeValue("id");

        String resultType = element.attributeValue("resultType");
        Class<?> resultTypeClass = resolveType(resultType);

        String statementType = element.attributeValue("statementType");
        statementType = statementType == null || "".equals(statementType) ? "prepared" : statementType;

        // SqlSource和SqlNode的封装过程
        SqlSource sqlSource = createSqlSource(element);

        // 建议使用构建者模式去优化
        MappedStatement mappedStatement = new MappedStatement(statementId, statementType, resultType, resultTypeClass, sqlSource);
        configuration.addMappedStatement(statementId, mappedStatement);
    }

    private Class<?> resolveType(String type) {
        try {
            Class<?> clazz = Class.forName(type);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private SqlSource createSqlSource(Element element) {
        //TODO 其他子标签的解析处理

        return parseScriptNode(element);
    }

    private SqlSource parseScriptNode(Element element) {
        // 解析所有SQL节点，最终封装到MixedSqlNode中
        SqlNode mixedSqlNode = parseDynamicTags(element);

        SqlSource sqlSource;
        //如果带有${}或者动态SQL标签
        if (isDynamic) {
            sqlSource = new DynamicSqlSource(mixedSqlNode);
        } else {
            sqlSource = new RawSqlSource(mixedSqlNode);
        }
        return sqlSource;
    }

    private SqlNode parseDynamicTags(Element element) {
        List<SqlNode> sqlNodes = new ArrayList<>();

        // 获取select标签的子元素 ：文本类型或者Element类型
        int nodeCount = element.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            Node node = element.node(i);
            if (node instanceof Text) {
                String text = node.getText();
                if (text == null || "".equals(text.trim())) {
                    continue;
                }
                // 先将sql文本封装到TextSqlNode中
                TextSqlNode textSqlNode = new TextSqlNode(text.trim());
                if (textSqlNode.isDynamic()) {
                    sqlNodes.add(textSqlNode);
                    isDynamic = true;
                } else {
                    sqlNodes.add(new StaticTextSqlNode(text.trim()));
                }
            } else if (node instanceof Element) {
                isDynamic = true;
                Element ele = (Element) node;
                String name = ele.getName();
                if ("if".equals(name)) {
                    String test = ele.attributeValue("test");
                    // 递归去解析子元素
                    SqlNode sqlNode = parseDynamicTags(ele);
                    sqlNodes.add(new IfSqlNode(test, sqlNode));
                } else {
                    // TODO
                }
            } else {
                // TODO
            }
        }

        return new MixedSqlNode(sqlNodes);
    }

    private <T> List<T> selectList(String statementId, Object param) {
        List<T> results = new ArrayList<>();

        MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
        // 1、获取数据库连接
        try (Connection connection = getConnection()) {
            // 2.1、获取BoundSql
            BoundSql boundSql = getBoundSql(mappedStatement, param);
            // 2.2、获取SQL
            String sql = boundSql.getSql();
            System.out.println(sql);
            // 3、创建Statement
            try (Statement statement = createStatement(connection, mappedStatement, sql)) {
                // 4、设置参数
                setParameters(param, statement, boundSql);
                // 5、执行Statement
                try (ResultSet rs = executeQuery(statement)) {
                    // 6、处理结果
                    handleResult(rs, mappedStatement, results);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return results;
    }

    private Connection getConnection() throws Exception {
        DataSource dataSource = configuration.getDataSource();
        return dataSource.getConnection();
    }

    private BoundSql getBoundSql(MappedStatement mappedStatement, Object param) {
        SqlSource sqlSource = mappedStatement.getSqlSource();
        //  触发SqlSource和SqlNode的解析处理流程
        return sqlSource.getBoundSql(param);
    }

    private Statement createStatement(Connection connection, MappedStatement mappedStatement, String sql) throws Exception {
        String statementType = mappedStatement.getStatementType();
        if ("prepared".equalsIgnoreCase(statementType)) {
            return connection.prepareStatement(sql);
        } else if ("callable".equalsIgnoreCase(statementType)) {
            return connection.prepareCall(sql);
        } else {
            return connection.createStatement();
        }
    }

    private void setParameters(Object param, Statement statement, BoundSql boundSql) throws Exception {
        if (statement instanceof PreparedStatement) {
            PreparedStatement ps = (PreparedStatement) statement;
            if (param instanceof Integer || param instanceof String) {
                ps.setObject(1, param);
            } else if (param instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) param;
                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                for (int i = 0; i < parameterMappings.size(); i++) {
                    ParameterMapping parameterMapping = parameterMappings.get(i);
                    ps.setObject(i + 1, map.get(parameterMapping.getName()));
                }
            } else {
                // TODO
            }
        }
    }

    private ResultSet executeQuery(Statement statement) throws Exception {
        if (statement instanceof PreparedStatement) {
            PreparedStatement ps = (PreparedStatement) statement;
            return ps.executeQuery();
        }
        return null;
    }

    private <T> void handleResult(ResultSet rs, MappedStatement mappedStatement, List<T> results) throws Exception {
        Class<?> resultTypeClass = mappedStatement.getResultTypeClass();
        while (rs.next()) {
            Object result = resultTypeClass.newInstance();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i + 1);
                Field field = resultTypeClass.getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(result, rs.getObject(columnName));
            }
            results.add((T) result);
        }
    }
}
