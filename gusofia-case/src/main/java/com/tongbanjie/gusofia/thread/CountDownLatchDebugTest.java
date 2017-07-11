package com.tongbanjie.gusofia.thread;

import java.util.concurrent.CountDownLatch;


public class CountDownLatchDebugTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        System.out.println("线程执行开始。。。。");
        countDownLatch.await();
        System.out.println("线程执行结束。。。。");
    }
}  