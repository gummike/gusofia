package com.tongbanjie.gusofia.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class VolatileTest {
    public volatile int inc = 0;

    public static final int threadNum = 10;

    public void increase() {
        inc++;
    }

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {

        final VolatileTest test = new VolatileTest();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum, new Runnable() {
            @Override
            public void run() {
                System.out.println(test.inc);
            }
        });

        for (int i = 0; i < threadNum; i++) {
            final int finalI = i;
            new Thread() {
                public void run() {
                    System.out.println("线程" + finalI + "开始执行");
                    for (int j = 0; j < 1000; j++)
                        test.increase();
                    System.out.println("线程" + finalI + "结束执行");
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }



    }
}