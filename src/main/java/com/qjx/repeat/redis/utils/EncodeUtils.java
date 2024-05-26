package com.qjx.repeat.redis.utils;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public class EncodeUtils {

    public static byte[] getBytes(Object object) {
        if (object == null) {
            return null;
        }
        return String.valueOf(object).getBytes();
    }
}
