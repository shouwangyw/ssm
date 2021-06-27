package com.yw.webflux.example.util;

import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 */
public class Timer {
    private long startTime;

    public Timer() {
        startTime = System.currentTimeMillis();
    }

    public long elapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public static void sleep(int timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (Exception ignore) {
        }
    }
}
