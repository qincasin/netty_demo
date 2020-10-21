package com.qjx.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by qincasin on 2020/10/21.
 */
public class NewIOServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(8899);
        ServerSocketChannel serverSocketChannel  = ServerSocketChannel.open();
        ServerSocket socket = serverSocketChannel.socket();
        //设置在  TIME_WAIT 状态下 仍然可以被新的连接绑定到这个端口号上面
        socket.setReuseAddress(true);
        socket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        System.out.println("server 启动 ，端口号:"+8899);
        while (true){
            //准备接收连接
            SocketChannel accept = serverSocketChannel.accept();
            accept.configureBlocking(true);
            int readCount = 0;

            while (-1!=readCount){
                try {
                    readCount = accept.read(byteBuffer);
                }catch (Exception e){
                    e.printStackTrace();
                }

                byteBuffer.rewind();
            }

        }

    }
}
