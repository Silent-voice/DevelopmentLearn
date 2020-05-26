package com.example.demo.scheduleTask;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl {
    int count1 = 1;
    int count2 = 1;

    @Async
    @Scheduled(fixedRate = 1000)
    public void job1(){
        System.out.println("[" + Thread.currentThread().getName() + "] " + " [job1] 每秒钟执行一次，第[" + count1 + "]次");
        count1 ++;
    }

    @Async
    @Scheduled(fixedRate = 1000)
    public void job2(){
        System.out.println("[" + Thread.currentThread().getName() + "] " + " [job2] 每秒钟执行一次，第[" + count2 + "]次");
        count2 ++;
    }

}
