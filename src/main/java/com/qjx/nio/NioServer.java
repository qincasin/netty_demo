package com.qjx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by qincasin on 2020/1/7.
 */
public class NioServer {

    private static Map<String, SocketChannel> clientMap;

    static {
        clientMap = new HashMap<>();
    }

    //一个channel  服务器端只有一个线程
    public static void main(String[] args) throws IOException {
        //1.创建一个serverSocketChannel对象
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //2.调用configureBlocking 配置成非阻塞的
        serverSocketChannel.configureBlocking(false);

        //3.通过serverSocketChannel获取服务器端所对应的serverSocket对象
        ServerSocket serverSocket = serverSocketChannel.socket();

        //4端口号的绑定
        serverSocket.bind(new InetSocketAddress(8890));

        System.out.println("准备工作完成....,开始创建selector 对象");

        //5.通过Selector.open()方法创建selector方法
        Selector selector = Selector.open();

        //6.将serverSocketChanel对象注册到Selector对象上
        //此时的serverSocketChannel 关注点在于连接上面
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //接下来就是事件处理

        while (true) {

            try {
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    final SocketChannel client;
                    try {
                        if (selectionKey.isAcceptable()) {
                            //是否有新进来的连接
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            //此时的socketChannel是关注 读取事件
                            //并且此时已经在两个socket注册到selector上面了
                            client.register(selector, SelectionKey.OP_READ);

                            String key = "【" + UUID.randomUUID().toString() + "】";
                            clientMap.put(key, client);

                        } else if (selectionKey.isReadable()) {
                            //是否有新到来的数据
                            //之前注册时socketChannel对象，所以本次取出来一定 socketChannel对象
                            client = (SocketChannel) selectionKey.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            //TODO 客户端连接断了之后需要做处理，不然会报--java.io.IOException: Connection reset by peer 异常
                            int count = client.read(byteBuffer);
                            if (count > 0) {
                                byteBuffer.flip();

                                Charset charset = StandardCharsets.UTF_8;
                                String receiveMessage = String.valueOf(charset.decode(byteBuffer).array());
                                System.out.println(client + ":" + receiveMessage);
                                //下面将消息分发给所有已连接的客户端
                                //拿到发送者的key值
                                String senderKey = null;
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    if (client == entry.getValue()) {
                                        senderKey = entry.getKey();
                                        break;
                                    }
                                }

                                //分发给已连接的客户端
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    SocketChannel value = entry.getValue();
                                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                                    //读数据
                                    buffer.put((senderKey+":"+receiveMessage).getBytes());
                                    //反转
                                    buffer.flip();
                                    //写数据
                                    value.write(buffer);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //集合清空
                selectionKeys.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
