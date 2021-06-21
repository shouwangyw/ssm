package com.yw.springmvc.example.handler.impl;

import com.yw.springmvc.example.handler.SimpleControllerHandler;
import com.yw.springmvc.example.model.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yangwei
 */
public class SaveUserHandler implements SimpleControllerHandler {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().write("--- SaveUserHandler ---");
        return null;
    }
}