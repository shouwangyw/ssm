package com.yw.spring.example.proxy.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 主要作用就是生成代理类 使用JDK的动态代理实现 它是基于接口实现的
 */
public class JDKProxyFactory implements InvocationHandler {
    // 目标对象的引用
    private Object target;

    // 通过构造方法将目标对象注入到代理对象中
    public JDKProxyFactory(Object target) {
        super();
        this.target = target;
    }

    /**
     * 获取代理对象
     */
    public Object getProxy() {
        // 如何生成一个代理类呢？
        // 1、编写源文件(java文件)----目标类接口interface实现类（调用了目标对象的方法）
        // 2、编译源文件为class文件
        // 3、将class文件加载到JVM中(ClassLoader)
        // 4、将class文件对应的对象进行实例化（反射）

        // Proxy是JDK中的API类
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),  // 第一个参数：目标对象的类加载器
                target.getClass().getInterfaces(),   // 第二个参数：目标对象的接口
                this);                                 // 第三个参数：代理对象的执行处理器
    }

    /**
     * 代理对象会执行的方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method method2 = target.getClass().getMethod("saveUser", null);
        Method method3 = Class.forName("com.sun.proxy.$Proxy4").getMethod("saveUser", null);
        System.out.println("目标对象的方法:" + method2.toString());
        System.out.println("目标接口的方法:" + method.toString());
        System.out.println("代理对象的方法:" + method3.toString());
        System.out.println("这是jdk的代理方法");

        //增强的部分
        // ...

        // 下面的代码，是反射中的API用法
        // 该行代码，实际调用的是[目标对象]的方法
        // 利用反射，调用[目标对象]的方法
        return method.invoke(target, args);
    }
}