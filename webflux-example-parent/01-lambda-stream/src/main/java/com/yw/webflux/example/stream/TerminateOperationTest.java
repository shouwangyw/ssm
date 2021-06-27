package com.yw.webflux.example.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yangwei
 */
public class TerminateOperationTest {
    private static final String WORDS = "Shenzhen is a vibrant city";

    // 对于流的并行操作，forEach()处理结果是无序的
    @Test
    public void test01() {
        Stream.of((WORDS.split(" ")))
                .parallel()
                .forEach(System.out::println);
    }

    // 对于流的并行操作，forEachOrdered()处理结果是无序的
    @Test
    public void test02() {
        Stream.of((WORDS.split(" ")))
                .parallel()
                .forEachOrdered(System.out::println);
    }

    // 将流中的元素收集到一个集合中
    @Test
    public void test03() {
        List<String> list = Stream.of((WORDS.split(" ")))
                .collect(Collectors.toList());
        System.out.println(list);   // [Shenzhen, is, a, vibrant, city]
    }

    // 将流中的元素收集到一个数组中
    @Test
    public void test04() {
        Object[] array = Stream.of((WORDS.split(" ")))
                .toArray();
        System.out.println(Arrays.toString(array)); // [Shenzhen, is, a, vibrant, city]
    }

    // 统计流中元素的个数
    @Test
    public void test05() {
        long count = Stream.of((WORDS.split(" "))).count();
        System.out.println(count); // 5
    }

    // 计算流中所有单词的长度之和
    @Test
    public void test06() {
        Optional<Integer> reduce = Stream.of((WORDS.split(" ")))
                .map(String::length)
                .reduce(Integer::sum);
        // Optional的get()方法在其封装的对象为空时会抛出异常
        System.out.println(reduce.get()); // 22
    }

    // 计算流中所有单词的长度之和
    @Test
    public void test07() {
        Optional<Integer> reduce = Stream.of((WORDS.split(" ")))
                .map(String::length)
//                .filter(n -> n > 200)
                .reduce(Integer::sum);
        // Optional的orElse()方法在正常情况下会返回正常的值
        // 当其封装的对象为空时会返回参数指定的值
        System.out.println(reduce.orElse(-1));
//        System.out.println(reduce);
    }

    // 将流中的元素使用逗号相连接
    @Test
    public void test08() {
        String reduce = Stream.of(WORDS.split(" "))
                .reduce(null, (s1, s2) -> s1 + ", " + s2);
        System.out.println(reduce); // null, Shenzhen, is, a, vibrant, city
    }

    // 从流中找出长度最长的那个单词
    @Test
    public void test09() {
        String max = Stream.of(WORDS.split(" "))
//                .filter(s -> s.length() > 200)
                .max((s1, s2) -> s1.length() - s2.length())
                // 等价于
//                .min((s1, s2) -> s2.length() - s1.length())
                .orElse("当前流中没有元素");
        System.out.println(max);    // Shenzhen
    }

    // 从流中找出长度最短的那个单词
    @Test
    public void test10() {
        String max = Stream.of(WORDS.split(" "))
//                .filter(s -> s.length() > 200)
                .max((s1, s2) -> s2.length() - s1.length())
                // 等价于
//                .min((s1, s2) -> s1.length() - s2.length())
                .orElse("当前流中没有元素");
        System.out.println(max);    // a
    }

    // 判断所有单词长度是否大于3
    // allMatch()：只要找到一个不符合条件的元素马上结束匹配工作
    @Test
    public void test11() {
        boolean allMatch = Stream.of(WORDS.split(" "))
                .allMatch(word -> word.length() > 3);
        System.out.println(allMatch);   // false
    }

    // 判断是否存在单词长度大于3的单词
    // anyMatch()：只要找到一个符合条件的元素马上结束匹配工作，返回true
    @Test
    public void test12() {
        boolean anyMatch = Stream.of(WORDS.split(" "))
                .anyMatch(word -> word.length() > 3);
        System.out.println(anyMatch);   // true
    }

    // 判断是否不存在单词长度大于3的单词
    // noneMatch()：只要找到一个符合条件的元素马上结束匹配工作，返回false
    @Test
    public void test13() {
        boolean noneMatch = Stream.of(WORDS.split(" "))
                .noneMatch(word -> word.length() > 3);
        System.out.println(noneMatch);   // false
    }

    // findFirst()：只要找到第一个元素，马上结束查找
    @Test
     public void test14() {
        String ele = Stream.of(WORDS.split(" "))
                .findFirst()
                .orElse("这里没有一个元素");
        System.out.println(ele);    // Shenzhen
    }

    // findAny()：只要找到任何一个元素，马上结束查找
    @Test
     public void test15() {
        String ele = Stream.of(WORDS.split(" "))
                .findAny()
                .orElse("这里没有一个元素");
        System.out.println(ele);    // Shenzhen
    }
}
