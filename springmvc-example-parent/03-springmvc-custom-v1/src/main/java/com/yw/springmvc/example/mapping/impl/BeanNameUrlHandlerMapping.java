package com.yw.springmvc.example.mapping.impl;

import com.yw.springmvc.example.handler.impl.QueryUserHandler;
import com.yw.springmvc.example.mapping.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <bean name="/queryUser" class="QueryUserHandler的全路径"></bean>
 *
 * @author yangwei
 */
public class BeanNameUrlHandlerMapping implements HandlerMapping {
    private Map<String, Object> urlHandlers = new HashMap<>();

    public BeanNameUrlHandlerMapping() {
        // TODO 暂时先写死
        this.urlHandlers.put("/queryUser", new QueryUserHandler());
    }

    @Override
    public Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();

        return this.urlHandlers.get(uri);
    }
}