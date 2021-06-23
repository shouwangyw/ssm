package com.yw.webflux.example.function;

import org.junit.Test;

import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * @author yangwei
 */
public class PredicateTest {
    @Test
    public void test01() {
        // Predicate.test()用法
        Predicate<Integer> pre = i -> i > 8;
        System.out.println(pre.test(9));    // true
        System.out.println(pre.test(7));    // false

        IntPredicate intPre = i -> i < 3;
        System.out.println(intPre.test(2));     // true
        System.out.println(intPre.test(5));     // false

        DoublePredicate doublePre = n -> n < 5;
        System.out.println(doublePre.test(5.2));    // false
        System.out.println(doublePre.test(4.8));    // true
    }

    @Test
    public void test02() {
        // Predicate默认（与或非）方法的用法
        Predicate<Integer> gt8 = i -> i > 8;
        Predicate<Integer> lt3 = i -> i < 3;

        System.out.println(gt8.and(lt3).test(9));   // false
        System.out.println(gt8.or(lt3).test(9));    // true
        System.out.println(gt8.negate().test(9));   // false
    }

    @Test
    public void test() {
        System.out.println(Predicate.isEqual("Hello").test("hello"));   // false
        System.out.println(Predicate.isEqual("Hello").test("Hello"));   // true
    }
}
