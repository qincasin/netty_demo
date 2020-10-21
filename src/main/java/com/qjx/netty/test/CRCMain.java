package com.qjx.netty.test;

import java.util.Arrays;

import static com.qjx.netty.test.Test.crc16_tab_h;
import static com.qjx.netty.test.Test.crc16_tab_l;

/**
 * Created by qincasin on 2020/10/16.
 */
public class CRCMain {
    public static void main(String[] args) {
        int b7DA = Integer.parseInt("B7DA", 16);
        System.out.println(b7DA);
        byte[] bytes = intToByteArray(b7DA);
        System.out.println(Arrays.toString(bytes));
        int[] test = test(bytes);
        System.out.println(Arrays.toString(test));
    }

    public static int[] test(byte[] bytes){
        int index;
        int crcLow  = 0xFF;
        int crcHigh  = 0xFF;
        for(int i = 0; i < bytes.length; i++ ){
            index = crcHigh ^ bytes[i];
            crcHigh = crcLow ^ crc16_tab_h[index];
            crcLow = crc16_tab_l[index];
        }
        return new int[]{crcLow, crcHigh};

    }
    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }
}
