package com.qjx.nio;

import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;

/**
 * selector
 */
public class NioTest12 {
    /**
     * 服务器端监听五个端口号，每个端口号 都可以被客户端发起连接，这里使用一个线程处理所有连接
     * @param args
     */
    public static void main(String[] args) throws Exception{
        Selector selector = Selector.open();


        System.out.println(SelectorProvider.provider().getClass());
        System.out.println(sun.nio.ch.DefaultSelectorProvider.create().getClass());

    }
}
