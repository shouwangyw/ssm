package com.yw.webflux.example.ps;

import java.util.Random;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author yangwei
 */
public class SomeSubscriberTest {
    public static void main(String[] args) {
        SubmissionPublisher<Integer> publisher = null;
        try {
            // 创建一个发布者
            publisher = new SubmissionPublisher<>(); // 无参构造，默认最大容量256
            // 创建一个订阅者
            SomeSubscriber subscriber = new SomeSubscriber();
            // 确立订阅关系，该方法的执行与其触发的onSubscribe()方法是异步执行的
            publisher.subscribe(subscriber);
            // 生产消息
            for (int i = 0; i < 300; i++) {
                // 生产一个[0, 100)随机数
                int item = new Random().nextInt(100);
                System.out.println("生成第" + i + "条消息" + item);
                // 发布消息，当publisher的buffer满时该方法会阻塞
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
