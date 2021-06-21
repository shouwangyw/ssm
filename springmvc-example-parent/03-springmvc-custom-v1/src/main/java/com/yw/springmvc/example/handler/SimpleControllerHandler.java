package com.yw.springmvc.example.handler;

import com.yw.springmvc.example.model.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 指定处理类编写规范(可以针对返回值进行二次处理)
 *
 * @author yangwei
 */
public interface SimpleControllerHandler {
    ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}