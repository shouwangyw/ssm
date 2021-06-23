package com.yw.springboot.example.service;

import lombok.AllArgsConstructor;

/**
 * @author yangwei
 */
@AllArgsConstructor
public class Wrapper {
    private String prefix;
    private String suffix;

    /**
     * 当前starter的核心功能实现方法
     */
    public String wrap(String word) {
        return prefix + word + suffix;
    }
}
