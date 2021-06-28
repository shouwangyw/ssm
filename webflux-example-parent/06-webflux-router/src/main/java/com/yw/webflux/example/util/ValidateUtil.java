package com.yw.webflux.example.util;

import com.yw.webflux.example.dao.po.Student;
import com.yw.webflux.example.exception.StudentException;
import org.springframework.util.StringUtils;

import java.util.stream.Stream;

/**
 * @author yangwei
 */
public class ValidateUtil {
    // 指定无效姓名列表
    private static final String[] INVALID_NAMES = {"admin", "administrator"};
    // 非法年龄上下限
    private static final int[] INVALID_AGES = {15, 80};

    // 对整个Student对象进行校验
    public static void validStudent(Student student) {
        String name = student.getName();
        int age = student.getAge();

        // 姓名为空验证
        if (StringUtils.isEmpty(name)) {
            throw new StudentException("name", "为null或空串", "姓名不能为空");
        }

        // 姓名非法验证
        Stream.of(INVALID_NAMES)
                // 对比的值为true，则通过过滤，该值将继续保留在流中
                .filter(name::equalsIgnoreCase)
                .findAny()  // 返回Optional
                .ifPresent(invalidName -> {
                    throw new StudentException("name", invalidName, "使用了非法姓名");
                });

        // 年龄非法验证
        if (age < INVALID_AGES[0] || age > INVALID_AGES[1]) {
            String msg = "年龄不在(" + INVALID_AGES[0] + "," + INVALID_AGES[1] + ")范围";
            throw new StudentException("age", age, msg);
        }
    }
}