package com.yw.webflux.example.methodref;

/**
 * @author yangwei
 */
public class Person {
    private String name;
    public Person() {
    }
    public Person(String name) {
        this.name = name;
    }
    // 静态方法
    public static void sleeping(int hours) {
        System.out.println("人每天需要睡眠 " + hours + " 小时");
    }
    // 实例方法：在第一个参数位置存在一个隐藏参数this
//    public String play(Person this, int minutes) {
    public String play(int minutes) {
        return name + "已经玩了" + minutes + "分钟了。";
    }

    public void study(String course) {
        System.out.println(name + "正在学习" + course);
    }

    @Override
    public String toString() {
        return "Person{name = " + name +"}";
    }
}
