package com.tongbanjie.gusofia.thread.lock;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Service {

    public static void main(String[] args) throws InterruptedException {
        MyService service = new MyService();
        ThreadA threadA = new ThreadA(service);
        threadA.setName("A");

        ThreadB threadB = new ThreadB(service);
        threadB.setName("B");
        threadA.start();
        threadB.start();

        Thread.sleep(5000);
        service.signalA();
        Thread.sleep(5000);
        service.signalB();

//        ThreadA[] threadAs = new ThreadA[10];
//        for (int i=0;i<5;i++){
//            threadAs[i] = new ThreadA(service);
//        }
//        ThreadB[] threadBs = new ThreadB[10];
//        for (int i=0;i<5;i++){
//            threadBs[i] = new ThreadB(service);
//        }
//
//        for (int i=0;i<5;i++){
//            threadAs[i].start();
//            threadBs[i].start();
//        }
//
//        Thread.sleep(1000);
//        service.signalA_All();
//        Thread.sleep(1000);
//        service.signalB_All();
    }
}

class MyService {
    private ReentrantLock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();

    public void methodA() {
        lock.lock();
        try {
            System.out.println("MethodA begin ThreadName=" + Thread.currentThread().getName() + "  " + System.currentTimeMillis());
            conditionA.await();
            System.out.println("MethodA end ThreadName=" + Thread.currentThread().getName() + "  " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void methodB() {
        lock.lock();
        try {
            System.out.println("MethodB begin ThreadName=" + Thread.currentThread().getName() + "  " + System.currentTimeMillis());
            conditionB.await();
            System.out.println("MethodB end ThreadName=" + Thread.currentThread().getName() + "  " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signalA() {
        lock.lock();
        conditionA.signal();
        lock.unlock();
    }

    public void signalA_All() {
        lock.lock();
        conditionA.signalAll();
        lock.unlock();
    }


    public void signalB() {
        lock.lock();
        conditionB.signal();
        lock.unlock();
    }

    public void signalB_All() {
        lock.lock();
        conditionB.signalAll();
        lock.unlock();
    }
}

class ThreadA extends Thread {

    private MyService service;

    ThreadA(MyService service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.methodA();
    }
}

class ThreadB extends Thread {

    private MyService service;

    ThreadB(MyService service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.methodB();
    }
}

