package com.tts.cp.lib.service;

public class WaitNotifyTest {
    public static void main(String[] args) throws Exception {
        /* 叫醒线程
        // current 获取当前线程
        Thread thread = Thread.currentThread();
        String name = thread.getName();
        long id = thread.getId();
        long id2 = thread.getId();
        int priority = thread.getPriority(); // 获取优先级
        boolean alive = thread.isAlive(); // 是否存活
        boolean daemon = thread.isDaemon(); //是否守护线程
        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "睡眠1");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "睡眠1醒了");
            }
        });
        thread1.start();
        Thread.sleep(200);
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" 叫醒--");
            thread1.interrupt(); // 叫醒线程
        }).start();
        */
        /* wait睡眠和notifyAll & notify叫醒睡眠的线程使用 notifyAll是叫醒所有睡眠的线程
        WaitNotifyTest waitNotifyTest = new WaitNotifyTest();
        new Thread(() -> {
            try {
                waitNotifyTest.printFile();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                System.out.println(new Date()+"\t"+Thread.currentThread().getName()+"\t睡觉一秒，目的是让上面的线程先执行，即先执行wait()");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitNotifyTest.notifyPrint();
        }).start();
    }
    private synchronized void printFile() throws InterruptedException {
        System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\t等待打印文件");
        this.wait();
        System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\t打印完成");
    }

    private synchronized void notifyPrint() {
        this.notifyAll();
        System.out.println(new Date() + "\t" + Thread.currentThread().getName() + "\t通知完成");
    }
    */
        /*
        Object lock = new Object();
        new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "+" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        Thread.sleep(1000);
        new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "+" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        */
    }

}

