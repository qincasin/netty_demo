package com.qjx.repeat.redis.codes;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.SynchronousQueue;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public class RedisRespHandler<T> extends ChannelInboundHandlerAdapter {

    private SynchronousQueue<T> queue;

    public RedisRespHandler(SynchronousQueue<T> queue) {
        this.queue = queue;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        queue.put((T) msg);
    }
}
