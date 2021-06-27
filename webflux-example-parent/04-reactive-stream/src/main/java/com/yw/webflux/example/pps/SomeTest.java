package com.yw.webflux.example.pps;

import java.util.Random;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author yangwei
 */
public class SomeTest {
    public static void main(String[] args) {
        SubmissionPublisher<Integer> publisher = null;
        try {
            publisher = new SubmissionPublisher<>();
            // 创建订阅者
            SomeSubscriber subscriber = new SomeSubscriber();
            // 创建处理器
            SomeProcessor processor = new SomeProcessor();
            // 建立订阅关系
            publisher.subscribe(processor);
            processor.subscribe(subscriber);
            for (int i = 1; i <= 300; i++) {
                int item = new Random().nextInt(100);
                System.out.println("开始生产消息" + i);
                publisher.submit(item);
            }
        } finally {
            publisher.close();
        }
        System.out.println("主线程开始等待");
        while (Thread.activeCount() > 2) {
        }
    }
}