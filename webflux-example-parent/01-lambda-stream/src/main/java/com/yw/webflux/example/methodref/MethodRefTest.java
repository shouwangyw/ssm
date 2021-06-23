package com.yw.webflux.example.methodref;

import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author yangwei
 */
public class MethodRefTest {
    @Test
    public void test01() {
        Person person = new Person("张三");
        System.out.println(person.play(5)); // 张三已经玩了5分钟了。
        person.study("WebFlux");    // 张三正在学习WebFlux
    }

    @Test
    public void test02() {
        // Lambda静态方法引用   类名::静态方法名
        // sleeping()方法只有一个输入，没有输出，符合函数式接口Consumer的定义
        Consumer<Integer> consumer = Person::sleeping;
        consumer.accept(8); // 相当于  Person.sleeping(8)
    }

    @Test
    public void test03() {
        // Lambda实例方法引用   实例名::实例方法名
        Person person = new Person("李四");
        // play()方法只有一个输入，且有输出，符合函数式接口Function的定义
        Function<Integer, String> func = person::play;
        System.out.println(func.apply(5));  // 相当于person.play(5)
    }

    @Test
    public void test04() {
        // Lambda实例方法引用   类名::实例方法名
        Person person = new Person("李四");
        BiFunction<Person, Integer, String> bf = Person::play;
        System.out.println(bf.apply(person, 5));  // 相当于person.play(5)
    }

    @Test
    public void test05() {
        // Lambda实例方法引用   实例名::实例方法名
        Person person = new Person("李四");
        // study()方法只有一个输入，没有输出，符合函数式接口Consumer的定义
        Consumer<String> consumer = person::study;
        consumer.accept("WebFlux");    // 相当于person.study("WebFlux")
    }

    @Test
    public void test06() {
        // Lambda无参构造器引用   类名::new
        // 无参构造器是没有输入，但有输出，其符合函数式接口Supplier的定义
        Supplier<Person> supplier = Person::new;
        System.out.println(supplier.get());  // 相当于 new Person
    }

    @Test
    public void test07() {
        // Lambda 带参构造器引用   类名::new
        Function<String, Person> func = Person::new;
        System.out.println(func.apply("王五"));
    }
}
