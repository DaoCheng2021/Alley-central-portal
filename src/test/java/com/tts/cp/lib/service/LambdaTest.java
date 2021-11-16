package com.tts.cp.lib.service;

import java.util.function.Consumer;

/**
 * @author Alley zhao created on 2021/9/9.
 */
public class LambdaTest {

    public static void main(String[] args) {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println("普通Runnable方法");
            }
        };
        runnable.run();

    }

    public static void test2(){
        Runnable runnable=()-> System.out.println("Lambda表达式");
        runnable.run();
    }
    public static void test3(){
        Consumer<String>consumer=e-> System.out.println("e");
        consumer.accept("test3传参数");
    }
    public static void test4(){

    }

}
