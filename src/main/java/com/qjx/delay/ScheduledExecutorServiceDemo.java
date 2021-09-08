package com.qjx.delay;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by qincasin on 2021/9/7.
 */
public class ScheduledExecutorServiceDemo {
    public static void main(String[] args) {
        System.out.println("程序启动时间"+ LocalDateTime.now());
        scheduledExecutorServiceTask();
    }

    private static void scheduledExecutorServiceTask() {
       ScheduledExecutorService executors = Executors.newScheduledThreadPool(1);
       executors.scheduleAtFixedRate(() -> System.out.println("执行任务 ，执行时间：" + LocalDateTime.now()),
               3,  //初次执行间隔时间
               2, //2 秒执行一次
               TimeUnit.SECONDS);
    }
}
