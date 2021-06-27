package com.yw.webflux.example.stream;

import org.junit.Test;

import java.util.stream.IntStream;

/**
 * 循环打印ABC
 * @author yangwei
 */
public class CyclePrint {
    @Test
    public void testPrintABC() {
        IntStream.rangeClosed(1, 100)
                .peek(CyclePrint::printA)
                .peek(CyclePrint::printB)
                .peek(CyclePrint::printC)
                .count();
    }

    private static void printA(int n) {
        System.out.println(n + " A -- " + Thread.currentThread().getName());
    }

    private static void printB(int n) {
        System.out.println(n + " B -- " + Thread.currentThread().getName());
    }

    private static void printC(int n) {
        System.out.println(n + " C -- " + Thread.currentThread().getName());
    }
}
