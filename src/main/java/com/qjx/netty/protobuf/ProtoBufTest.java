package com.qjx.netty.protobuf;

public class ProtoBufTest {
    public static void main(String[] args) throws Exception {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("张三")
                .setAge(20)
                .setAddress("南京")
                .build();

        byte[] bytes = student.toByteArray();

        DataInfo.Student student2 = DataInfo.Student.parseFrom(bytes);

        System.out.println(student2);
        System.out.println(student2.getName());
        System.out.println(student2.getAddress());
        System.out.println(student2.getAge());
    }
}
