package com.qjx.netty.secondexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        /**
         *  注意
         *  1.客户端不在是ServerBootstrap而是Bootstrap
         *  2.反射类不是NioServerSocketChannel而是NioSocketChannel
         *  3.一般使用handler，而不是用childHandler
         *  4.不是bind绑定端口而是connect
         */
        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {

        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
