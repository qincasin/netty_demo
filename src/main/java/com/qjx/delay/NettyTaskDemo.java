package com.qjx.delay;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Created by qincasin on 2021/9/7.
 */
public class NettyTaskDemo {
    public static void main(String[] args) {
        System.out.println("程序启动时间：" + LocalDateTime.now());
        myNettyTask();

    }

    private static void myNettyTask() {
        // 创建延迟任务实例
        HashedWheelTimer timer = new HashedWheelTimer(200, // 时间间隔
                TimeUnit.MILLISECONDS,
                12); // 时间轮中的槽数
        TimerTask task = timeout -> System.out.println("执行任务1，执行时间：" + LocalDateTime.now());
        timer.newTimeout(task,2,TimeUnit.SECONDS);

        timer.newTimeout(
                timeout -> System.out.println("执行任务2，执行时间：" + LocalDateTime.now()),
                10,
                TimeUnit.SECONDS);
    }
}
