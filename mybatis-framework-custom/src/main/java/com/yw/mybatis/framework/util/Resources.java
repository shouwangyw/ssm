package com.yw.mybatis.framework.util;

/**
 * @author yangwei
 * @date 2021-06-18 08:23
 */

import java.io.InputStream;
import java.io.Reader;

/**
 * @author yangwei
 */
public class Resources {
    public static InputStream getResourceAsStream(String resource) {
        if (resource == null || "".equals(resource)) {
            return null;
        }

        return Resources.class.getClassLoader().getResourceAsStream(resource);
    }

    public static Reader getResourceAsReader(String resource) {
        return null;
    }
}
