# 客户端和服务端通信
> 完成客户端和服务端通信主要三个类：
1. Server/Client : 开启服务端/客户端，完成监听
2. Initizlizer ：用于注入到Bootstrap类中，类似于拦截器，可以做些过滤的功能。
3. Handler : 用于注入到Initizlizer类中，一般自定义的Handler完成消息接受和发送。

# code

## 服务端
### main
```java
public class MyServer {
  public static void main(String[] args) throws Exception{
        //事件循环组
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new MyServerInitializer());

            ChannelFuture channelFuture=serverBootstrap.bind(8899).sync();
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
        ChannelPipeline pipeline=ch.pipeline();
        //解码器
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        //编码器
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new MyServerHandler());
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

```


## 客户端
### main
```java
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
```
### initializer
```java
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new MyClientHandler());
    }
}
```
### handler
```java

/**
 * 这里的泛型是String，说明这个传输的是个String对象
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     *
     * @param ctx 上下文请求对象
     * @param msg 表示服务端发来的消息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client 输出:"+msg);
        ctx.writeAndFlush("来自 client："+ LocalDateTime.now());
    }


    /**
     * 如果没有这个方法，Client并不会主动发消息给Server
     * 那么Server的channelRead0无法触发，导致Client的channelRead0也无法触发
     * 这个channelActive可以让Client连接后，发送一条消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("hahah");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
```

