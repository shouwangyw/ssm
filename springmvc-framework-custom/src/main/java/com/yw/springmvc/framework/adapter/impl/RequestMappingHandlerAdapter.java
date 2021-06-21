package com.yw.springmvc.framework.adapter.impl;

import com.yw.springmvc.framework.adapter.HandlerAdapter;
import com.yw.springmvc.framework.annotation.ResponseBody;
import com.yw.springmvc.framework.model.HandlerMethod;
import com.yw.springmvc.framework.model.ModelAndView;
import com.yw.springmvc.framework.util.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yangwei
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HandlerMethod);
    }

    @Override
    public ModelAndView handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerMethod hm = (HandlerMethod) handler;
        Object controller = hm.getController();
        Method method = hm.getMethod();

        // 处理请求参数
        Object[] args = handleParameters(request, method);

        // 通过反射调用方法
        Object returnValue = method.invoke(controller, args);

        // 处理返回值
        handleReturnValue(returnValue, method, response);

        return null;
    }

    private Object[] handleParameters(HttpServletRequest request, Method method) {
        List<Object> args = new ArrayList<>();

        // 请求参数KV集合
        Map<String, String[]> parameterMap = request.getParameterMap();

        // 方法参数数组
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            // 取出参数名称（需要特殊处理，否则获取到的就是arg0,arg1这样的参数名称）
            String name = parameter.getName();
            Class<?> type = parameter.getType();
            // 方法参数名称一定要和请求的Key保持一致
            String[] strings = parameterMap.get(name);
            Object valueToUse = resolveValue(strings, type);
            args.add(valueToUse);
        }
        return args.toArray();
    }

    private Object resolveValue(String[] strings, Class<?> type) {
        if (type == Integer.class) {
            return Integer.parseInt(strings[0]);
        } else if (type == String.class) {
            return strings[0];
        }
        return null;
    }

    private void handleReturnValue(Object value, Method method, HttpServletResponse response) throws Exception {
        if (method.isAnnotationPresent(ResponseBody.class)) {
            if (value instanceof String) {
                response.setContentType("text/plain;charset=utf-8");
                response.getWriter().write(value.toString());
            } else if (value instanceof Map) {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JsonUtils.object2Json(value));
            }
        } else {
            // 视图处理
        }
    }
}