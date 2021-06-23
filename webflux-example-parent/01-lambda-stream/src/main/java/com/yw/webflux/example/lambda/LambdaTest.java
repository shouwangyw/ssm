package com.yw.webflux.example.lambda;

import com.yw.webflux.example.lambda.inter.HasDefault;
import com.yw.webflux.example.lambda.inter.HasParamHasReturn;
import com.yw.webflux.example.lambda.inter.NoParamHasReturn;
import com.yw.webflux.example.lambda.inter.NoParamNoReturn;
import org.junit.Test;

/**
 * @author yangwei
 */
public class LambdaTest {
    @Test
    public void test01() {
        new NoParamNoReturn() {
            @Override
            public void doSome() {
                System.out.println("使用匿名内部类实现");
            }
        }.doSome();

        NoParamNoReturn lambda = () -> System.out.println("使用Lambda实现");
        lambda.doSome();
    }

    @Test
    public void test02() {
        System.out.println(new NoParamHasReturn() {
            @Override
            public String doSome() {
                return "匿名内部类";
            }
        }.doSome());

        NoParamHasReturn lambda = () -> "Lambda";
        System.out.println(lambda.doSome());
    }

    @Test
    public void test03() {
        System.out.println(new HasParamHasReturn() {
            @Override
            public String doSome(String a, int b) {
                return a + b;
            }
        }.doSome("Hello @FunctionalInterface, ", 2021));

        HasParamHasReturn lambda = (str, n) -> str + n;
        System.out.println(lambda.doSome("Hello Lambda, ", 2021));
    }

    @Test
    public void test04() {
        HasDefault lambda = (a, b) -> a + b;
        System.out.println(lambda.doSome("Hello ", "Lambda"));
        lambda.doOther("Hello", "default");
    }
}
