package com.qjx.nio;

import java.nio.ByteBuffer;

/**
 * 只读buffer
 * 我们可以随时将一个普通Buffer调用asReadOnlyBuffer 方法返回一个只读buffer
 * 但不能将一个只读buffer转换为读写Buffer
 */
public class NioTest7 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println(byteBuffer.getClass()); //class java.nio.HeapByteBuffer
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());  //class java.nio.HeapByteBufferR

        readOnlyBuffer.position(0);
//        readOnlyBuffer.put((byte)2);   //报错 只读 buffer异常


    }
}
