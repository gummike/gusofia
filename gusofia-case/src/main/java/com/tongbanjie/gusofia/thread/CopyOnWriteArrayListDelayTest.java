package com.tongbanjie.gusofia.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zi.you
 * @date 17/5/5
 */
public class CopyOnWriteArrayListDelayTest {
    private CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>();
    private Integer THREAD_POOL_SIZE = 10;

    public static void main(String[] args) throws InterruptedException {
        new CopyOnWriteArrayListDelayTest().start();
    }

    private void start() throws InterruptedException {
        initData();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        executorService.execute(new Reader());
        Thread.sleep(5000);
        executorService.execute(new Writer());
//        Thread.sleep(1000);
//        System.out.println(list.size());
//        executorService.execute(new Reader2());
    }

    private void initData() {
        list.add(1);
        list.add(2);
        list.add(3);
    }

    private class Reader implements Runnable {

        @Override
        public void run() {
            List<Integer> ll = list;
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + ":" + "=========第" + i + "次循环========");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Integer integer : ll) {
                    System.out.println(Thread.currentThread().getName() + ":" + integer);
                }
            }

        }
    }

    private class Reader2 implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + ":" + "====Reader2=====第" + i + "次循环========");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Integer integer : list) {
                    System.out.println(Thread.currentThread().getName() + ":" + integer);
                }
            }

        }
    }

    private class Writer implements Runnable {

        @Override
        public void run() {
            for (int i = 100; i < 110; i++) {
                System.out.println(i);
                list.add(i);
            }
        }
    }
}
