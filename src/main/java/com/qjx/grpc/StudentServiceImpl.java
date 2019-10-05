package com.qjx.grpc;

import com.qjx.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    private static final Logger logger = Logger.getLogger(StudentServiceImpl.class.getName());

    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {

        System.out.println("接受到客户端信息：" + request.getUsername());

        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三").build());
        responseObserver.onCompleted();

    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {

        System.out.println("接收到客户端信息：" + request.getAge());

        responseObserver.onNext(StudentResponse.newBuilder().setName("张三").setAge(20).setCity("南京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("李四").setAge(30).setCity("北京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("王五").setAge(40).setCity("天津").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("赵六").setAge(50).setCity("杭州").build());

        responseObserver.onCompleted();

    }

    @Override
    public StreamObserver<StudentRequest> getStudentWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            //Receives a value from the stream.
            @Override
            public void onNext(StudentRequest value) {
                logger.info("onNext:" + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.WARNING, "rpc failed :{}", t.getMessage());
            }

            @Override
            public void onCompleted() {
                StudentResponse studentResponse = StudentResponse
                        .newBuilder()
                        .setName("张三")
                        .setAge(20)
                        .setCity("西安")
                        .build();
                StudentResponse studentResponse2 = StudentResponse
                        .newBuilder()
                        .setName("李四")
                        .setAge(30)
                        .setCity("广州")
                        .build();
                StudentResponseList list = StudentResponseList
                        .newBuilder()
                        .addStudentResponse(studentResponse)
                        .addStudentResponse(studentResponse2)
                        .build();
                responseObserver.onNext(list);
                responseObserver.onCompleted();

            }
        };
    }

    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequest>() {
            @Override
            public void onNext(StreamRequest value) {
                System.out.println(value.getRequestInfo());

                responseObserver.onNext(
                        StreamResponse
                                .newBuilder()
                                .setResponseInfo(
                                        UUID.randomUUID().toString()
                                )
                                .build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
