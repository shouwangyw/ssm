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

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception ignore) {
        }
    }

    public static void sleepMills(int millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (Exception ignore) {
        }
    }
}
