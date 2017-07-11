package com.tongbanjie.gusofia;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zi.you
 * @date 17/5/31
 */
public class BlockServer {

    private static final Log LOGGER = LogFactory.getLog(BlockServer.class);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8091);
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            byte[] cache = new byte[5];
            int read;
            StringBuffer sb = new StringBuffer();
//            int read1 = is.read(cache, 0, 1024);
            while ((read = is.read(cache)) != -1) {
                sb.append(new String(cache, 0, read));
            }
            System.out.println("收到客户的消息：" + sb.toString());
            os.write("你好".getBytes());
            os.flush();
            os.close();
            is.close();
            socket.close();
        }
    }
}
