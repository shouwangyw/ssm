package com.yw.webflux.example.function;

import org.junit.Test;

import java.util.function.UnaryOperator;

/**
 * @author yangwei
 */
public class UnaryOperaterTest {
    @Test
    public void test01() {
        UnaryOperator<String> uo = n -> "I love you, " + n;
        System.out.println(uo.apply("HuBei")); // I love you, HuBei
    }

    @Test
    public void test02() {
        UnaryOperator<Integer> uo1 = x -> x * 2;
        UnaryOperator<Integer> uo2 = x -> x * x;

        // 先将 5 作为uo1的参数，计算结果10，再讲uo1计算结果10作为uo2的参数再计算
        System.out.println(uo1.andThen(uo2).apply(5)); // 100
        // 先将 5 作为uo2的参数，计算结果25，再讲uo2计算结果10作为uo1的参数再计算
        System.out.println(uo1.compose(uo2).apply(5)); // 50
    }

    @Test
    public void test03() {
        System.out.println(UnaryOperator.identity().apply(5)); // 5
        System.out.println(UnaryOperator.identity().apply(3 * 8)); // 24
    }
}
