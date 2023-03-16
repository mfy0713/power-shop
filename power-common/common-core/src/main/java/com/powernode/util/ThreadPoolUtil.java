package com.powernode.util;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {

    public static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            4,  //核心线程数
            Runtime.getRuntime().availableProcessors()*2,   //cpu核数*2
            30, //等待时间
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(10000),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()    //拒绝策略
    );
}
