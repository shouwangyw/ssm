package com.yw.webflux.example.util;

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
}
