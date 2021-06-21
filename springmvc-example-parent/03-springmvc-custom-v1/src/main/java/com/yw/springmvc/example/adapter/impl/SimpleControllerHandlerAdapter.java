package com.yw.springmvc.example.adapter.impl;

import com.yw.springmvc.example.adapter.HandlerAdapter;
import com.yw.springmvc.example.handler.SimpleControllerHandler;
import com.yw.springmvc.example.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangwei
 */
public class SimpleControllerHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof SimpleControllerHandler);
    }

    @Override
    public ModelAndView handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ((SimpleControllerHandler) handler).handleRequest(request, response);
    }
}