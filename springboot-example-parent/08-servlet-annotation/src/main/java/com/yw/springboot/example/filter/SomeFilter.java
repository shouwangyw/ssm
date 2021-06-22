package com.yw.springboot.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author yangwei
 */
@WebFilter("/*")
public class SomeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("信息已被过滤");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}