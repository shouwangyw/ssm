package com.yw.webflux.example.lambda.inter;

/**
 * 含有默认方法
 *
 * @author yangwei
 */
@FunctionalInterface
public interface HasDefault {
    String doSome(String a, String b);

    default void doOther(String a, String b) {
        System.out.println("执行默认方法 - " + a + b);
    }
}
