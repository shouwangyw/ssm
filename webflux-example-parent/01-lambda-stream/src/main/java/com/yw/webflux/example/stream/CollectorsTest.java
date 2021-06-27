package com.yw.webflux.example.stream;

import org.apache.commons.collections4.MapUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yangwei
 */
public class CollectorsTest {
    private List<Student> students;

    @Before
    public void before() {
        students = Arrays.asList(
                new Student("周零", "清华大学", "男", 20, 95.5),
                new Student("郑一", "清华大学", "女", 21, 98.5),
                new Student("吴二", "北京大学", "男", 22, 91.0),
                new Student("张三", "复旦大学", "男", 23, 90.5),
                new Student("李四", "清华大学", "女", 21, 92.0),
                new Student("王五", "北京大学", "男", 23, 93.5),
                new Student("赵六", "清华大学", "男", 22, 96.5),
                new Student("钱七", "复旦大学", "女", 20, 97.5),
                new Student("秦八", "复旦大学", "男", 21, 99.0),
                new Student("段九", "北京大学", "男", 20, 92.5)
        );
    }

    // 获取所有学生姓名列表
    @Test
    public void test01() {
        List<String> names = students.stream().map(Student::getName)
                .collect(Collectors.toList());
        System.out.println(names);
    }

    // 获取所有学校名单
    @Test
    public void test02() {
        Set<String> schools = students.stream().map(Student::getSchool)
                .collect(Collectors.toSet());
        // 或者
//        Set<String> schools = students.stream()
//                .map(Student::getSchool)
//                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(schools);
    }

    // 获取各个学校的学生（按照学校进行分组）
    @Test
    public void test03() {
        Map<String, List<Student>> schoolGroup = students.stream()
                .collect(Collectors.groupingBy(Student::getSchool));
        // 显示格式可读性很差
        System.out.println(schoolGroup);

        // 使用工具类显示map中的Key-Value
        MapUtils.verbosePrint(System.out, "学校", schoolGroup);

        // 获取key为“清华大学”的value，即获取所有清华大学的选手
        System.out.println(schoolGroup.get("清华大学"));
    }

    // 统计各个学校人数
    @Test
    public void test04() {
        Map<String, Long> schoolCount = students.stream()
                .collect(Collectors.groupingBy(Student::getSchool, Collectors.counting()));
        System.out.println(schoolCount);
        // 获取清华大学人数
        System.out.println(schoolCount.get("清华大学"));
    }

    // 按照性别分组
    @Test
    public void test05() {
        Map<String, List<Student>> genderGroup = students.stream()
                .collect(Collectors.groupingBy(Student::getGender));
        MapUtils.verbosePrint(System.out, "性别", genderGroup);
        // 获取所有男生
        System.out.println(genderGroup.get("男"));
    }

    // 按照性别分组
    @Test
    public void test06() {
        Map<Boolean, List<Student>> genderGroup = students.stream()
                .collect(Collectors.partitioningBy(s -> "男".equals(s.getGender())));
        MapUtils.verbosePrint(System.out, "性别", genderGroup);

        // 获取所有男生
        System.out.println(genderGroup.get(true));
    }

    // 以95为标准按照成绩将所有参赛选手分组，分为大于95的组及不大于95的组
    @Test
    public void test07() {
        Map<Boolean, List<Student>> genderGroup = students.stream()
                .collect(Collectors.partitioningBy(s -> s.getScore() > 95));
        MapUtils.verbosePrint(System.out, "95划分成绩", genderGroup);
        // 获取所有成绩大于95的学生
        System.out.println(genderGroup.get(true));
    }

    // 以95为标准按照成绩将所有参赛选手分组，分为大于95的组及不大于95的组
    // 对这两组分别计算其平均分
    @Test
    public void test08() {
        Map<Boolean, Double> scoreGroupAvg = students.stream()
                .collect(Collectors.partitioningBy(s -> s.getScore() > 95, Collectors.averagingDouble(Student::getScore)));
        System.out.println(scoreGroupAvg);
        // 获取所有成绩大于95的所有学生的平均分
        System.out.println(scoreGroupAvg.get(true));
    }

    // 获取成绩相关的统计数据
    @Test
    public void test09() {
        DoubleSummaryStatistics scoreSummary = students.stream()
                .collect(Collectors.summarizingDouble(Student::getScore));
        System.out.println(scoreSummary);
        // 输出成绩的数量
        System.out.println("成绩个数：" + scoreSummary.getCount());
        // 输出成绩中的最小值
        System.out.println("最小成绩：" + scoreSummary.getMax());
    }
}
