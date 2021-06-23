package com.yw.webflux.example.function;

import org.junit.Test;

import java.util.function.Function;

/**
 * @author yangwei
 */
public class FunctionTest {
    @Test
    public void test01() {
        Function<Integer, String> func = n -> "I love you, " + n;
        System.out.println(func.apply(2021));   // I love you, 2021
    }

    @Test
    public void test02() {
        Function<Integer, Integer> func1 = x -> x * 2;
        Function<Integer, Integer> func2 = x -> x * x;

        // 先将 5 作为func1的参数，计算结果为 10；再将 func1 计算结果10作为 func2 的参数再计算
        System.out.println(func1.andThen(func2).apply(5)); // 100
        // 先将 5 作为func2的参数，计算结果为 25；再将 func2 计算结果25作为 func1 的参数再计算
        System.out.println(func1.compose(func2).apply(5)); // 50
    }

    @Test
    public void test03() {
        System.out.println(Function.identity().apply(5));       // 5
        System.out.println(Function.identity().apply(3 * 8));   // 24
    }
}
