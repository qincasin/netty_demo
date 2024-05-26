package com.qjx.repeat.redis.cmd;

import java.util.List;

/**
 * 如果我们想实现 set key val, 这个命令, 那么cmd就是"set", "key"和"val"都是 paramList的元素
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public abstract class AbstractCmd<T> implements Cmd<T> {

    /**
     * 具体是什么命令，例如get set 等等
     */
    protected String cmd;

    /**
     * 这个命令后面的 参数
     */
    protected List<T> paramList;

    /**
     * redis 命令
     *
     * @return
     */
    protected abstract String getCmd();
}
