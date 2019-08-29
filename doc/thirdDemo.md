# 多个客户端简单聊天 
> 多个客户端连接服务端,可以相互发送消息，并且有上下线通知.


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
public class MyChatInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new MyChatServerHandler());
    }
}

```
### handler
```java
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
/**
 * 这里的泛型是String，说明这个传输的是个String对象
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * ChannelHandlerContext ctx: 表示请求上下文信息。可用于获得channel，远程地址等
     * msg ：表示客户端发送来的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+" : "+msg);
        ctx.channel().writeAndFlush("from Server"+ UUID.randomUUID());

    }
    //异常的捕获，一般出现异常，就把连接关闭
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
}
public class MyChatInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new MyChatServerHandler());
    }
}
```


## 客户端
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

