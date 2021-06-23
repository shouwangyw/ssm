package com.yw.webflux.example.function;

import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author yangwei
 */
public class BiFunctionTest {
    @Test
    public void test01() {
        BiFunction<Integer, Integer, String> biFunc = (x, y) -> x + " : " + y;
        System.out.println(biFunc.apply(3, 5)); // 3 : 5
    }

    @Test
    public void test02() {
        BiFunction<Integer, Integer, String> biFunc = (x, y) -> x + " : " + y;
        UnaryOperator<String> uo = s -> "键值对为 " + s;
        // 将(3, 5)应用于biFunc上，再讲biFunc的运算结果作为uo的参数进行运算
        System.out.println(biFunc.andThen(uo).apply(3, 5)); // 键值对为 3 : 5
    }

    @Test
    public void test03() {
        BiFunction<Integer, Integer, Integer> biFunc = (x, y) -> x + y;
        Function<Integer, String> func = n -> "结果为：" + n;
        // 将(3, 5)应用于biFunc上，再讲biFunc的运算结果作为func的参数进行运算
        System.out.println(biFunc.andThen(func).apply(3, 5)); // 结果为：8
    }
}
