package com.yw.webflux.example.lambda.inter;

/**
 * 无参数有返回值
 *
 * @author yangwei
 */
@FunctionalInterface
public interface NoParamHasReturn {
    String doSome();
}