package com.tts.cp.lib.visit.bean;

/**
 * @author Alley zhao created on 2022/2/7.
 */
// 单例模式
public class LHan {

    private static LHan instance; // 懒汉式

    private LHan() {
    } // 私有构造方法

    public static LHan getInstance() {
        if (instance == null) {
            instance = new LHan();
        }
        return instance;
    }

}
