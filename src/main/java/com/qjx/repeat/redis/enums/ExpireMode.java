package com.qjx.repeat.redis.enums;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public enum ExpireMode {
    /**
     * 过期时间是毫秒
     */
    PX("PX"),
    /**
     * 过期时间是秒
     */
    EX("EX");
    private final String type;

    private ExpireMode(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
