package com.tts.cp.lib.visit.bean;

/**
 * @author Alley zhao created on 2022/2/7.
 */
// 单例模式
public class EHan {

    private static EHan instance = new EHan(); // 饿汉式

    private EHan() { // 构造方法就是于类同名，且没有返回方法
        System.out.println("这是构造方法");
    } // 私有化构造方法

    public static EHan getInstance() {
        System.out.println("sss");
        return instance;
    }

}
