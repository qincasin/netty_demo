package com.qjx.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 关于buffer 的 Scattering 与 Gathering
 */
public class NioTest11 {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);
        // 定义三个buffer
        int messageLength = 2 + 3 + 4;

        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            int bytesRead = 0;
            //读操作
            while (bytesRead < messageLength) {
                long r = socketChannel.read(buffers);
                bytesRead += r;

                System.out.println("bytesRead: " + bytesRead);

                Arrays.asList(buffers).stream().map(
                        buffer -> "position: " + buffer.position() + ", limit: " + buffer.limit()
                ).forEach(System.out::println);
            }

            Arrays.asList(buffers).stream().forEach(buffer -> buffer.flip());
            // 执行写操作
            long bytesWrite = 0;
            while (bytesWrite < messageLength) {
                long r = socketChannel.write(buffers);
                bytesWrite += r;
            }
            Arrays.asList(buffers).forEach(buffer -> {
                buffer.clear();
            });
            System.out.println("bytesRead: " + bytesRead + ",bytesWritten:" + bytesWrite + ",messageLength:" + messageLength);
        }

    }
}
