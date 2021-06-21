package com.yw.springboot.example.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * 开发环境消息服务实现类
 * 注解@Profile可以用于环境选择，可以用在类上也可以用在方法上。
 *
 * @author yangwei
 */
@Service
@Profile("dev")
public class DevMsgServiceImpl implements MsgService {

    @Override
    public String send() {
        String ret = "-------- DevMsgServiceImpl -----------";
        System.out.println(ret);
        return ret;
    }
}