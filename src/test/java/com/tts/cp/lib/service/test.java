package com.tts.cp.lib.service;

/**
 * @author Alley zhao created on 2022/3/18.
 */

class A {
    static {
        System.out.println("A");
    }

    public A() {
        System.out.println("A1");
    }

    {
        System.out.println("A2");
    }
}

class B extends A {
    static {
        System.out.println("B");
    }

    public B() {
        System.out.println("B1");
    }

    public B(String a) { // 构造方法不能被重写，但是可以被重载
        System.out.println("B1.1");
    }

    {
        System.out.println("B2");
    }
}

public class test {
    public static void main(String[] args) {
        A a = new B();
        a = new B();
// 执行顺序： 父静态代码块->子静态代码块->父普通代码块->父构造方法->子普通代码块->子构造方法
    }
}
