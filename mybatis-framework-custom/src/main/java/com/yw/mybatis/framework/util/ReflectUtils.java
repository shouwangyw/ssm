package com.yw.mybatis.framework.util;

/**
 * @author yangwei
 */
public class ReflectUtils {
    private ReflectUtils() {
    }

    public static Class<?> resolveType(String type) {
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
        }
        return null;
    }
}