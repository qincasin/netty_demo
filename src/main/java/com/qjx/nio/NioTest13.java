package com.qjx.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by qincasin on 2020/1/27.
 */
public class NioTest13 {
    public static void main(String[] args) throws Exception {
        String inputFile = "NioTest13_in.txt";
        String  outputFile= "NioTest13_out.txt";

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile,"r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile,"rw");

        long length = new File(inputFile).length();
        FileChannel inputFileChannel = inputRandomAccessFile.getChannel();
        FileChannel outputFileChannel = outputRandomAccessFile.getChannel();

        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, length);

        System.out.println("==============");
        Charset.availableCharsets().forEach((k,v)->{
            System.out.println(k+", "+v);
        });
        System.out.println("==============");

        Charset charset = StandardCharsets.ISO_8859_1;
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        CharBuffer charBuffer = decoder.decode(inputData);
        ByteBuffer outputBuffer = encoder.encode(charBuffer);

        outputFileChannel.write(outputBuffer);
        inputFileChannel.close();
        outputFileChannel.close();


    }
}
