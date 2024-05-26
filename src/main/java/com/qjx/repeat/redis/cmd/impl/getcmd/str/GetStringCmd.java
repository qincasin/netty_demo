package com.qjx.repeat.redis.cmd.impl.getcmd.str;

import com.qjx.repeat.redis.cmd.CmdResp;
import com.qjx.repeat.redis.cmd.impl.getcmd.AbstractGetCmd;
import com.qjx.repeat.redis.utils.CmdBuildUtils;
import com.qjx.repeat.redis.utils.SymbolUtils;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public class GetStringCmd extends AbstractGetCmd<String> implements CmdResp<String, String> {

    public GetStringCmd(String key) {
        super(key);
    }

    @Override
    public String build() {
        return CmdBuildUtils.buildString(getCmd(), paramList);
    }


    @Override
    public String parseResp(String resp) {
        if (resp == null) {
            return null;
        }
        // 跳过第一位符号位
        int index = 1;
        int len = 0;
        char ch = resp.charAt(index);
        while (ch != SymbolUtils.CRLF.charAt(0)) {
            len = len * 10 + (ch - '0');
            ch = resp.charAt(++index);
        }
        if (len <= 0) {
            return null;
        }
        index += 2;
        return resp.substring(index, index + len);
    }

    public static void main(String[] args) {
        GetStringCmd a = new GetStringCmd("1111");
        System.out.println(a.parseResp("*5\r\n+bar\r\n-unknown command\r\n:3\r\n"));
    }
}
