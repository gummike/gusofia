package com.tongbanjie.gusofia.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyBlockingQueue_Offer_Poll extends Thread {
//    public static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(3);
    public static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);
    private int index;

    public MyBlockingQueue_Offer_Poll(int i) {
        this.index = i;
    }

    public void run() {
        try {
            boolean isSuc = queue.offer(String.valueOf(this.index), 10, TimeUnit.SECONDS);
            if (isSuc) {
                System.out.println("{" + this.index + "} in queue!");
            } else {
                System.out.println("{" + this.index + "} in queue faillllll !");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            service.submit(new MyBlockingQueue_Offer_Poll(i));
        }
        Thread thread = new Thread() {
            public void run() {
                try {
                    while (true) {
//                        Thread.sleep((int) (Math.random() * 1000));
//                        if (MyBlockingQueue_Offer_Poll.queue.isEmpty())
//                            break;
                        String str = MyBlockingQueue_Offer_Poll.queue.take();
                        System.out.println(str + " has take!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        service.submit(thread);
        service.shutdown();
    }
}  

