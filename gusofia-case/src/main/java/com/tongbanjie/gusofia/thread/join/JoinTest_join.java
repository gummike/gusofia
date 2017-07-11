package com.tongbanjie.gusofia.thread.join;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zi.you
 * @date 17/7/11
 */
public class JoinTest_join {

    public static void main(String[] args) {
        System.out.println("main start ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread runnable = new Thread() {
            @Override
            public void run() {
                System.out.println("thread start");
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread " + i);
                }
                System.out.println("thread end");
            }
        };
        runnable.start();
        try {
            // 不是Executors 可以用join
            runnable.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end");
    }
}
