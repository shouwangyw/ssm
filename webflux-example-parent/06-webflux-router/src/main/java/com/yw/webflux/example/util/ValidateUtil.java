package com.yw.webflux.example.util;

import com.yw.webflux.example.exception.StudentException;

import java.util.stream.Stream;

/**
 * @author yangwei
 */
public class ValidateUtil {
    // 指定无效姓名列表
    private static final String[] INVALID_NAME = {"admin", "administrator"};

    // 对姓名进行校验
    public static void validName(String name) {
        Stream.of(INVALID_NAME)
                // 对比的值为true，则通过过滤，该值将继续保留在流中
                .filter(name::equalsIgnoreCase)
                .findAny()  // 返回Optional
                .ifPresent(inName -> {
                    throw new StudentException("name", name, "使用了非法姓名");
                });
    }
}