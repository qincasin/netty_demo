package com.qjx.netty.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by qincasin on 2020/10/12.
 */
public class MyServerTest {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.handler(new LoggingHandler(LogLevel.INFO))
                    .group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_REUSEADDR,true);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>(){

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addFirst(new IdleStateHandler(5,0,10, TimeUnit.SECONDS));
                    //每个连接都有个ConnectionCountHandler对连接记数进行增加
                    pipeline.addLast(new PingHandler());
                    pipeline.addLast(new ConnectionCountHandler());

                }
            });
            bootstrap.bind(8888).addListener((ChannelFutureListener) future -> {
                System.out.println("端口绑定成功: " + 8888);
            });
            System.out.println("服务已启动!");
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
