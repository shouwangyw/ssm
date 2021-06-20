package com.yw.spring.framework.util;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @author yangwei
 */
public class DocumentUtils {
    public static Document getDocument(InputStream inputStream) {
        try {
            SAXReader reader = new SAXReader();
            return reader.read(inputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}