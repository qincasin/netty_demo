package com.qjx.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * stream 转换为 nio 中的buffer demo
 */
public class NioTest2 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
        FileChannel filechannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(521);
        filechannel.read(byteBuffer);

        byteBuffer.flip();

        while (byteBuffer.remaining() > 0) {
            byte b = byteBuffer.get();
            System.out.println("Character: " + (char) b);
        }

        fileInputStream.close();

    }


}
