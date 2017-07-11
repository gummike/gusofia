package com.tongbanjie.gusofia.thread.join;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author zi.you
 * @date 17/7/11
 */
public class JoinTest {

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        System.out.println("main start ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExecutorService executorService = Executors.newSingleThreadExecutor();
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
                countDownLatch.countDown();
            }
        };
        executorService.submit(runnable);
        executorService.shutdown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end");
    }
}
