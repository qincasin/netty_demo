package com.qjx.repeat.redis.client;

import com.qjx.repeat.redis.enums.ClientType;
import com.qjx.repeat.redis.enums.ExpireMode;
import com.qjx.repeat.redis.enums.Xmode;

/**
 * binary client
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public class RedisBinaryClient extends AbstractRedisClient<byte[]> implements RedisClient<byte[]> {

    public RedisBinaryClient(ClientType clientType) {
        super(clientType);
    }

    @Override
    public boolean set(byte[] key, byte[] v) {
        return false;
    }

    @Override
    public boolean setNx(byte[] key, byte[] v) {
        return false;
    }

    @Override
    public boolean setWithExpireTime(byte[] key, byte[] v, long seconds) {
        return false;
    }

    @Override
    public boolean set(byte[] key, byte[] v, ExpireMode expireMode, long expireTime, Xmode x) {
        return false;
    }

    @Override
    public byte[] get(byte[] key) {
        return new byte[0];
    }
}
