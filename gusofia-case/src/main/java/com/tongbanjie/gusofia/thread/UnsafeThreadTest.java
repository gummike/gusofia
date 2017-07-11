package com.tongbanjie.gusofia.thread;

/**
 * @author zi.you
 * @date 17/7/5
 */
public class UnsafeThreadTest {
    public static void main(String[] args) {
        final Cat cat = new Cat();
        new Thread() {
            @Override
            public void run() {
                cat.printName("zhangsan");
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                cat.printName("lisi");
            }
        }.start();
    }
}


class Cat {
    public void printName(String name) {
        Object o = new Object();
        synchronized (o) {
            for (int i = 0; i < name.length(); i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(name.charAt(i));
            }
        }
    }
}