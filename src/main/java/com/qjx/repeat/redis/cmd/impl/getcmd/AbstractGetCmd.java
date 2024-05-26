package com.qjx.repeat.redis.cmd.impl.getcmd;

import com.qjx.repeat.redis.cmd.AbstractCmd;
import java.util.ArrayList;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public abstract class AbstractGetCmd<T> extends AbstractCmd<T> {

    public AbstractGetCmd(T key) {
        super();
        super.paramList = new ArrayList<>();
        paramList.add(key);
    }

    @Override
    protected String getCmd() {
        return super.cmd = "get";
    }
}
