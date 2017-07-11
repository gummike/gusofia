package com.tongbanjie.gusofia.thread.thread_pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadScheduledPoolTest {
    public static void main(String[] args) {
        ScheduledExecutorService schedulePool = Executors.newScheduledThreadPool(1);
        // 5秒后执行任务
        schedulePool.schedule(new Runnable() {
            public void run() {
                System.out.println("爆炸1");
            }
        }, 5, TimeUnit.SECONDS);

        // 5秒后执行任务，以后每2秒执行一次
        schedulePool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("爆炸2-"+Thread.currentThread().getId());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 5, 2, TimeUnit.SECONDS);

        // 5秒后执行任务，以后每次延时（2秒+线程执行时间）执行一次
        schedulePool.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("爆炸3"+Thread.currentThread().getId());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },5, 2, TimeUnit.SECONDS);
    }
}  