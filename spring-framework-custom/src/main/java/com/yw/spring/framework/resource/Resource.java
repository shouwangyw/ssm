package com.yw.spring.framework.resource;

import java.io.InputStream;

/**
 * 资源接口
 *
 * @author yangwei
 */
public interface Resource {
    InputStream getResource();
}