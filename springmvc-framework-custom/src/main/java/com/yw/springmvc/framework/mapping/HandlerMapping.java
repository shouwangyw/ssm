package com.yw.springmvc.framework.mapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 它的实现类是用来建立请求和处理类的映射关系
 * 该接口的作用是提供对该映射关系的访问，比如说根据请求查找处理类
 *
 * @author yangwei
 */
public interface HandlerMapping {
    /**
     * 根据请求查找处理类
     */
    Object getHandler(HttpServletRequest request) throws Exception;
}