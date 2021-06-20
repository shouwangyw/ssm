package com.yw.spring.framework.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author yangwei
 */
public class ReflectUtils {
    public static Object newInstance(Class clazz) {
        // 构造器方式去创建实例
        try {
            // 选择无参构造
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void invokeMethod(Object obj, String methodName) {
        if (methodName == null) {
            return;
        }
        try {
            Class<?> clazz = obj.getClass();
            Method method = clazz.getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(obj);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setField(Object obj, String fieldName, Object fieldValue) {
        try {
            Class<?> clazz = obj.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, fieldValue);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Class<?> getTypeByFieldName(String className, String name) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(name);
            return field.getType();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}