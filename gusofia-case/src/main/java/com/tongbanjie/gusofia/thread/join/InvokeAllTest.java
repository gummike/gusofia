package com.tongbanjie.gusofia.thread.join;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InvokeAllTest {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newCachedThreadPool();

        ArrayList<Callable<Integer>> callers = new ArrayList<Callable<Integer>>();

        int len = 200;
        final Random rnd = new Random(System.currentTimeMillis());
        for (int i = 0; i < len; i++) {
            callers.add(new Callable() {
                public Object call() throws Exception {
                    String name = Thread.currentThread().getName();
                    int num = rnd.nextInt(10);
                    //如果生成的随机数大于5，就抛出个异常，  
                    //但此异常并不会被主程序catch到，谁能告诉我为什么？？？  
                    if (num > 5) {
                        System.out.println("exception:----");
                        throw new InterruptedException(name);
                    }
                    Thread.sleep(10000);
                    System.out.println(name);
                    return null;
                }
            });
        }

        pool.invokeAll(callers);

        System.out.println("done!");
        pool.shutdown();
    }
}  