package wcy.godinsec.wcy_dandan.presenter.impl;

import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/5/2 16:18
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class Student {
    public String name;
    protected int age;
    char sex = '男';
    private String phoneNum;


    //**************构造方法***************//
    //（默认的构造方法）
    Student(String str) {
        LogUtils.e("(默认)的构造方法 s = " + str);
    }

    //无参构造方法
    public Student() {
        LogUtils.e("调用了公有、无参构造方法执行了。。。");
    }

    //有一个参数的构造方法
    public Student(char name) {
        LogUtils.e("姓名 Char ：" + name);
    }

    //有一个参数的构造方法
    private Student(String name, String sex) {
        LogUtils.e("姓名 String ：" + name + " 性别 : " + sex);
    }

    //有多个参数的构造方法
    public Student(String name, int age) {
        LogUtils.e("姓名：" + name + "年龄：" + age);//这的执行效率有问题，以后解决。
    }

    //受保护的构造方法
    protected Student(boolean n) {
        LogUtils.e("受保护的构造方法 n = " + n);
    }

    //私有构造方法
    private Student(int age) {
        LogUtils.e("私有的构造方法   年龄：" + age);
    }

















    //**************成员方法***************//
    public void show1(String s) {
        LogUtils.e("调用了：公有的，String参数的show1(): s = " + s);
    }

    protected void show2() {
        LogUtils.e("调用了：受保护的，无参的show2()");
    }

    void show3() {
        LogUtils.e("调用了：默认的，无参的show3()");
    }

    private String show4(int age) {
        LogUtils.e("调用了，私有的，并且有返回值的，int参数的show4(): age = " + age);
        return "abcd";
    }









    private static void main(String[] strings)
    {
        LogUtils.e("调用了，公共的，参数为可变长参数的值 = main" );
    }








    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
