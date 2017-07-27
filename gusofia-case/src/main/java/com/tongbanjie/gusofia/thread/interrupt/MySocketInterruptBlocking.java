package com.tongbanjie.gusofia.thread.interrupt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author zi.you
 * @date 17/7/12
 */
public class MySocketInterruptBlocking extends Thread {

    private volatile boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        MySocketInterruptBlocking thread = new MySocketInterruptBlocking();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> submit = executorService.submit(thread);
//        thread.start();
        Thread.sleep(3000);
        // 能停止非阻塞的线程，是阻塞线程跑出异常
//        thread.interrupt();
        // 不能停止阻塞线程的方式，因为现在阻塞了 不会去读取stop变量
        thread.setStop(true);
        executorService.shutdown();
        System.out.println("main end");
    }

    @Override
    public void run() {
        while (!stop) {
            System.out.println("running");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
                return;
            }
        }
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
