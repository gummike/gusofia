package com.tongbanjie.gusofia.thread.join;

import java.util.concurrent.*;

/**
 * @author zi.you
 * @date 17/7/11
 */
public class JoinTest_CyclicBarrier {


//    CyclicBarrier初始化时规定一个数目，然后计算调用了CyclicBarrier.await()进入等待的线程数。当线程数达到了这个数目时，所有进入等待状态的线程被唤醒并继续。
//    CyclicBarrier就象它名字的意思一样，可看成是个障碍， 所有的线程必须到齐后才能一起通过这个障碍。
//    CyclicBarrier初始时还可带一个Runnable的参数， 此Runnable任务在CyclicBarrier的数目达到后，所有其它线程被唤醒前被执行。
//    可以循环多组到达障碍的次数

    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("all done!");
            }
        });
        for (int i = 0; i < 99; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("aa  " + System.currentTimeMillis());
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
