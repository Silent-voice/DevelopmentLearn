package com.company;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.*;
import java.util.concurrent.*;

/*
    concurrent集合
    1. 线程安全类：List、Map、Set、Queue，不需要用户单独实现，像普通集合一样使用

    原子操作封装类
    1. AtomicInteger 为例，提供了4个方法
        1.1 int addAndGet(int delta) 、int incrementAndGet()、int get()、int compareAndSet(int expect, int update)
 */

public class ReentrantLockLearn {

    List<Integer> safe_list = new CopyOnWriteArrayList<>();
    Map<String, String> safe_map = new ConcurrentHashMap<>();
    Set<String> safe_set = new CopyOnWriteArraySet<>();

    AtomicInteger var = new AtomicInteger(0);
}

/*
    ReentrantLock
    1. 加锁和解锁需要自己控制，更麻烦，但也更灵活，获取不到锁时可以执行其他操作，不像synchronized只能等待
    2. 多了很多功能，支持公平锁(等待时间越长越容易获取锁)和非公平锁
    3. 能够接收中断

    Condition
    1. 实现lock的 wait() 和 notifyAll() 功能
 */

class TaskQueue2 {
    // 创建ReentrantLock
    private final Lock lock = new ReentrantLock();
    // 创建一个绑定lock的Condition对象
    private final Condition condition = lock.newCondition();
    private Queue<String> queue = new LinkedList<>();

    public void addTask(String s) {
        lock.lock();    // 手动获取锁
        try {
            queue.add(s);
            // condition.signal();  等同于notify
            condition.signalAll();  // 等同于notifyAll
        } finally {         // 必须写成try{}finally{}这种格式
            lock.unlock();  // 手动释放锁
        }
    }

    public String getTask() throws InterruptedException{
        lock.lock();
        try {
            while (queue.isEmpty()) {
                condition.await();  // 等同于wait
            }
            return queue.remove();
        } finally {
            lock.unlock();
        }
    }


    public void addTask2 (String s) throws InterruptedException{
        // 尝试获取锁，等待1s，如果未获取锁返回false；否则返回true，此时锁归本进程所有
        if (lock.tryLock(1, TimeUnit.SECONDS)) {
            try {
                queue.add(s);
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("don't get lock");
        }
    }

    public String getTask2() throws InterruptedException{
        lock.lock();
        try {
            while (queue.isEmpty()) {
                // 睡眠一段时间，没有被其他线程唤醒返回false；否则返回true
                if (condition.await(1, TimeUnit.SECONDS)) {
                    System.out.println("be signal");
                } else {
                    System.out.println("don't be signal");
                }
            }
            return queue.remove();
        } finally {
            lock.unlock();
        }
    }


}


/*
    ReadWriteLock
    1. 允许多个进程共同读取共享资源，但当一个进程进行写操作时，其他进程都无法获取该资源
    2. 当有进程在读时，写进程要等待读进程结束才能写
    3. 适合多读少写的情景

 */

class Counter4 {
    private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
    private final Lock rlock = rwlock.readLock();   // 读锁，可多进程访问
    private final Lock wlock = rwlock.writeLock();  // 写锁
    private int[] counts = new int[10];

    public void inc(int index) {
        wlock.lock();       // 加写锁
        try {
            counts[index] += 1;
        } finally {
            wlock.unlock(); // 释放写锁
        }
    }

    public int[] get() {
        rlock.lock();       // 加读锁
        try {
            return Arrays.copyOf(counts, counts.length);
        } finally {
            rlock.unlock(); // 释放读锁
        }
    }
}


/*
    StampedLock
    1. 一种更复杂的读写锁，允许在读的时候写

 */
class Point {
    private final StampedLock stampedLock = new StampedLock();

    private double x;
    private double y;

    public void move(double deltaX, double deltaY) {
        long stamp = stampedLock.writeLock();   // 获取写锁
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            stampedLock.unlockWrite(stamp);     // 释放写锁
        }
    }

    public double distanceFromOrigin() {
        // 获得一个乐观读锁
        // 本质获取的是一个版本号，如果发生了写操作，该版本号就会改变，我们就能知道这次读取的数据无效了
        long stamp = stampedLock.tryOptimisticRead();

        double currentX = x;
        double currentY = y;

        // 检查版本号是否发生变化
        if (!stampedLock.validate(stamp)) {
            stamp = stampedLock.readLock();     // 获取一个悲观读锁，此时其他进程不可写了
            try {
                currentX = x;
                currentY = y;
            } finally {
                stampedLock.unlockRead(stamp); // 释放悲观读锁
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}