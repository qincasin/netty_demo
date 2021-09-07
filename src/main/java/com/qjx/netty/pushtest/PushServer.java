package com.qjx.netty.pushtest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by qincasin on 2021/5/10.
 */
public class PushServer {
    public static void main(String[] args) {
        EventLoopGroup b = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(b,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new StringDecoder())
                                    .addLast(new SimpleChannelInboundHandler<String>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                            System.out.println(" 收到数据 msg:"+msg);
                                            ctx.channel().writeAndFlush(msg);
                                        }

                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            Channel channel = ctx.channel();
                                            System.out.println(channel.remoteAddress() + " 上线！！！");
                                        }

                                        @Override
                                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                            Channel channel = ctx.channel();
                                            System.out.println(channel.remoteAddress() + " 下线！！！");
                                        }

                                        @Override
                                        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                            System.out.println("channel channelRegistered");

                                        }

                                        @Override
                                        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                                            System.out.println("channel channelUnregistered");
                                        }
                                    });
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(8001).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            b.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
