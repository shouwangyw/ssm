package com.yw.webflux.example.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author yangwei
 */
public class MiddleOperationTest {
    // 在所有操作都是无状态时，流中元素对于操作的执行
    //      并非是将流中所有元素按照顺序先执行完一个操作，再让所有元素执行完第二个操作
    //      而是逐个拿出元素，将所有操作执行完毕后，再拿出一个元素，将所有操作再执行完毕
    @Test
    public void test01() {
        String words = "I Love China Welcome";
        Stream.of(words.split(" ")) // 当前流中的元素为各个单词
                .peek(System.out::println)
                .map(String::length) // 当前流中的元素为各个单词的长度
                .forEach(System.out::println);
    }

    @Test
    public void test02() {
        IntStream.range(1, 10)
                .mapToObj(i -> "No." + i)
                .forEach(System.out::println);
    }

    @Test
    public void test03() {
        String[] nums = {"111", "222", "333"};
        Arrays.stream(nums)     // "111", "222", "333"
                .mapToInt(Integer::valueOf)     // 111, 222, 333
                .forEach(System.out::println);
    }

    @Test
    public void test04() {
        String words = "I Love China Welcome";
        Stream.of(words.split(" "))
                // flatMap中参数为Function，且要求返回类型为Stream
                .flatMap(word -> word.chars().boxed())
//                .forEach(System.out::print);
                .forEach(c -> System.out.print((char) (c.intValue())));
    }

    @Test
    public void test05() {
        String words = "I Love China Welcome";
        Stream.of(words.split(" "))
                // 最终形成的流中的元素为各个单词的字母
                .flatMap(word -> Stream.of(word.split("")))
                .forEach(System.out::println);
    }

    @Test
    public void test06() {
        String words = "I Love China Welcome";
        Stream.of(words.split(" "))
                // 当过滤条件为true时，当前元素会保留在流中，否则从流中删除
                .filter(word -> word.length() > 4)
                .forEach(System.out::println);
    }

    @Test
    public void test07() {
        String words = "I Love China Welcome";
        Stream.of(words.split(" "))
                .flatMap(word -> Stream.of(word.split("")))
                .distinct() // 取出重复字母
                .sorted()   // 按照字典序进行排序
                .forEach(System.out::println);
    }

    @Test
    public void test08() {
        String words = "I Love China Welcome";
        Stream.of(words.split(" "))
                .flatMap(word -> Stream.of(word.split("")))
                .distinct()
                // 按照逆字典序进行排序
                .sorted((o1, o2) -> (int) o2.charAt(0) - (int) o1.charAt(0))
                .forEach(System.out::println);
    }

    @Test
    public void test09() {
        String words = "I Love China Welcome";
        Stream.of(words.split(" "))
                .flatMap(word -> Stream.of(word.split("")))
                .distinct()
                .sorted()
                .skip(4)    // 指定跳过(去除)4个元素
                .forEach(System.out::print);

        Stream.of(words.split(" "))
                .skip(3)
                .forEach(System.out::println);
    }

    @Test
    public void test10() {
        String words = "I Love China Welcome";
        Stream.of(words.split(" "))
                .flatMap(word -> Stream.of(word.split("")))
                .distinct()
                .peek(System.out::print)
                .sorted()
                .forEach(System.err::print);
    }
}
