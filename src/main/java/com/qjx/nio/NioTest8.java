package com.qjx.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest8 {
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("input2.txt");
        FileOutputStream outputStream = new FileOutputStream("output2.txt");

        FileChannel inputChannel = inputStream.getChannel();

        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(512);

        while (true) {
            byteBuffer.clear();  // 如果注释掉会导致 第二次循环时 position和limit值相同，进而导致read方法无法读取新数据 进而导致 read返回值为0 而非-1 最后导致项目一直死循环，文件中一个写入buffer中相同值

            int read = inputChannel.read(byteBuffer);

            System.out.println("read:" + read);

            if (-1 == read) {
                break;
            }

            byteBuffer.flip();

            outputChannel.write(byteBuffer);


        }

        inputChannel.close();
        outputChannel.close();

    }
}
