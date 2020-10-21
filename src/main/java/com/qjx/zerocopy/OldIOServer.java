package com.qjx.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by qincasin on 2020/10/21.
 */
public class OldIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8899);
        System.out.println("服务端启动,端口号:" + 8899);
        while (true) {
            try {
                Socket accept = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(accept.getInputStream());
                byte[] bytes = new byte[4096];
                while (true) {
                    int read = dataInputStream.read(bytes, 0, bytes.length);
                    if (read == -1) {
                        break;
                    }
                }

            } catch (Exception e) {

            }
        }
    }
}
