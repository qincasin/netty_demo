package com.qjx.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class GrpcServer {

    private static final Logger logger = Logger.getLogger(GrpcServer.class.getName());

    private Server server;

    private void start() throws IOException {
        int port = 8899;
        this.server = ServerBuilder.forPort(port)
                .addService(new StudentServiceImpl())
                .build()
                .start();
        logger.info("Server started,listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("关闭jvm");
            //jvm关闭的时候 netty server可能会存在不关闭
            GrpcServer.this.stop();
        }));

    }


    private void stop(){
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * 等待被终止
     * @throws InterruptedException
     */
    private void blockUtilShutdown() throws InterruptedException{
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException,InterruptedException {
        final GrpcServer server = new GrpcServer();
        server.start();
        server.blockUtilShutdown();


    }
}
