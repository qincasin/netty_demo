package com.qjx.repeat.test.redis;

import com.qjx.repeat.redis.client.RedisStringClient;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisStringClientTest {

    public static final String KEY = "key";
    public static final String VALUE = "value";

    @Test
    public void test1() {
        RedisStringClient redisStringClient = RedisStringClient.getInstance();
        boolean set = redisStringClient.set(KEY, VALUE);
        assert set;
    }

    @Test
    public void test002() {
        RedisStringClient redisStringClient = RedisStringClient.getInstance();
        String str = redisStringClient.get(KEY);
        assert VALUE.equalsIgnoreCase(str);
    }
}
