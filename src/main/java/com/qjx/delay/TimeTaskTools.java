package com.qjx.delay;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by qincasin on 2021/9/7.
 */
public class TimeTaskTools {
    private static final int TASK_THRESHOLD = 5000;
    private static ConcurrentHashMap<String, Timeout> taskMap = new ConcurrentHashMap<>(256);

    public static ConcurrentHashMap<String, Timeout> getTaskMap() {
        return taskMap;
    }

    public static void addTask(String key, TimerTask timerTask, long delay, TimeUnit unit) {
        if (taskMap.size() > TASK_THRESHOLD) {
            taskMap.values().stream().forEach(timeout -> timeout.cancel());
            taskMap.clear();
        }
        closeTimer(key);

        Timeout timeout = TimerHolder.TIMER.newTimeout(timerTask, delay, unit);
        taskMap.put(key, timeout);
    }

    public static void closeTimer(String key) {
        Timeout timeout = taskMap.get(key);
        if (timeout != null) {
            timeout.cancel();
        }
        taskMap.remove(key);
    }


    private static class TimerHolder {
        private static HashedWheelTimer TIMER = new HashedWheelTimer(20, TimeUnit.MILLISECONDS);
    }

}
