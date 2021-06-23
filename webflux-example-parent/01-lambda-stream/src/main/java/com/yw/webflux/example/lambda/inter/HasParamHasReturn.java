package com.yw.webflux.example.lambda.inter;

/**
 * 有参数有返回值
 *
 * @author yangwei
 */
@FunctionalInterface
public interface HasParamHasReturn {
    String doSome(String a, int b);
}