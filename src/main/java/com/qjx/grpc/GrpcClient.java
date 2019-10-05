package com.qjx.grpc;

import com.qjx.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrpcClient {

    private static final Logger logger = Logger.getLogger(GrpcClient.class.getName());

    private final ManagedChannel channel;

    private final StudentServiceGrpc.StudentServiceBlockingStub blockingStub;

    private final StudentServiceGrpc.StudentServiceStub stub;

    public GrpcClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build());
    }

    GrpcClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = StudentServiceGrpc.newBlockingStub(channel);
        //Creates a new async stub that supports all call types for the service
        stub = StudentServiceGrpc.newStub(channel);
    }

    private void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private MyResponse send(String username) {
        MyResponse response = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername(username).build());
        return response;
    }

    private Iterator<StudentResponse> send2(Integer age) {
        Iterator<StudentResponse> response = blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(age).build());
        return response;
    }

    /**
     * 客户端发送流式请求
     *
     * @param studentResponseListStreamObserver
     * @return
     */
    private StreamObserver<StudentRequest> send3(StreamObserver<StudentResponseList> studentResponseListStreamObserver) {
        StreamObserver<StudentRequest> studentRequest = stub.getStudentWrapperByAges(studentResponseListStreamObserver);
        return studentRequest;
    }


    private StreamObserver<StreamRequest> send4(StreamObserver<StreamResponse> responseStreamObserver) {
        StreamObserver<StreamRequest> streamRequestStreamObserver = stub.biTalk(responseStreamObserver);
        return streamRequestStreamObserver;
    }


    public static void main(String[] args) throws Exception {
        GrpcClient client = new GrpcClient("localhost", 8899);
        try {
            String user = "zhangsan";
            MyResponse res = client.send(user);
            System.out.println(res.getRealname());

            System.out.println("-------------------------");

            Integer age = 20;

            Iterator<StudentResponse> res2 = client.send2(age);
            while (res2.hasNext()) {
                StudentResponse next = res2.next();
                System.out.println(next.getName() + " , " + next.getAge() + " , " + next.getCity());
            }


            System.out.println("-----------------------------");

            /**
             *  第三种方式： 流 输入， 返回参数列表
             */
            //1.服务端向客户端响应数据后，客户端回调
            StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
                @Override
                public void onNext(StudentResponseList value) {
                    value.getStudentResponseList().forEach(student -> {
                        logger.info("name:" + student.getName());
                        logger.info("age:" + student.getAge());
                        logger.info("city:" + student.getCity());
                        logger.info("*************");
                    });
                }

                @Override
                public void onError(Throwable t) {
                    logger.log(Level.WARNING, t.getMessage());
                }

                @Override
                public void onCompleted() {
                    logger.info("completed!!!!");
                }
            };


            //2.客户端向服务器发送数据
            //只要客户端以流式数据向服务器发送请求，这种请求必须是异步的
            StreamObserver<StudentRequest> studentRequestStreamObserver = client.send3(studentResponseListStreamObserver);

            studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
            studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
            studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
            studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(50).build());

            studentRequestStreamObserver.onCompleted();


            /**
             * 第四种方式
             */


            StreamObserver<StreamResponse> streamObserver = new StreamObserver<StreamResponse>() {
                @Override
                public void onNext(StreamResponse value) {
                    System.out.println(value.getResponseInfo());
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println(t.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("onCompleted!!");
                }
            };
            StreamObserver<StreamRequest> requestStreamObserver = client.send4(streamObserver);

            for (int i = 0; i < 10; i++) {
                requestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());
                Thread.sleep(1000);
            }


            Thread.sleep(50000);

        } finally {
            client.shutdown();
        }


    }
}
