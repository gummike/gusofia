package com.tongbanjie.gusofia.thread;

public class Test {
    public volatile int inc = 0;

    public void increase() {
        inc++;
    }

    public static void main(String[] args) throws InterruptedException {
        final Test test = new Test();
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    for (int j = 0; j < 1000; j++)
                        test.increase();
                }

                ;
            }.start();
        }

//        while (Thread.activeCount() > 1)  //保证前面的线程都执行完
//            Thread.yield();
        Thread.sleep(5000);
        System.out.println(test.inc);
    }
}