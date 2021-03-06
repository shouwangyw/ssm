package com.yw.springmvc.framework.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * spring mvc 提供的唯一的一个Servlet类
 *
 * @author yangwei
 */
public abstract class AbstractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doDispatch(request, response);
    }

    /**
     * 请求分发
     */
    abstract void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}