package com.qjx.repeat.redis.codes;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public class ByteBufToByteDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        byte[] bytes = new byte[byteBuf.writerIndex()];
        byteBuf.readBytes(bytes);
        out.add(bytes);
    }
}
