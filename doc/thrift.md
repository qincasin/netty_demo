# thrift
## thrift 传输格式 protocol
- TBinaryProtocol - 二进制格式
- TCompactProtocol- 压缩格式
- TJsonProtocol-Json格式 (文本效率低)
- TSimpleJsonProtocol-提供Json只写协议，生成的文件很容易通过脚本语言解析(极少使用)
- TDebugProtocol-使用易懂的可读的文本格式，以便于debug

## 传输方式  transport
- TSocket-阻塞式socket(使用的最少)
- TFramedTransport - 以frame为单位进行传输，非阻塞式服务中使用
- TFileTransport-以文件形式进行传输
- TMemoryTransport-以内存用于I/O。java实现时内部实际使用了简单ByteArrayOutputStream
- TZipTransport-使用zlib进行压缩，与其他传输方式联合使用，当前无java实现
    
## 支持的服务模型 server
- TSimpleServer 简单的单线程服务模型，常用于测试
- TThreadPoolServer  多线程服务模型，使用标准的阻塞式IO
- TNonblockingServer 多线程服务模型，使用非阻塞式IO(需使用TFramedTransport数据传输方式)
- THsHaServer THsHa以入了线程池去处理，其模型把读写任务放在线程池去处理；
Half-sync/Half-async 的处理模型，Half-async是在使用IO事件上(accept/read/write io),Half-sync用于handler对rpc的同步处理


