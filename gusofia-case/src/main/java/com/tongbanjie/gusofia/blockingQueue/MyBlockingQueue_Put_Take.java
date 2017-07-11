package com.tongbanjie.gusofia.blockingQueue;

import java.util.concurrent.*;

public class MyBlockingQueue_Put_Take extends Thread {
//    public static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(3);
    public static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);
    private int index;

    public MyBlockingQueue_Put_Take(int i) {
        this.index = i;
    }

    public void run() {
        try {
            queue.put(String.valueOf(this.index));
            System.out.println("{" + this.index + "} in queue!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            service.submit(new MyBlockingQueue_Put_Take(i));
        }
        Thread thread = new Thread() {
            public void run() {
                try {
                    while (true) {
                        Thread.sleep((int) (Math.random() * 1000));
//                        if (MyBlockingQueue_Put_Take.queue.isEmpty())
//                            break;
                        // take 会一直阻塞，知道有数据
                        String str = MyBlockingQueue_Put_Take.queue.take();
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

