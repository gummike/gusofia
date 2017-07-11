package com.tongbanjie.gusofia.thread;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zi.you
 * @date 17/7/5
 */
public class TimerTest {
    public static void main(String[] args) throws InterruptedException {
        final Timer timer = new Timer();
        timer.schedule(new Task1(), 2000);
        Thread.sleep(3000);
        timer.cancel();
    }
}

class Task1 extends  TimerTask{
    @Override
    public void run() {
        System.out.println("原子弹");
        Timer timer = new Timer();
        timer.schedule(new Task2(), 1000);
    }
}


class Task2 extends  TimerTask{
    @Override
    public void run() {
        System.out.println("氢弹");
        Timer timer = new Timer();
        timer.schedule(new Task1(), 1000);
    }
}
