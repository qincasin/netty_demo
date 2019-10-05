// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Student.proto

package com.qjx.proto;

public final class StudentProto {
  private StudentProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_qjx_netty_proto_MyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_qjx_netty_proto_MyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_qjx_netty_proto_MyResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_qjx_netty_proto_MyResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_qjx_netty_proto_StudentRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_qjx_netty_proto_StudentRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_qjx_netty_proto_StudentResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_qjx_netty_proto_StudentResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_qjx_netty_proto_StudentResponseList_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_qjx_netty_proto_StudentResponseList_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_qjx_netty_proto_StreamRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_qjx_netty_proto_StreamRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_qjx_netty_proto_StreamResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_qjx_netty_proto_StreamResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rStudent.proto\022\023com.qjx.netty.proto\"\035\n\t" +
      "MyRequest\022\020\n\010username\030\001 \001(\t\"\036\n\nMyRespons" +
      "e\022\020\n\010realname\030\002 \001(\t\"\035\n\016StudentRequest\022\013\n" +
      "\003age\030\001 \001(\005\":\n\017StudentResponse\022\014\n\004name\030\001 " +
      "\001(\t\022\013\n\003age\030\002 \001(\005\022\014\n\004city\030\003 \001(\t\"T\n\023Studen" +
      "tResponseList\022=\n\017studentResponse\030\001 \003(\0132$" +
      ".com.qjx.netty.proto.StudentResponse\"%\n\r" +
      "StreamRequest\022\024\n\014request_info\030\001 \001(\t\"\'\n\016S" +
      "treamResponse\022\025\n\rresponse_info\030\001 \001(\t2\226\003\n" +
      "\016StudentService\022Z\n\025GetRealNameByUsername" +
      "\022\036.com.qjx.netty.proto.MyRequest\032\037.com.q" +
      "jx.netty.proto.MyResponse\"\000\022a\n\020getStuden" +
      "tsByAge\022#.com.qjx.netty.proto.StudentReq" +
      "uest\032$.com.qjx.netty.proto.StudentRespon" +
      "se\"\0000\001\022l\n\027getStudentWrapperByAges\022#.com." +
      "qjx.netty.proto.StudentRequest\032(.com.qjx" +
      ".netty.proto.StudentResponseList\"\000(\001\022W\n\006" +
      "BiTalk\022\".com.qjx.netty.proto.StreamReque" +
      "st\032#.com.qjx.netty.proto.StreamResponse\"" +
      "\000(\0010\001B\037\n\rcom.qjx.protoB\014StudentProtoP\001b\006" +
      "proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_qjx_netty_proto_MyRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_qjx_netty_proto_MyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_qjx_netty_proto_MyRequest_descriptor,
        new java.lang.String[] { "Username", });
    internal_static_com_qjx_netty_proto_MyResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_qjx_netty_proto_MyResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_qjx_netty_proto_MyResponse_descriptor,
        new java.lang.String[] { "Realname", });
    internal_static_com_qjx_netty_proto_StudentRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_com_qjx_netty_proto_StudentRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_qjx_netty_proto_StudentRequest_descriptor,
        new java.lang.String[] { "Age", });
    internal_static_com_qjx_netty_proto_StudentResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_com_qjx_netty_proto_StudentResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_qjx_netty_proto_StudentResponse_descriptor,
        new java.lang.String[] { "Name", "Age", "City", });
    internal_static_com_qjx_netty_proto_StudentResponseList_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_com_qjx_netty_proto_StudentResponseList_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_qjx_netty_proto_StudentResponseList_descriptor,
        new java.lang.String[] { "StudentResponse", });
    internal_static_com_qjx_netty_proto_StreamRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_com_qjx_netty_proto_StreamRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_qjx_netty_proto_StreamRequest_descriptor,
        new java.lang.String[] { "RequestInfo", });
    internal_static_com_qjx_netty_proto_StreamResponse_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_com_qjx_netty_proto_StreamResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_qjx_netty_proto_StreamResponse_descriptor,
        new java.lang.String[] { "ResponseInfo", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
