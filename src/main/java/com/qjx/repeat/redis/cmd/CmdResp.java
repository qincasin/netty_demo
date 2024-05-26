package com.qjx.repeat.redis.cmd;

/**
 * PARAM其实就是redis返回的RESP的类型,如果是RedisStringClient的话,
 * 那么redis会返回String类型的RESP报文.
 * 如果是RedisBinaryClient的话, 那么redis会返回byte数组类型的RESP报文
 * <p>
 * RETURN其实是我们最近想解析出来的数据类型.
 * 这个是根据命令的不同而不同的,
 * 例如set命令, 是需要返回true 或false的, 所以它Boolean.
 * 而其他命令可能就不一样了,是String或Integer等等
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public interface CmdResp<PARAM, RETURN> {

    /**
     * 解析redis 的resp
     *
     * @param resp
     * @return
     */
    RETURN parseResp(PARAM resp);
}
