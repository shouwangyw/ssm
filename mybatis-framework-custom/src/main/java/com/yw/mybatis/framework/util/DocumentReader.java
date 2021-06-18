package com.yw.mybatis.framework.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @author yangwei
 */
public class DocumentReader {
    /**
     * 创建Document对象
     */
    public Document createDocument(InputStream inputStream) {
        SAXReader reader = new SAXReader();
        try {
            return reader.read(inputStream);
        } catch (DocumentException e) {
        }
        return null;
    }
}