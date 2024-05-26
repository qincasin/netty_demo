package com.qjx.repeat.redis.connections;

import com.qjx.repeat.redis.codes.ByteBufToByteDecoder;
import com.qjx.repeat.redis.codes.ByteToByteBufEncoder;
import com.qjx.repeat.redis.codes.RedisRespHandler;
import com.qjx.repeat.redis.config.RedisConfig;
import com.qjx.repeat.redis.enums.ClientType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 连接池
 * <p>
 * 1. 初始化时, 生成一定量的RedisConnection对象
 * <p>
 * 2. 提供检测RedisConnection对象是否活跃的方法
 * <p>
 * 3. 提供borrowConnection方法
 * <p>
 * 4. 提供returnConnection方法
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public class ConnectionPool<T> {

    private BlockingQueue<RedisConnection<T>> connections;

    public ConnectionPool(ClientType clientType) {
        // 默认创建 10 个连接
        this.connections = new LinkedBlockingQueue<>(RedisConfig.connectionCount);
        init(clientType);
    }

    private void init(ClientType clientType) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        try {
            int count = RedisConfig.connectionCount;
            int tryCount = RedisConfig.connectionCount * 2;
            while (connections.size() < count && tryCount-- > 0) {
                // 公平的同步队列   传到 RedisRespHandler 中用于异步获取返回的数据
                final SynchronousQueue<T> synchronousQueue = new SynchronousQueue<>(true);
                bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        if (ClientType.STRING.equals(clientType)) {
                            // string 类型的的kv
                            ch.pipeline()
                                    .addLast("stringEncoder", new StringEncoder())
                                    .addLast("stringDecoder", new StringDecoder())
                                    .addLast("redisRespHandler", new RedisRespHandler<>(synchronousQueue));
                        } else if (ClientType.BINARY.equals(clientType)) {
                            // TODO
                            ch.pipeline()
                                    .addLast("byteBufToByteDecoder", new ByteBufToByteDecoder())
                                    .addLast("redisRespHandler", new RedisRespHandler<>(synchronousQueue))
                                    .addLast("byteToByteBufEncoder", new ByteToByteBufEncoder());
                        } else {
                            throw new InterruptedException("clientType error");
                        }
                    }
                });
                ChannelFuture channelFuture = bootstrap.connect(RedisConfig.host, RedisConfig.port).sync();
                Channel channel = channelFuture.channel();
                if (channel.isActive()) {
                    String name = "connect-" + connections.size();
                    this.connections.add(new RedisConnection<>(name, (NioSocketChannel) channel, synchronousQueue));
                }
            }
            if (connections.size() != count) {
                throw new IllegalArgumentException("");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public RedisConnection borrowConnection() {
        try {
            RedisConnection<T> connection = connections.take();
            System.out.println("borrowConnection :" + connection.getName());
            return connection;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void returnConnection(RedisConnection<T> channel) {
        // 清除
        channel.cleanUp();
        boolean offer = connections.offer(channel);
        if (!offer) {
            // try again
            offer = connections.offer(channel);
        }
        if (!offer) {
            System.out.println("returnConnection error");
            channel.disconnect();
            channel.close();
        }
    }

    public boolean checkChannel(RedisConnection channel) {
        return channel != null && channel.isActive();
    }

}
