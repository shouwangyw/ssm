package com.yw.springmvc.example.controller;

import com.yw.springmvc.framework.annotation.Controller;
import com.yw.springmvc.framework.annotation.RequestMapping;
import com.yw.springmvc.framework.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 注意：
 * SpringMvc中的处理器在该类中到底指的是谁？
 * 是Controller类呢？还是其他的？
 * 真正的处理器是Controller中的某个使用RequestMapping注解标记的方法
 * 也就是Controller对象+Method对象==> HandlerMethod对象（真正的处理器）
 *
 * @author yangwei
 */
@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping("query")
    @ResponseBody
    public Map<String, Object> query(Integer id, String name) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", id);
        map.put("name", name);
        return map;
    }

    @RequestMapping("save")
    @ResponseBody
    public String save() {
        return "OK";
    }
}