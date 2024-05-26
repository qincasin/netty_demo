package com.qjx.repeat.redis.connections;

import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <Description>
 *
 * @author qinjiaxing on 2024/5/26
 * @author <others>
 */
public class RedisConnection<T> {


    private NioSocketChannel channel;
    // 用于锁定该连接
    private Lock lock;
    // 一个同步队列, 用于从netty中的handler中获取redis的返回
    private SynchronousQueue<T> synchronousQueue;
    // 连接的名称
    private String name;

    public RedisConnection(String name, NioSocketChannel channel, SynchronousQueue<T> synchronousQueue) {
        this.name = name;
        this.channel = channel;
        this.synchronousQueue = synchronousQueue;
        this.lock = new ReentrantLock();
    }

    public void cleanUp() {
    }

    /**
     * 从synchronousQueue获取redis的返回内容,在AbstractRedisClient中被调用
     *
     * @param timeOut
     * @return
     * @throws InterruptedException
     */
    public T getResp(long timeOut) throws InterruptedException {
        return synchronousQueue.poll(timeOut, TimeUnit.MILLISECONDS);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    /***********************代理channel的几个方法*******************************/

    public ChannelFuture writeAndFlush(Object msg) {
        return channel.writeAndFlush(msg);
    }

    public void disconnect() {
        channel.disconnect();
    }

    public void close() {
        channel.close();
    }

    public boolean isActive() {
        return channel.isActive();
    }

}
