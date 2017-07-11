package com.tongbanjie.gusofia.thread.join;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zi.you
 * @date 17/7/11
 */
public class JoinTest2 {

    public static void main(String[] args) {
        System.out.println("main start ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExecutorService executorService = Executors.newCachedThreadPool();

        Callable<Integer> runnable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
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
                return null;
            }
        };
        ArrayList<Callable<Integer>> callers = new ArrayList<Callable<Integer>>();
        callers.add(runnable);
        try {
            executorService.invokeAll(callers);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

        System.out.println("main end");
    }
}
