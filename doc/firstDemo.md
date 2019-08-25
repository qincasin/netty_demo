# netty_http
## 使用场景
- RPC的通信框架，通讯协议，通讯库，实现远程过程中的调用(基于socket方式)
- 长连接服务器，实现客户端与服务端长连接的服务器
- http服务器，类似于jetty、tomcat

## demo
> 客户端发出一个请求，不带任何参数的，服务端返回一个hello world

> 使用netty技术完成服务端，步骤如下：
>  1.创建两个事件循环组。bossGroup，workerGroup
>   2.创建ServerBootstrap服务端，注入三个参数EventLoopGroup，NioServerSocketChannel.class，childHandler.(看代码) 
>   3.绑定一个端口，进行监听。bind(8899).sync() 4.关闭监听，关闭事件循环组。

## SimpleChannelInboundHandler执行顺序
>处理顺序如下：
- handlerAdded
- channelRegistered
- channelActive
- 请求方法名:GET（channelRead0）
    - （下面的表示的是断开连接后）
    - 1.如果是使用curl ：连接会立刻关闭2.如果是浏览器访问，http1.0：它是短连接，会立刻关闭。http1.1，是长连接，连接保持一段时间
- channelInactive
- channelUnregistered


```java
public class TestServer {

    public static void main(String[] args) {
        //定义线程组
        // 事件循环组
        // NioEventLoopGroup 死循环
        EventLoopGroup bossGroup = new NioEventLoopGroup(); //获取连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();  // 连接处理

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());//子处理器
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e){
            //todo
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
```

```java


/**
 * 自己定义处理类
 *
 *    处理顺序如下：
 *   handlerAdded
 *   channelRegistered
 *   channelActive
 *   请求方法名:GET（channelRead0）
 *   （下面的表示的是断开连接后）
 *   1.如果是使用curl ：连接会立刻关闭
 *   2.如果是浏览器访问，http1.0：它是短连接，会立刻关闭。http1.1，是长连接，连接保持一段时间
 *   channelInactive
 *   channelUnregistered
 *
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**4
     * 读取客户端发过来的请求，并且向客户端返回相应
     *
     * 继承InboundHandler类，代表处理进入的请求，还有OutboundHandler,处理出去请求
     *  其中里面的泛型表示msg的类型，如果指定了HttpObject，表明这是个HTTP连接的对象
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println(msg.getClass());
        System.out.println(ctx.channel().remoteAddress());

        Thread.sleep(8000);

        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest)msg;

            System.out.println("请求方法名:"+httpRequest.method().name());
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求favicon.ico");
                return;
            }
            //需要返回的内容
            //ByteBuf,netty中极为重要的概念，代表响应返回的数据

            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            //支撑相应的response
            //构造一个http响应,HttpVersion.HTTP_1_1:采用http1.1协议，HttpResponseStatus.OK：状态码200
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //如果只是调用write方法，他仅仅是存在缓冲区里，并不会返回客户端
            ctx.writeAndFlush(response);
            //ctx.channel().close();
        }


    }

    //3
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active");
        super.channelActive(ctx);
    }

    //2
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registered");
        super.channelRegistered(ctx);
    }
    //1
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added");
        super.handlerAdded(ctx);
    }

    //5
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive");
        super.channelInactive(ctx);
    }

    //6
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregistered");
        super.channelUnregistered(ctx);
    }

```