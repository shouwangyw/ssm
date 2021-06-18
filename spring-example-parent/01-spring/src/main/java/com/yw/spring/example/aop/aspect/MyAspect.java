package com.yw.spring.example.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component("myAspect")
@Aspect // 标记该类是一个切面类
public class MyAspect {
    private static final String pointcut = "execution(* *..*.*ServiceImpl.*(..))";

    // @Beafore：标记该方法是一个前置通知
    // value：切入点表达式
    @Before(value = "MyAspect.fn()")
    public void before() {
        System.out.println("这是注解方式的前置通知");
    }

    @AfterReturning(pointcut)
    public void after() {
        System.out.println("这是注解方式的后置通知");
    }

    // 通过@Pointcut定义一个通用的切入点
    @Pointcut("execution(* *..*.*ServiceImpl.*(..))")
    public void fn() {
    }

    @Around(value = "execution(* *..*.*ServiceImpl.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
        Object ret = null;
        try {
            // 获取方法执行所需的参数
            Object[] args = joinPoint.getArgs();
            // 前置通知：开启事务 beginTransaction();
            // 执行方法
            ret = joinPoint.proceed(args);
            // 后置通知：提交事务commit
        } catch (Throwable e) {
            // 异常通知：回滚事务rollback();
        } finally {
            // 最终通知：释放资源release();
        }
        return ret;
    }
}