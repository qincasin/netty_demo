package com.qjx.netty.fiveexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());

        /**
         * 以块方式来进行写
         */
        pipeline.addLast(new ChunkedWriteHandler());

        /**
         *  特别重要，http数据在传输过程是分段的
         *  HttpObjectAggregator,而他就是将多个段聚合起来。
         */
        pipeline.addLast(new HttpObjectAggregator(8192));

        /**
         * 对于websocket 它的数据传输是以帧(frame)的形式传递
         * 可以查看WebSocketFrame 有六个子类
         * /ws 表示的websocket的地址
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new TextWebSocketFrameHandle());
    }
}
