package com.qjx.nio;

import java.nio.IntBuffer;
import java.util.Random;

/**
 * nio intBuffer 构造整数打印出来
 */
public class NioTest1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        //10
        System.out.println("capacity:" + buffer.capacity());

        System.out.println("first loop");

        for (int i = 0; i < 5; i++) {
            int randomNumber = new Random().nextInt(20);
            buffer.put(randomNumber);
            System.out.println("position:" + buffer.position());

//            System.out.println("limit:" + buffer.limit());
//            System.out.println("capacity:" + buffer.capacity());
        }
        //10
        System.out.println("before flip limit :" + buffer.limit());

        buffer.flip();
        //5
        System.out.println("after flip limit :" + buffer.limit());

        System.out.println("enter while loop ");
        while (buffer.hasRemaining()) {

            System.out.println("position:" + buffer.position());
            System.out.println("limit:" + buffer.limit());
            System.out.println("capacity:" + buffer.capacity());

            System.out.println(buffer.get());
        }
    }
}
