package com.yw.spring.example.proxy.util;

import com.yw.spring.example.proxy.service.UserService;
import com.yw.spring.example.proxy.service.UserServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理工具类
 *
 * @author yangwei
 */
public class MyProxyUtils {
    /**
     * 使用JDK的方式生成代理对象
     */
    public static UserService getProxy(final UserService service) throws Exception {
        // 使用Proxy类生成代理对象
        UserService userService = (UserService) Proxy.newProxyInstance(
                service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                (proxy, method, args) -> { // InvocationHandler
                    if ("saveUser".equals(method.getName())) {
                        System.out.println("记录日志...");
                        // 开启事务
                    }
                    // 提交事务
                    // 让service类的saveUser或其他方法正常执行
                    return method.invoke(service, args);
                });
        // 返回代理对象
        return userService;
    }

    /**
     * 使用CGLIB的方式生成代理对象
     */
    public static UserService getProxy() {
        // 创建CGLIB的核心类
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(UserServiceImpl.class);
        // 设置回调函数
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args,
                                    MethodProxy methodProxy) throws Throwable {
                if ("saveUser".equals(method.getName())) {
                    System.out.println("记录日志...");
                }
                return methodProxy.invokeSuper(obj, args);
            }
        });
        // 生成代理对象
        return (UserService) enhancer.create();
    }
}