package com.yw.webflux.example.ps;

import com.yw.webflux.example.util.Timer;

import java.util.concurrent.Flow;

/**
 * 订阅者
 * @author yangwei
 */
public class SomeSubscriber implements Flow.Subscriber<Integer> {
    /**
     * 声明订阅令牌
     */
    private Flow.Subscription subscription;

    /**
     * 当订阅关系确立后，触发该方法的执行（只执行一次）
     * 即Publisher的subscribe()方法的执行会触发该方法的执行
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("--- 执行订阅者的onSubscribe()方法 ---");
        this.subscription = subscription;
        // 首次订阅8条消息
        this.subscription.request(8);
    }

    /**
     * 消息在这里进行消费，该方法的第一次触发是由onSubscribe()方法中的 this.subscription.request(8); 触发
     */
    @Override
    public void onNext(Integer item) {
        System.out.println("订阅者正在处理的消息数据为：" + item);
        Timer.sleepMills(50);
//        if (item < 30) {
//            this.subscription.cancel();
//        }
        // 订阅者每消费一条消息，就再请求10条消息
        this.subscription.request(10);
    }

    /**
     * 当发布者发布过程中，或订阅关系确立过程中，或订阅者请求消息过程中，或消费者消费过程中
     * 出现异常，则会触发该方法的执行，该方法的执行会导致 onNext() 方法不再执行
     */
    @Override
    public void onError(Throwable throwable) {
        System.out.println(" --- 执行onError()方法 ---");
        throwable.printStackTrace();
        this.subscription.cancel();
    }

    /**
     * 当所有消息消费完毕后会执行该方法
     *      Publisher的close()方法执行完毕后会触发该方法的执行
     */
    @Override
    public void onComplete() {
        System.out.println("发布者已关闭，订阅者将所有消息全部处理完成");
    }
}