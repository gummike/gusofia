package com.tongbanjie.gusofia.thread.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadCachedPoolTest {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 1; i < 50; i++) {
            final int taskID = i;
            threadPool.execute(new Runnable() {
                public void run() {
                    for (int i = 1; i < 50; i++) {
                        try {
                            Thread.sleep(20);// 为了测试出效果，让每次任务执行都需要一定时间  
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("第" + taskID + "次任务的第" + i + "次执行， ThreadId: "+ Thread.currentThread().getId());
                    }
                }
            });
        }
        threadPool.shutdown();// 任务执行完毕，关闭线程池  
    }
}  