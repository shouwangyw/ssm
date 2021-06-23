package com.yw.webflux.example.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author yangwei
 */
public class StreamTest {
    @Test
    public void test01() {
        int[] nums = {1, 2, 3};
        int sum = IntStream.of(nums)
                .map(i -> i * 2)    // 中间操作 {2, 4, 6}
                .map(i -> i * i)    // 中间操作 {4, 16, 36}
                .sum();             // 终止操作 56
        System.out.println(sum);
    }

    @Test
    public void test02() {
        int[] nums = {1, 2, 3};
        int sum = IntStream.of(nums)
                .map(i -> i * 2)    // 中间操作 {2, 4, 6}
                .map(i -> {
                    System.out.println(i + " 进行乘方");
                    return i * i;
                })    // 中间操作 {4, 16, 36}
                .sum();             // 终止操作 56
        System.out.println(sum);
//        2 进行乘方
//        4 进行乘方
//        6 进行乘方
//        56
    }

    @Test
    public void test03() {
        int[] nums = {1, 2, 3};
        int sum = IntStream.of(nums)
                .map(i -> i * 2)    // 中间操作 {2, 4, 6}
                .map(StreamTest::compute)    // 中间操作 {4, 16, 36}
                .sum();             // 终止操作 56
        System.out.println(sum);
    }

    // 静态方法，对指定元素进行乘方
    private static int compute(int n) {
        System.out.println(n + " 进行乘方");
        return n * n;
    }

    @Test
    public void test04() {
        int[] nums = {1, 2, 3};
        IntStream stream = IntStream.of(nums)
                .map(i -> i * 2)    // 中间操作 {2, 4, 6}
                .map(StreamTest::compute);// 中间操作 {4, 16, 36}
        System.out.println(Arrays.toString(nums)); // [1, 2, 3]
    }
}
