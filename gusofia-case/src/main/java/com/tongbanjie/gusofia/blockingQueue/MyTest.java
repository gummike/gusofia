package com.tongbanjie.gusofia.blockingQueue;

import java.util.concurrent.*;

/**
 * @author zi.you
 * @date 17/7/10
 */
public class MyTest extends Thread {
    public static BlockingQueue queue = new ArrayBlockingQueue(3);

    private Integer index;

    public MyTest(Integer index) {
        this.index = index;
    }

    @Override
    public void run() {
        try {
            queue.put(index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("put: " + index);
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            executorService.submit(new MyTest(i));
        }
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        System.out.println("take: " + queue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        executorService.shutdownNow();
    }
}
