package com.qjx.zerocopy;

import java.io.*;
import java.net.Socket;

/**
 * Created by qincasin on 2020/10/21.
 */
public class OldIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8899);

        String fileName = "/Users/qinjiaxing/Downloads/openjdk-13.0.2_osx-x64_bin.tar.gz";

        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        InputStream fileInputStream = new FileInputStream(fileName);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;
        long start = System.currentTimeMillis();
        System.out.println("开始时间:"+ start);

        while ((readCount = dataInputStream.read(buffer)) > 0) {
            total +=readCount;
            outputStream.write(buffer);
        }
        System.out.println("总数量:" + total + ",耗时:" + (System.currentTimeMillis() - start));
        outputStream.close();
        socket.close();
        fileInputStream.close();



    }
}
