package com.qjx.repeat.core;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;


/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/22
 * @author <others>
 */
public class ProtostuffHelper {

    public ProtostuffHelper() {
    }

    public static <T> byte[] serializeObject(T object, Class<T> clz) {
        Schema<T> schema = RuntimeSchema.createFrom(clz);
        LinkedBuffer buff = LinkedBuffer.allocate(512);
        return ProtostuffIOUtil.toByteArray(object, schema, buff);
    }

    public static <T> T deserializeObject(byte[] object, Class<T> clz) {
        RuntimeSchema schema = RuntimeSchema.createFrom(clz);
        Object t;
        try {
            t = clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("init object failed.");
        }
        ProtostuffIOUtil.mergeFrom(object, t, schema);
        return (T) t;
    }
}
