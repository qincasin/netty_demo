package com.qjx.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 1.上线状态：
 * a b c 三个连接 ，a和服务器建立连接后，b此时和服务器建立连接，
 * 则服务器打印 b此时上线了，同时通知a，b上线了，
 * 此时 c建立连接了，
 * 则服务器打印 c上线了，同时 广播给 a b ，c上线了
 * 2.发送消息：
 * 都在线状态下，a发出一条消息， 此时 a b c均可以收到 a 发出的消息， a 能看到是自己发出的，b c 能看到 是a发出的消息
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + " 发送的消息：" + msg + "\n");
            } else {
                ch.writeAndFlush(" [自己] " + msg + " \n");
            }

        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("handlerAdded...");

        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 加入\n");
        channelGroup.add(channel);
    }

    /**
     * 断开连接时执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("handlerRemoved...");
        /**
         * 如果是离开，相对应的应该移除channel，但是这里不需要
         * 因为netty，自动将它移除了
         */
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 离开\n");
        //channelGroup.remove(channel);

        System.out.println(channelGroup.size());


    }

    /**
     * 表示连接处于活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channelActive...");
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 上线！！！");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channelInactive...");
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "下线！！！");
    }
}
