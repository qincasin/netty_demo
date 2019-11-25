package com.qjx.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 文件锁概念 共享锁 排它锁(只能有一个写)
 */
public class NioTest10 {
    public static void main(String[] args)throws Exception {
        RandomAccessFile rand = new RandomAccessFile("NioTest10.txt", "rw");
        FileChannel fileChannel = rand.getChannel();
        FileLock lock = fileChannel.lock(3,6,true);

        System.out.println("valid:"+lock.isValid());
        System.out.println("lock type:"+lock.isShared());

        lock.release();

        rand.close();
    }
}
