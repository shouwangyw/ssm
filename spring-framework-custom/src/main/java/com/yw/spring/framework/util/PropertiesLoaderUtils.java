package com.yw.spring.framework.util;

import com.yw.spring.framework.resource.ClasspathResource;

import java.io.IOException;
import java.util.Properties;

/**
 * @author yangwei
 */
public class PropertiesLoaderUtils {
    public static Properties loadProperties(ClasspathResource resource) throws IOException {
        Properties props = new Properties();
        props.load(resource.getResource());
        return props;
    }
}