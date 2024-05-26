package com.qjx.repeat.redis.cmd;

/**
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public interface Cmd<PT> {

    /**
     * 构建RESP协议
     */
    PT build();
}
