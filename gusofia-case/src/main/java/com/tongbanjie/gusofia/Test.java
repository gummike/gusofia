package com.tongbanjie.gusofia;

import java.util.concurrent.CountDownLatch;

class Test {
    private volatile int i = 0;

    CountDownLatch countDownLatch = new CountDownLatch(10000);
    public void increase() {
        i++;
    }

    public int getI() {
        return i;
    }


    public static void main(String[] args) throws InterruptedException {
        final Test test = new Test();
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        test.increase();
                        test.countDownLatch.countDown();
                    }
                }
            }).start();
        }
        System.out.println("before");
        test.countDownLatch.await();
        System.out.println("after");
        System.out.println(test.getI());
    }
}