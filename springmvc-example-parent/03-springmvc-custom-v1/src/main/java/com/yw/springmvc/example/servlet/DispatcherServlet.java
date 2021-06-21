package com.yw.springmvc.example.servlet;

import com.yw.springmvc.example.adapter.HandlerAdapter;
import com.yw.springmvc.example.adapter.impl.HttpRequestHandlerAdapter;
import com.yw.springmvc.example.adapter.impl.SimpleControllerHandlerAdapter;
import com.yw.springmvc.example.mapping.HandlerMapping;
import com.yw.springmvc.example.mapping.impl.BeanNameUrlHandlerMapping;
import com.yw.springmvc.example.mapping.impl.SimpleUrlHandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * spring mvc 提供的唯一的一个Servlet类
 *
 * @author yangwei
 */
public class DispatcherServlet extends AbstractServlet {
    /**
     * HandlerMapping的策略集合
     */
    private List<HandlerMapping> handlerMappings = new ArrayList<>();
    /**
     * HandlerAdapter的策略集合
     */
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    /**
     * servlet初始化
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        handlerMappings.add(new BeanNameUrlHandlerMapping());
        handlerMappings.add(new SimpleUrlHandlerMapping());

        handlerAdapters.add(new HttpRequestHandlerAdapter());
        handlerAdapters.add(new SimpleControllerHandlerAdapter());
    }

    @Override
    public void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1、根据请求，查找对应的处理类
            // 问题1：处理类长啥样？（它和Servlet无关，可以随便写，只是说为了统一，最后指定规范[接口]）
            // 问题2：去哪找处理类？（也就是请求URL和处理类的关系在哪建立）
            Object handler = getHandler(request);
            if (handler == null) {
                return;
            }
            // 2、调用处理类的方法，执行请求处理，并返回处理结果
            HandlerAdapter ha = getHandlerAdapter(handler);
            if (ha == null) {
                return;
            }
            ha.handleRequest(handler, request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Object getHandler(HttpServletRequest request) throws Exception {
        // 首先 处理类和请求之间 的映射关系可能存储在不同的地方（HandlerMapping）
        if (handlerMappings != null) {
            for (HandlerMapping hm : handlerMappings) {
                Object handler = hm.getHandler(request);
                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        if (handlerAdapters != null) {
            // 遍历策略集合
            for (HandlerAdapter ha : handlerAdapters) {
                if (ha.supports(handler)) {
                    return ha;
                }
            }
        }
        return null;
    }
}