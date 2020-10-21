package com.qjx.netty.test;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by qincasin on 2020/10/15.
 */
public class Main {
    public static void main(String[] args) {
//        //117.11911
//        //31.827513
//        int  = 42EA3CFC;
//        // 41FE9EBF;
////        int a = 0xFFB0 ; //-80
////        int a = 0xFF30 ; //48
////        int a = 0xFF80 ; //-128
//
//        //FFB0 FF30 FF80
////        int a = 0x30FF8022 ;
//        //30FF8022;
//        //9010;;
//        ByteBuffer buffer = ByteBuffer.wrap(new byte[4]);
//        buffer.asIntBuffer().put(a);
//        String s = Arrays.toString(buffer.array());
//        System.out.println("默认字节序:"+buffer.order().toString()+" 内存字节序:"+s);

        byte[] bytes = FormatTransfer.toHH(0xFF30,3);
        System.out.println(Arrays.toString(bytes));
//        System.out.println(Arrays.toString(bytes));
//        float f1 = FormatTransfer.hBytesToFloat(buffer.array());
//        System.out.println(f1);
//        //42EA3CFC41FE9EBF
        //
//        String a = "FFB0";
//        Integer integer = Integer.valueOf(a.trim(), 16);
//        System.out.println(integer);
//        float v = Float.intBitsToFloat(Integer.valueOf(a.trim(), 16));
//        System.out.println(v);
//        ByteBuffer buff = ByteBuffer.wrap(new byte[4]);
//        buff.asIntBuffer().put(a)
//        long i = Long.parseLong(a, 16);
//        System.out.println(i);

        System.out.println((byte) (0xFF30 & 0xff));
        System.out.println((0xFF30 & 0xff));
        long ff30 = Long.parseLong("FF30", 16);
        System.out.println(ff30&0x0FF);
        int ffb0 = Integer.parseInt("FFB0", 16) & 0xff;
        int ffb1 = Integer.parseInt("FF30", 16) & 0xff;
        ;
        int ffb2 = Integer.parseInt("FF80", 16) & 0xff;
        ;
        System.out.println(ffb0+" "+ffb1+" "+ffb2);



        int ffb3 = 0xFFB0 & 0xff;
        int ffb4 = 0xFF30 & 0xff;
        int ffb5 = 0xFF80 & 0xff;

        System.out.println("--------");
        System.out.println(ffb3+" "+ffb4+" "+ffb5);

        byte[] ffb6 = FormatTransfer.toHH(0xFFB0,3);
        byte[] ffb7 = FormatTransfer.toHH(0xFF30,3);
        byte[] ffb8 = FormatTransfer.toHH(0xFF80,3);

        System.out.println(Arrays.toString(ffb6)+" "+ Arrays.toString(ffb7)+" "+Arrays.toString(ffb8));
//        System.out.println(L);

    }
}
