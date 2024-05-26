package com.qjx.repeat.redis.config;

/**
 * redis的一些配置信息
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public class RedisConfig {

    public static final String host = "127.0.0.1";
    public static final int port = 6379;
    /**
     * 每次创建的连接数
     */
    public static final int connectionCount = 10;
    /**
     * 超时时间
     */
    public static final int TIME_OUT_MS = 5000;

}
