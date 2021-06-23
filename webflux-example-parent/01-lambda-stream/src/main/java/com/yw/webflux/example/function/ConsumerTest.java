package com.yw.webflux.example.function;

import org.junit.Test;

import java.util.function.Consumer;

/**
 * @author yangwei
 */
public class ConsumerTest {
    @Test
    public void test01() {
        Consumer<String> consumer = s -> System.out.println("Hello, " + s);
        consumer.accept("Consumer");    // Hello, Consumer
    }

    @Test
    public void test02() {
        Consumer<Integer> c1 = n -> System.out.println(n * 2);
        Consumer<Integer> c2 = n -> System.out.println(n * n);
        c1.andThen(c2).accept(5); // 10 25
    }
}
