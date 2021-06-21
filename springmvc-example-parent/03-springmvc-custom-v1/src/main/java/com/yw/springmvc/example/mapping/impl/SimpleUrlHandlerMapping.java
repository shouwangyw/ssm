package com.yw.springmvc.example.mapping.impl;

import com.yw.springmvc.example.handler.impl.SaveUserHandler;
import com.yw.springmvc.example.mapping.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <bean  class="内置的处理器类">
 * <props>
 *      <prop key="url">类的全路径</prop>
 * </props>
 * </bean>
 *
 * @author yangwei
 */
public class SimpleUrlHandlerMapping implements HandlerMapping {
    private Map<String, Object> urlHandlers = new HashMap<>();

    public SimpleUrlHandlerMapping() {
        // TODO 暂时先写死
        this.urlHandlers.put("/saveUser", new SaveUserHandler());
    }

    @Override
    public Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        return this.urlHandlers.get(uri);
    }
}