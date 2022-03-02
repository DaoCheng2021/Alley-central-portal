package com.tts.cp.lib.service;

/**
 * @author Alley zhao created on 2022/3/2.
 */
public class TestThread implements Runnable {


    public static void main(String[] args) {
        TestThread testThread = new TestThread();
        Thread thread1 = new Thread(testThread, "Thread 1 ");
        Thread thread2 = new Thread(testThread, "Thread 2 ");
        thread1.start();
        thread2.start();
    }

    @Override
    public void run() {
//        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "-" + i);
            }
//        }
    }
}
