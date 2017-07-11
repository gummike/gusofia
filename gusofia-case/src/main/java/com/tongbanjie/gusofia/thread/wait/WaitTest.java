package com.tongbanjie.gusofia.thread.wait;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zi.you
 * @date 17/7/11
 */
public class WaitTest {
    public static void main(String[] args) {
        Test test = new Test();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.submit(test);
        }

    }
}

