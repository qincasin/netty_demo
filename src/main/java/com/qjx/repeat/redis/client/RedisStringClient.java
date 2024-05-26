package com.qjx.repeat.redis.client;

import com.qjx.repeat.redis.cmd.impl.getcmd.str.GetStringCmd;
import com.qjx.repeat.redis.cmd.impl.setcmd.str.SetStringCmd;
import com.qjx.repeat.redis.enums.ClientType;
import com.qjx.repeat.redis.enums.ExpireMode;
import com.qjx.repeat.redis.enums.Xmode;
import com.qjx.repeat.redis.exceptions.FailedToGetConnectionException;

/**
 * string client
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public class RedisStringClient extends AbstractRedisClient<String> implements RedisClient<String> {

    protected RedisStringClient() {
        super(ClientType.STRING);
    }

    public static RedisStringClient getInstance() {
        return InstanceHolder.CLIENT;
    }

    @Override
    public boolean set(String key, String v) {
        SetStringCmd setCmd = new SetStringCmd(key, v);
        try {
            return invokeCmd(setCmd, setCmd);
        } catch (FailedToGetConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean setNx(String key, String v) {
        SetStringCmd setCmd = new SetStringCmd(key, v, Xmode.NX);
        try {
            return invokeCmd(setCmd, setCmd);
        } catch (FailedToGetConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean setWithExpireTime(String key, String v, long seconds) {
        SetStringCmd setCmd = new SetStringCmd(key, v, ExpireMode.EX, seconds);
        try {
            return invokeCmd(setCmd, setCmd);
        } catch (FailedToGetConnectionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean set(String key, String v, ExpireMode expireMode, long expireTime, Xmode x) {
        SetStringCmd setCmd = new SetStringCmd(key, v, ExpireMode.EX, expireTime, x);
        try {
            return invokeCmd(setCmd, setCmd);
        } catch (FailedToGetConnectionException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public String get(String key) {
        GetStringCmd getCmd = new GetStringCmd(key);
        try {
            return invokeCmd(getCmd, getCmd);
        } catch (FailedToGetConnectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class InstanceHolder {

        public static final RedisStringClient CLIENT = new RedisStringClient();
    }
}
