package com.example.demo.asyncThread;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor(){
        //定义线程池
        ThreadPoolTaskExecutor taskExecutor =new ThreadPoolTaskExecutor();
        //核心线程数，即存活线程总数
        taskExecutor.setCorePoolSize(10);
        //线程池最大线程数，即总共创建的线程总数，包括因为各种原因kill掉，已经不在线程池内的线程
        taskExecutor.setMaxPoolSize(30); ;
        //线程队列最大线程数
        taskExecutor.setQueueCapacity(2000);
        //初始化
        taskExecutor.initialize();
        return taskExecutor;
    }

}
