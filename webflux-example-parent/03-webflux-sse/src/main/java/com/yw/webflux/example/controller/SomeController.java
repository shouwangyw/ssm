package com.yw.webflux.example.controller;

import com.yw.webflux.example.util.Timer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yangwei
 */
@Controller
public class SomeController {

    /**
     * 跳转到 default-see.html 页面
     */
    @RequestMapping("/default")
    public String defaultSSEHandle() {
        return "/default-sse";
    }

    /**
     * 跳转到 custom-see.html 页面
     */
    @RequestMapping("/custom")
    public String customSSEHandle() {
        return "/custom-sse";
    }

    /**
     * 定义返回普通响应的处理器方法
     * 目的主要是用于对比一下使用 SSE 前后，接口响应的区别。
     */
    @RequestMapping("/common")
    public void commonHandle(HttpServletResponse response) throws IOException {
        doSome(response);
    }

    /**
     * 定义返回默认 SSE 响应的处理器方法
     */
    @RequestMapping("/sse/default")
    public void defaultHandle(HttpServletResponse response) throws IOException {
        // 根据SSE规范进行设置
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        doSome(response);
    }

    private void doSome(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        for (int i = 0; i < 10; i++) {
            out.println("data:" + i + "\n");
            out.println("\r\n");
            out.flush();

            Timer.sleep(1);
        }
    }

    /**
     * 向客户端发送自定义事件的SSE响应
     */
    @RequestMapping("/sse/custom")
    public void customSSEHandle(HttpServletResponse response) throws IOException {
        // 根据SSE规范进行设置
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        for (int i = 0; i < 10; i++) {
            out.println("event:china\n");
            out.println("data:" + i + "\n");
            out.println("\r\n");
            out.flush();

            Timer.sleep(1);
        }
    }
}
