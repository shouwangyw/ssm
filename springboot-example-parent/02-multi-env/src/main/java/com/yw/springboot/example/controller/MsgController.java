package com.yw.springboot.example.controller;

import com.yw.springboot.example.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息处理器
 *
 * @author yangwei
 */
@RestController
public class MsgController {
    @Autowired
    private MsgService msgService;

    @GetMapping("/send")
    public String sendHandle() {
        return msgService.send();
    }
}