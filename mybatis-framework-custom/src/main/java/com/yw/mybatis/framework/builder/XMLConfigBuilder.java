package com.yw.mybatis.framework.builder;

import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.util.DocumentReader;
import com.yw.mybatis.framework.util.Resources;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * 主要作用：解析全局配置文件
 *
 * @author yangwei
 */
public class XMLConfigBuilder {
    private Configuration configuration;

    public XMLConfigBuilder() {
        super();
        this.configuration = new Configuration();
    }

    /**
     * 解析全局配置文件
     *
     * @param inputStream
     * @return
     */
    public Configuration parse(InputStream inputStream) {
        // 创建 Document 对象(没有对 MyBatis 语义进行解析)
        DocumentReader documentReader = new DocumentReader();
        Document document = documentReader.createDocument(inputStream);

        // 根据 MyBatis 语义去解析 Document 对象，将解析结果封装到一个对象(Configuration)
        parserConfiguration(document.getRootElement());

        return configuration;
    }

    /**
     * 解析configuration根标签
     *
     * @param rootElement
     */
    private void parserConfiguration(Element rootElement) {
        // 解析environments标签
        parseEnvironmentsElement(rootElement.element("environments"));
        // 解析mappers标签
        parseMappersElement(rootElement.element("mappers"));
    }

    /**
     * 解析数据源信息(dom4j + path) 将数据源信息封装到 Configuration 对象中
     *
     * @param element
     */
    private void parseEnvironmentsElement(Element element) {
        // 获取默认的环境对象的ID
        String defaultEnvId = element.attributeValue("default");
        if (defaultEnvId == null || "".equals(defaultEnvId)) {
            return;
        }
        List<Element> elements = element.elements();
        for (Element envElement : elements) {
            String envId = envElement.attributeValue("id");
            if (defaultEnvId.equals(envId)) {
                // 创建数据源对象
                createdDataSource(envElement);
            }
        }
    }

    /**
     * @param envElement environment 标签
     */
    private void createdDataSource(Element envElement) {
        // 获取数据源信息
        Element dataSourceElement = envElement.element("dataSource");
        // 获取连接池类型
        String dataSourceType = dataSourceElement.attributeValue("type");
        // 获取连接属性信息
        List<Element> propertyElements = dataSourceElement.elements("property");

        Properties properties = new Properties();
        for (Element propertyElement : propertyElements) {
            String name = propertyElement.attributeValue("name");
            String value = propertyElement.attributeValue("value");
            properties.setProperty(name, value);
        }
        if (dataSourceType.equals("DBCP")) {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("db.driver"));
            dataSource.setUrl(properties.getProperty("db.url"));
            dataSource.setUsername(properties.getProperty("db.username"));
            dataSource.setPassword(properties.getProperty("db.password"));

            configuration.setDataSource(dataSource);
        }
    }

    /**
     * 解析mappers标签
     *
     * @param element
     */
    private void parseMappersElement(Element element) {
        List<Element> mapperElements = element.elements();

        for (Element mapperElement : mapperElements) {
            parseMapperElement(mapperElement);
        }
    }

    private void parseMapperElement(Element mapperElement) {
        // 根据映射文件路径，读取映射文件(InputStream流)
        String resource = mapperElement.attributeValue("resource");
        InputStream inputStream = Resources.getResourceAsStream(resource);

        // 创建 Document 对象(没有对 MyBatis 语义进行解析)
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        xmlMapperBuilder.parse(inputStream);
    }
}