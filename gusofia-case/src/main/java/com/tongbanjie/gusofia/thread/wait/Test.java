package com.tongbanjie.gusofia.thread.wait;

class Test implements Runnable {

    public Test() {
    }



    @Override
    public void run() {
        synchronized (Test.class) {
            long time = System.currentTimeMillis();
            System.out.println("before      " + time);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Test.class.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("after       " + time);
        }
    }

}