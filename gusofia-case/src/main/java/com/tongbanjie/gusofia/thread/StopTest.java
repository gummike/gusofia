package com.tongbanjie.gusofia.thread;

/**
 * @author zi.you
 * @date 17/5/2
 */
public class StopTest {
    public volatile boolean stop = false;

    public static void main(String[] args) {
        final StopTest test = new StopTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!test.stop) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(System.currentTimeMillis());
                }
                System.out.println("结束执行");
            }
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

//                while (true) {
                    test.stop = true;
                    test.stop = false;
                    System.out.println("--------------");
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }).start();
    }
}
