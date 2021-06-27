package com.yw.webflux.example.pps;

import com.yw.webflux.example.util.Timer;

import java.util.concurrent.Flow;

/**
 * @author yangwei
 */
public class SomeSubscriber implements Flow.Subscriber<String> {
    /**
     * 声明订阅令牌
     */
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(8);
    }

    @Override
    public void onNext(String item) {
        System.out.println("订阅者正在处理的消息数据为：" + item);
        Timer.sleepMills(50);
        this.subscription.request(10);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        this.subscription.cancel();
    }

    @Override
    public void onComplete() {
        System.out.println("发布者已关闭，订阅者将所有消息全部处理完成");
    }
}