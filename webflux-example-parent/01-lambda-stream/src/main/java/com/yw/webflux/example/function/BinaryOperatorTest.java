package com.yw.webflux.example.function;

import com.yw.webflux.example.function.po.Student;
import com.yw.webflux.example.function.po.StudentComparator;
import org.junit.Test;

import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * @author yangwei
 */
public class BinaryOperatorTest {
    @Test
    public void test01() {
        BinaryOperator<Integer> bo = (x, y) -> x * y;
        System.out.println(bo.apply(3, 5)); // 15
    }

    @Test
    public void test02() {
        BinaryOperator<Integer> bo = (x, y) -> x * y;
        Function<Integer, String> func = n -> "结果为：" + n;
        // 将(3, 5)应用于bo上，再将bo的运算结果作为func的参数进行运算
        System.out.println(bo.andThen(func).apply(3, 5));   // 结果为：15
    }

    @Test
    public void test03() {
        Student stu3 = new Student("张三", 23);
        Student stu4 = new Student("李四", 24);

        StudentComparator comparator = new StudentComparator();
        Student minStu = BinaryOperator.minBy(comparator).apply(stu3, stu4);
        Student maxStu = BinaryOperator.maxBy(comparator).apply(stu3, stu4);

        System.out.println(minStu); // Student(name=张三, age=23)
        System.out.println(maxStu); // Student(name=李四, age=24)
    }
}
