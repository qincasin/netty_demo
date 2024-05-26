package com.qjx.repeat.redis.client;

import com.qjx.repeat.redis.cmd.Cmd;
import com.qjx.repeat.redis.cmd.CmdResp;
import com.qjx.repeat.redis.config.RedisConfig;
import com.qjx.repeat.redis.connections.ConnectionPool;
import com.qjx.repeat.redis.connections.RedisConnection;
import com.qjx.repeat.redis.enums.ClientType;
import com.qjx.repeat.redis.exceptions.FailedToGetConnectionException;

/**
 * redis client 具体的抽象
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public abstract class AbstractRedisClient<T> implements RedisClient<T> {

    private ConnectionPool<T> connectionPool;

    public AbstractRedisClient(ClientType clientType) {
        connectionPool = new ConnectionPool<>(clientType);
    }

    protected <RETURN> RETURN invokeCmd(Cmd<T> cmd, CmdResp<T, RETURN> cmdResp) throws FailedToGetConnectionException {
        RedisConnection<T> connection = null;
        try {
            // 构建 RESP结构体
            T data = cmd.build();
            // 从连接池中borrow 连接
            connection = connectionPool.borrowConnection();
            if (connectionPool.checkChannel(connection)) {
                // 要锁定这个连接
                connection.lock();
                try {
                    // 发送命令
                    try {
                        connection.writeAndFlush(data).sync();
                        // 获取命令的返回结果
                        return cmdResp.parseResp(connection.getResp(RedisConfig.TIME_OUT_MS));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                } finally {
                    connection.unlock();
                }
            } else {
                throw new FailedToGetConnectionException("can not get connection form connection pool");
            }
        } finally {
            if (connectionPool.checkChannel(connection)) {
                connectionPool.returnConnection(connection);
            }
        }
    }
}
