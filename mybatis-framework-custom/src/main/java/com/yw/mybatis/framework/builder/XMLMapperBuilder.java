package com.yw.mybatis.framework.builder;

import com.yw.mybatis.framework.Configuration;
import com.yw.mybatis.framework.util.DocumentReader;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;

/**
 * @author yangwei
 */
public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 解析mapper映射文件
     *
     * @param inputStream
     */
    public void parse(InputStream inputStream) {
        // 创建 Document 对象(没有对 MyBatis 语义进行解析)
        DocumentReader reader = new DocumentReader();
        Document document = reader.createDocument(inputStream);

        // 解析mapper映射文件
        parseMapperElement(document.getRootElement());
    }

    /**
     * 解析mapper根标签
     *
     * @param rootElement
     */
    private void parseMapperElement(Element rootElement) {
        String namespace = rootElement.attributeValue("namespace");

        List<Element> selectElements = rootElement.elements("select");
        for (Element selectElement : selectElements) {
            XMLStatementBuilder statementBuilder = new XMLStatementBuilder(configuration);
            statementBuilder.parse(namespace, selectElement);
        }
    }
}