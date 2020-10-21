package com.qjx.zerocopy;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by qincasin on 2020/10/21.
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        String fileName = "/Users/qinjiaxing/Downloads/openjdk-13.0.2_osx-x64_bin.tar.gz";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        long start = System.currentTimeMillis();

        long totalCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println("总字节数:" + totalCount + "," + "耗时:" + (System.currentTimeMillis() - start));
        fileChannel.close();

    }
}
