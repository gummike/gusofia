package com.tongbanjie.gusofia;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zi.you
 * @date 17/6/1
 */
public class SocketServerNonBlockingProcessInThread2 {

    public static void main(String[] args) {
        try {
            new SocketServerNonBlockingProcessInThread2().init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8088);
            serverSocket.setSoTimeout(1000);
            if (serverSocket != null) {

                while (true) {
                    Socket socket = null;
                    try {
                        socket = serverSocket.accept();
                    } catch (IOException e) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    SocketProcessor processor = new SocketProcessor(socket);
                    new Thread(processor).start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    class SocketProcessor implements Runnable {

        private Socket socket;

        public SocketProcessor(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            if (socket != null) {
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();
                    byte[] cache = new byte[1024];
                    int len = inputStream.read(cache);
                    //读取信息
                    String message = new String(cache, 0, len);
                    System.out.println("来自客户的的数据::" + message);
                    outputStream.write("你好!".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
