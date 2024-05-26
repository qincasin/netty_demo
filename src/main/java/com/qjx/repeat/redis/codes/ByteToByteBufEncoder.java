package com.qjx.repeat.redis.codes;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public class ByteToByteBufEncoder extends MessageToByteEncoder<byte[]> {

    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {
        if (msg == null) {
            return;
        }
        out.writeBytes(msg);
    }
}
