package com.yw.springmvc.example.handler;

import com.yw.springmvc.framework.handler.SimpleControllerHandler;
import com.yw.springmvc.framework.model.ModelAndView;

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
        response.getWriter().write("--- SaveUserHandler V2 ---");
        return null;
    }
}