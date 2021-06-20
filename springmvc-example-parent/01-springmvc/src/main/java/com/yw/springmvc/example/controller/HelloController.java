package com.yw.springmvc.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yangwei
 */
@Controller
public class HelloController {
	@RequestMapping("")
	public String index() {
		return "index";
	}

	// @RequestMapping：在方法上或者类上加该注解，指定请求的`url`由该方法处理
	@RequestMapping("hello")
	public ModelAndView hello(){
		ModelAndView mv = new ModelAndView();
		/**
		 * 设置数据模型
		 * 相当于request的setAttribute方法
		 * 底层是个Map，先将K-V数据放入Map中，最终根据视图对象不同，再进行后续处理
		 */
		mv.addObject("msg", "springmvc-demo");
		// 设置视图（逻辑路径）
		mv.setViewName("hello");
		return mv;
	}
}