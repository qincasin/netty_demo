package com.qjx.repeat.redis.exceptions;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public class FailedToGetConnectionException extends AwesomeNettyRedisException {

    public FailedToGetConnectionException(String s) {
        super(s);
    }
}
