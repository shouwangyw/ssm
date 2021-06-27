package com.yw.webflux.example.pps;

import com.yw.webflux.example.util.Timer;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * 处理器
 * @author yangwei
 */
public class SomeProcessor extends SubmissionPublisher<String> implements Flow.Processor<Integer, String> {
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
    public void onNext(Integer item) {
        System.out.println("--- 处理器正在处理的消息数据为：" + item);
        // 若来自于真正publisher的消息大于等于50，则该消息被丢掉
        // 即processor传给真正的subscriber的消息是小于50的消息
        if (item < 50) {
            this.submit("消息已处理：" + item);
        }
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
        System.out.println("处理器已将消息处理完毕");
        this.close();
    }
}
