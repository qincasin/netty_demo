# Buffer
## 分片 slice
>分割 分片 Slice sliceBuffer 和原有buffer共享相同的底层数组

>结合 go 语言的分片理解下 .....

```java
  /**
     * Creates a new byte buffer whose content is a shared subsequence of
     * this buffer's content.
     *
     * <p> The content of the new buffer will start at this buffer's current
     * position.  Changes to this buffer's content will be visible in the new
     * buffer, and vice versa; the two buffers' position, limit, and mark
     * values will be independent.
     *
     * <p> The new buffer's position will be zero, its capacity and its limit
     * will be the number of bytes remaining in this buffer, its mark will be
     * undefined, and its byte order will be

     * {@link ByteOrder#BIG_ENDIAN BIG_ENDIAN}.



     * The new buffer will be direct if, and only if, this buffer is direct, and
     * it will be read-only if, and only if, this buffer is read-only.  </p>
     *
     * @return  The new byte buffer

     *
     * @see #alignedSlice(int)

     */
    @Override
    public abstract ByteBuffer slice();
```

## 只读buffer
> 只读buffer
我们可以随时将一个普通Buffer调用asReadOnlyBuffer 方法返回一个只读buffer
但不能将一个只读buffer转换为读写Buffer

- 对应的put等方法 源码中是通过直接throw ex实现的 

```java
/**
 * 只读buffer
 * 我们可以随时将一个普通Buffer调用asReadOnlyBuffer 方法返回一个只读buffer
 * 但不能将一个只读buffer转换为读写Buffer
 */
public class NioTest7 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println(byteBuffer.getClass()); //class java.nio.HeapByteBuffer
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());  //class java.nio.HeapByteBufferR

        readOnlyBuffer.position(0);
//        readOnlyBuffer.put((byte)2);   //报错 只读 buffer异常


    }
}
```

## 直接缓冲 !!!
###  Buffer类中**address**成员变量的作用

- **如下摘自 jdk 12 中的注释 (jdk8中只作为Direct buffer 在JNI中提速)**
> java 内存模型中 通过如下的address 地址来**调用堆外内存** (address表示在堆外内存当中所分配的真正数据地址)

```java
         // Used by heap byte buffers or direct buffers with Unsafe access
        // For heap byte buffers this field will be the address relative to the
        // array base address and offset into that array. The address might
        // not align on a word boundary for slices, nor align at a long word
        // (8 byte) boundary for byte[] allocations on 32-bit systems.
        // For direct buffers it is the start address of the memory region. The
        // address might not align on a word boundary for slices, nor when created
        // using JNI, see NewDirectByteBuffer(void*, long).
        // Should ideally be declared final
        // NOTE: hoisted here for speed in JNI GetDirectBufferAddress
       long address;
```

- HeapBuffer (间接) A ->CPU  , CPU-->B
- DirectBuffer(直接) A ->B  ----**零拷贝** 

## 内存映射文件