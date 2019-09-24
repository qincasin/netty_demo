# 测试心跳机制 
> 心跳机制：判断是否还处于连接状态。 如我们的客户端是移动手机并且已经建立好了连接，当打开飞行模式（或者强制关机）的时候，我们就无法感知当前连接已经断开了（handlerRemoved不会触发的）


# code

## 服务端
### main
```java

public class MyChatServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //childHandler
            bootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyChatInitializer());
            ChannelFuture channelFuture = bootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
```
### initializer
```java
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
```
### handler
```java
package com.qjx.netty.fourthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 触发了某个事件之后就会被调用
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        String eventType = null;
        /**
         * 如果当前事件是IdleStateEvent类型，表示他是个空闲状态
         */
        if (evt instanceof IdleStateEvent) {
            /**
             * 判断event是什么状态，event.state()返回的是IdleState类型，它是个枚举类型，有三个属性
             * READER_IDLE，读空闲。WRITER_IDLE写空闲，ALL_IDLE什么都不做空闲
             *
             */
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;

            }
            /**
             * 注意了，这个读空闲和写空闲，指的是当前实现这个Handler的类，即Server
             * 如果Server没有接受到客户端的数据，即为读空闲（因为没有读取数据）
             * 如果Server没有发送消息，即为写空闲
             */
            System.out.println(ctx.channel().remoteAddress() + "超时事件： " + eventType);
            //如果不关闭，会一直循环判断
            ctx.channel().closeFuture();
        }
    }
}
```


## 客户端(沿用之前的客户端代码)
### main
```java
/**
  * 客户端代码
  */
 public class MyChatClient {
     public static void main(String[] args) throws Exception {
         EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
         try {
             Bootstrap bootstrap = new Bootstrap();
             bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                     .handler(new MyChatClientInitializer());
             Channel channel = bootstrap.connect("localhost", 8899).sync().channel();
 
             //死循环读取数据
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
             for (; ; ) {
                 channel.writeAndFlush(bufferedReader.readLine() + "\r\n");
             }
 
 //            channelFuture.channel().closeFuture().sync();
         } finally {
             eventLoopGroup.shutdownGracefully();
         }
     }
 }
```
### initializer
```java
public class MyChatClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new MyChatClientHandler());
    }
}
```
### handler
```java
public class MyChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }
}
```

