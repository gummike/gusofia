package com.tongbanjie.gusofia.thread.thread_pool;

import java.util.concurrent.*;

/**
 * @author zi.you
 * @date 17/7/13
 */
public class Test {
    public static void main(String[] args) {

        int corePoolSize = 1;
        int maximumPoolSize = 1;
        int keepAliveTime = 1;
        BlockingQueue<Runnable> runnableTaskQueue = new ArrayBlockingQueue<Runnable>(1);

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, runnableTaskQueue);

    }
}
