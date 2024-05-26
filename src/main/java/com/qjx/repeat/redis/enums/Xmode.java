package com.qjx.repeat.redis.enums;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public enum Xmode {
    /**
     * key 存在的时候才创建
     */
    XX("XX"),
    /**
     * key 不存在的时候才创建
     */
    NX("EX"),
    ;
    private final String type;

    Xmode(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}