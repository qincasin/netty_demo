package com.qjx.repeat.redis.cmd.impl.setcmd.str;

import com.qjx.repeat.redis.cmd.CmdResp;
import com.qjx.repeat.redis.cmd.impl.setcmd.AbstractSetCmd;
import com.qjx.repeat.redis.enums.ExpireMode;
import com.qjx.repeat.redis.enums.Xmode;
import com.qjx.repeat.redis.utils.CmdBuildUtils;
import com.qjx.repeat.redis.utils.SymbolUtils;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public class SetStringCmd extends AbstractSetCmd<String> implements CmdResp<String, Boolean> {

    public SetStringCmd(String key, String value) {
        this(key, value, null, 0, null);
    }

    public SetStringCmd(String key, String value, Xmode xmode) {
        this(key, value, null, 0, xmode);
    }

    public SetStringCmd(String key, String value, ExpireMode expireMode, long expireTime) {
        this(key, value, expireMode, expireTime, null);
    }

    public SetStringCmd(String key, String value, ExpireMode expireMode, long expireTime, Xmode xmode) {
        super(key,
                value,
                expireMode == null ? null : expireMode.getType(),
                String.valueOf(expireTime),
                xmode == null ? null : xmode.getType());
    }


    @Override
    public String build() {
        return CmdBuildUtils.buildString(getCmd(), paramList);
    }

    @Override
    public Boolean parseResp(String resp) {
        char ch = resp.charAt(0);
        // 一般返回 +OK 就是成功
        if (ch == SymbolUtils.OK_PLUS.charAt(0)) {
            return true;
        }
        return false;
    }
}
