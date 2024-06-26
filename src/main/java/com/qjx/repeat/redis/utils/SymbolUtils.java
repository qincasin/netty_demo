package com.qjx.repeat.redis.utils;

/**
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public class SymbolUtils {

    /**
     * 普通字符串以+开始
     */
    public static final String OK_PLUS = "+";
    public static final byte[] OK_PLUS_BYTE = new byte[]{(byte) '+'};
    /**
     * 异常或error 以-开始
     */
    public static final String ERR_MINUS = "-";
    public static final byte[] ERR_MINUS_BYTE = new byte[]{(byte) '-'};
    /**
     * 整数 以:开始
     */
    public static final String INT_COLON = ":";
    public static final byte[] INT_COLON_BYTE = new byte[]{(byte) ':'};
    /**
     * 数组以*开始
     */
    public static final String ARRAY_STAR = "*";
    public static final byte[] ARRAY_STAR_BYTE = new byte[]{(byte) '*'};
    /**
     * bulk 字符串以$开始
     */
    public static final String BULK_DOLLAR = "$";
    public static final byte[] BULK_DOLLAR_BYTE = new byte[]{(byte) '$'};
    /**
     * CRLF 以\r\n结束
     */
    public static final String CRLF = "\r\n";
    public static final byte[] CRLF_BYTE = new byte[]{(byte) '\r', (byte) '\n'};
}
