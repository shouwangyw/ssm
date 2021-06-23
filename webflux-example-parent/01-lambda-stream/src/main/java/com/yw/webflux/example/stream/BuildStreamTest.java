package com.yw.webflux.example.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author yangwei
 */
public class BuildStreamTest {
    @Test
    public void test01() {
        // 使用数组创建流
        int[] nums = {1, 2, 3};
        IntStream stream1 = IntStream.of(nums);
        IntStream stream2 = Arrays.stream(nums);

        System.out.println(IntStream.of(nums).sum());   // 6
        System.out.println(Arrays.stream(nums).sum());  // 6
    }

    @Test
    public void test02() {
        // 使用集合创建流
        List<String> list = new ArrayList<>();
        Stream<String> listStream = list.stream();

        Set<Integer> set = new HashSet<>();
        Stream<Integer> setStream = set.stream();
    }

    @Test
    public void test03() {
        // 创建数字流
        // 创建一个包含1, 2, 3的stream
        IntStream intStream = IntStream.of(1, 2, 3);

        // 创建一个包含[1, 5)范围的stream
        IntStream rangeStream = IntStream.range(1, 5);
        // 创建一个包含[1, 5]范围的stream
        IntStream rangeClosedStream = IntStream.rangeClosed(1, 5);
        System.out.println(rangeStream.sum());          // 10
        System.out.println(rangeClosedStream.sum());    // 15

        // new Random().ints() 创建一个无限流，limit(5)限制流中元素个数为5个
        IntStream randomLimitStream = new Random().ints().limit(5);
//        IntStream randomLimitStream = new Random().ints(5);   // 与上面等价
    }

    @Test
    public void test04() {
        // 自定义流: 生成一个无限流，然后限定元素个数
        Stream<Double> stream = Stream.generate(Math::random).limit(5);
        System.out.println(Arrays.toString(stream.toArray()));
    }
}
