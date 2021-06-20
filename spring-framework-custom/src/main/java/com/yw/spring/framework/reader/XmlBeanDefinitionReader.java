package com.yw.spring.framework.reader;

import com.yw.spring.framework.registry.BeanDefinitionRegistry;
import com.yw.spring.framework.util.DocumentUtils;
import org.dom4j.Document;

import java.io.InputStream;

/**
 * @author yangwei
 */
public class XmlBeanDefinitionReader {
    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }


    public void loadBeanDefinitions(InputStream inputStream) {
        // 创建文档对象
        Document document = DocumentUtils.getDocument(inputStream);
        XmlBeanDefinitionDocumentReader documentReader = new XmlBeanDefinitionDocumentReader(registry);
        documentReader.registerBeanDefinitions(document.getRootElement());
    }
}