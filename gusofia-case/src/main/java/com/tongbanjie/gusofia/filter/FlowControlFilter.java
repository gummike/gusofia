package com.tongbanjie.gusofia.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 流量控制过滤器
 * 用于保护当前JVM进程，在过载流量下，处于稳定可控状态。
 */
public class FlowControlFilter implements Filter {

    //最大并发量  
    private int permits = Runtime.getRuntime().availableProcessors() + 1;//默认为500  

    //当并发量达到permits后，新的请求将会被buffer，buffer最大尺寸  
    //如果buffer已满，则直接拒绝  
    private int bufferSize = 500;//  
    //buffer中的请求被阻塞，此值用于控制最大阻塞时间  
    private long timeout = 30000;//默认阻塞时间  

    private String errorUrl;//跳转的错误页面  

    private BlockingQueue<Node> waitingQueue;

    private Thread selectorThread;
    private Semaphore semaphore;

    private Object lock = new Object();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String p = filterConfig.getInitParameter("permits");
        if (p != null) {
            permits = Integer.parseInt(p);
            if (permits < 0) {
                throw new IllegalArgumentException("FlowControlFilter,permits parameter should be greater than 0 !");
            }
        }

        String t = filterConfig.getInitParameter("timeout");
        if (t != null) {
            timeout = Long.parseLong(t);
            if (timeout < 1) {
                throw new IllegalArgumentException("FlowControlFilter,timeout parameter should be greater than 0 !");
            }
        }

        String b = filterConfig.getInitParameter("bufferSize");
        if (b != null) {
            bufferSize = Integer.parseInt(b);
            if (bufferSize < 0) {
                throw new IllegalArgumentException("FlowControlFilter,bufferSize parameter should be greater than 0 !");
            }
        }

        errorUrl = filterConfig.getInitParameter("errorUrl");

        waitingQueue = new LinkedBlockingQueue<>(bufferSize);
        semaphore = new Semaphore(permits);

        selectorThread = new Thread(new SelectorRunner());
        selectorThread.setDaemon(true);
        selectorThread.start();


    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        checkSelector();
        Thread t = Thread.currentThread();
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Node node = new Node(t, false);
        boolean buffered = waitingQueue.offer(node);
        //如果buffer已满  
        if (!buffered) {
            if (errorUrl != null) {
                httpServletResponse.sendRedirect(errorUrl);
            }
            return;
        }
        long deadline = System.currentTimeMillis() + timeout;
        //进入等待队列后，当前线程阻塞  
        LockSupport.parkNanos(this, TimeUnit.MICROSECONDS.toNanos(timeout));
        if (t.isInterrupted()) {
            //如果线程是中断返回  
            t.interrupted();//clear status  

        }
        //如果等待过期，则直接返回  
        if (deadline >= System.currentTimeMillis()) {
            if (errorUrl != null) {
                httpServletResponse.sendRedirect(errorUrl);
            }
            //对信号量进行补充  
            synchronized (lock) {
                if (node.dequeued) {
                    semaphore.release();
                } else {
                    node.dequeued = true;
                }
            }
            return;
        }
        //继续执行  
        try {
            chain.doFilter(request, response);
        } finally {
            semaphore.release();
            checkSelector();
        }
    }

    private void checkSelector() {
        if (selectorThread != null && selectorThread.isAlive()) {
            return;
        }
        synchronized (lock) {
            if (selectorThread != null && selectorThread.isAlive()) {
                return;
            }
            selectorThread = new Thread(new SelectorRunner());
            selectorThread.setDaemon(true);
            selectorThread.start();
        }
    }

    private class SelectorRunner implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    Node node = waitingQueue.take();
                    //如果t，阻塞逃逸，只能在pack超时后退出  
                    synchronized (lock) {
                        if (node.dequeued) {
                            //如果此线程已经park过期而退出了，则直接忽略  
                            continue;
                        } else {
                            node.dequeued = true;
                        }

                    }
                    semaphore.acquire();
                    LockSupport.unpark(node.currentThread);
                }
            } catch (Exception e) {
                //  
            } finally {
                //全部释放阻塞  
                Queue<Node> queue = new LinkedList<>();
                waitingQueue.drainTo(queue);
                for (Node n : queue) {
                    if (!n.dequeued) {
                        LockSupport.unpark(n.currentThread);
                    }
                }
            }
        }
    }

    private class Node {
        Thread currentThread;
        boolean dequeued;//是否已经出队  

        public Node(Thread t, boolean dequeued) {
            this.currentThread = t;
            this.dequeued = dequeued;
        }
    }


    @Override
    public void destroy() {
        selectorThread.interrupt();
    }

}  