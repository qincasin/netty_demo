package com.qjx.netty.firstexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    //这是一个回调的方法，在channel被注册时被调用
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //一个管道，里面有很多ChannelHandler，这些就像拦截器，可以做很多事
        ChannelPipeline pipeline = ch.pipeline();
        /**
         * 注意这些new的对象都是多例的，每次new出来新的对象,因为每个连接的都是不同的用户
         */
        //HttpServerCodec完成http编解码，可查源码
        // A combination of {@link HttpRequestDecoder} and {@link HttpResponseEncoder}
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        //增加一个自己定义的处理器hander
        pipeline.addLast("testHttpServerCodec",new TestHttpServerHandler());
    }

}
