package com.tongbanjie.gusofia.thread.interrupt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zi.you
 * @date 17/7/12
 */
public class MySocketInterrupt extends Thread {

    private volatile boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        MySocketInterrupt thread = new MySocketInterrupt();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(thread);
        Thread.sleep(3000);
        // 不能停止非阻塞的线程
        thread.interrupt();
        // 停止非阻塞线程的方式
//        thread.setStop(true);
        executorService.shutdown();
        System.out.println("main end");
    }

    @Override
    public void run() {
        while (!stop) {
            System.out.println("running");
        }
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
