package com.yw.springboot.example.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * 生成环境消息服务实现类
 *
 * @author yangwei
 */
@Service
@Profile("prod")
public class ProdMsgServiceImpl implements MsgService {

    @Override
    public String send() {
        String ret = "-------- ProdMsgServiceImpl -----------";
        System.out.println(ret);
        return ret;
    }
}