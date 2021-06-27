package com.yw.webflux.example.stream;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author yangwei
 */
public class SerialParallelTest {
    @Test
    public void test01() {
        // 串行处理
        IntStream.range(1, 100)
//                .sequential()   // 默认
                .peek(SerialParallelTest::printA)
                .count();
    }

    @Test
    public void test02() {
        // 并行处理
        IntStream.range(1, 100)
                .parallel()
                .peek(SerialParallelTest::printA)
                .count();
    }

    @Test
    public void test03() {
        // 串并行混合处理：先并行后串行，最终执行效果为后者：串行处理
        IntStream.range(1, 100)
                .parallel()
                .peek(SerialParallelTest::printA)
                .sequential()
                .peek(SerialParallelTest::printB)
                .count();
    }

    @Test
    public void test04() {
        // 串并行混合处理：先串行后并行，最终执行效果为后者：并行处理
        IntStream.range(1, 100)
                .sequential()
                .peek(SerialParallelTest::printA)
                .parallel()
                .peek(SerialParallelTest::printB)
                .count();
    }

    @Test
    public void test05() {
        // 串行处理仅有一个main线程
        IntStream.range(1, 100)
//                .sequential()
                .peek(SerialParallelTest::printThread)
                .count();
    }

    @Test
    public void test06() {
        // 并行处理的默认线程数量
        IntStream.range(1, 100)
                .parallel()
                .peek(SerialParallelTest::printThread)
                .count();
    }

    @Test
    public void test07() {
        // 修改默认线程池中的线程数量
        // 指定默认线程池中的数量为32，其中包含指定的31个ForkJoinPool.commonPool-worker与main线程
        String key = "java.util.concurrent.ForkJoinPool.common.parallelism";
        System.setProperty(key, "31");
        IntStream.range(1, 100)
                .parallel()
                .peek(SerialParallelTest::printThread)
                .count();
    }

    @Test
    public void test08() {
        // 使用自定义线程池
        // 创建线程池，包含4个线程
        ForkJoinPool pool = new ForkJoinPool(4);
        // 定义并行任务
        Callable<Long> task = () -> IntStream.range(1, 100)
                .parallel()
                .peek(SerialParallelTest::printThread)
                .count();
        // 想线程池提交并行任务
        pool.submit(task);
        // 将pool阻塞，即会阻塞main线程的执行
        synchronized (pool) {
            try {
                pool.wait();
            } catch (Exception ignore) {
            }
        }
    }

    private static void printA(int x) {
        System.out.println(x + " A");
        sleep();
    }

    private static void printB(int x) {
        System.out.println(x + " B");
        sleep();
    }

    private static void printThread(int x) {
        String name = Thread.currentThread().getName();
        System.out.println(x + " -- " + name);
        sleep();

    }

    private static void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (Exception ignore) {
        }
    }
}
