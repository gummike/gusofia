package com.tongbanjie.gusofia.thread.wait;

public class Test1 implements Runnable {

    public void run() {
        synchronized (this) {
            try {
                long time = System.currentTimeMillis();
                System.out.println(time+"--start");
                Thread.sleep(2000);
                System.out.println(time+"--end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            new Thread(new Test1()).start(); // 关键，如果将 new Test1拿到外面，那么同步方法才有意义，如下：
//        }
//    }

    public static void main(String[] args) {
        Test1 test = new Test1();
        for (int i = 0; i < 10; i++) {
            new Thread(test).start();
        }
    }
}