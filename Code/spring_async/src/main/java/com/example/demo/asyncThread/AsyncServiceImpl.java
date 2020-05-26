package com.example.demo.asyncThread;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService{

    @Override
    @Async      // 启用异步线程调用该方法
    public void generateReport() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("报表线程名称：[" + Thread.currentThread().getName() + "]");
    }
}
