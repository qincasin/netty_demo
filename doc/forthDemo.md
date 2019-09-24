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
public class MyServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //childHandler
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new MyServerInitializer());
            ChannelFuture channelFuture = bootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
```
### handler
```java
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        String eventType = null;

        if (evt instanceof IdleStateEvent) {
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

            System.out.println(ctx.channel().remoteAddress() + "超时事件： " + eventType);

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

