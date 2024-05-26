package com.qjx.repeat.redis.cmd.impl.setcmd;

import com.qjx.repeat.redis.cmd.AbstractCmd;
import java.util.ArrayList;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public abstract class AbstractSetCmd<T> extends AbstractCmd<T> {

    public AbstractSetCmd(T key, T value, T expireMode, T expireTime, T xmode) {
        super();
        super.paramList = new ArrayList<>();
        paramList.add(key);
        paramList.add(value);
        // 设置了过期时间
        if (expireMode != null) {
            paramList.add(expireMode);
            paramList.add(expireTime);
        }
        // 设置了 xx 或者 nx
        if (xmode != null) {
            paramList.add(xmode);
        }
    }

    @Override
    protected String getCmd() {
        return super.cmd = "set";
    }
}
