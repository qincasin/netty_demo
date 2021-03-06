package com.qjx.netty.fourthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //在五秒钟没有执行读，7秒钟没有执行写，在十秒钟空闲 都会触发这个操作
        /**
         * 处理空闲状态事件的处理器
         * IdleStateHandler三个参数分别对应：readerIdleTime，writerIdleTime，allIdleTime
         * readerIdleTime：表示多长时间没有读，就发送一个心跳包检验是否连接。
         * writerIdleTime：表示多长时间没有写，就发送一个心跳包检验是否连接。
         * allIdleTime：表示什么都不做，过多长时间，就发送一个心跳包检验是否连接。
         */
        pipeline.addLast(new IdleStateHandler(5,7,10, TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
