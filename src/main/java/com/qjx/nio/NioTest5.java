package com.qjx.nio;

import java.nio.ByteBuffer;

/**
 * ByteBuffer 类型化的get 和 put
 */
public class NioTest5 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(15);
        buffer.putLong(50000L);
        buffer.putDouble(14.13);
        buffer.putChar('你');
        buffer.putShort((short)2);
        buffer.putChar('好');

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getChar());
    }
}
