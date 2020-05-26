package com.company;

/*
    锁
    1. 被锁住的代码块也叫做临界区，同一时刻只能有一个线程访问
    2. 加锁关键字 synchronized(lock){} ，这段代码执行完毕，无论是否抛出异常，都会释放锁

    JVM自定义的原子操作：
    1. 基础变量类型(除long和double)的赋值语句， int n = m
    2. 引用类型赋值语句，List<String> list = anotherList
    3. 如果代码只有一步操作：只读取共享变量或者只写入共享变量，可以不用加锁。有多步操作就得加锁
    n = m 等价于 synchronized(lock) { n = m }，不需要加锁，因为加锁不加锁效果一样

 */

import java.util.LinkedList;
import java.util.Queue;

public class LockLearn {

    public void fun1(){
        var c1 = new Counter2();
        var c2 = new Counter2();

        // lambda 表达式内部可以直接访问外部的变量，但不能修改外部的变量，内部变量名也不能与外部相同
        // 以c1为锁
        new Thread(() -> {
            c1.add(1);
        }).start();
        new Thread(() -> {
            c1.dec(1);
        }).start();

        // 以c2为锁
        new Thread(() -> {
            c2.add(1);
        }).start();
        new Thread(() -> {
            c2.dec(1);
        }).start();
    }

}

/*
    1. 使用同一个锁的多个线程才能确保相互之间对共享变量操作的原子性
    2. 锁是一个对象实例
 */
class Counter {
    public static final Object lock = new Object();     // 锁
    public static int count = 0;    // 共享变量
}

class AddThread extends Thread {
    public void run() {
        for (int i=0; i<10000; i++) {
            synchronized(Counter.lock) {    // 获取锁
                Counter.count += 1;         // 原子操作
            }
        }
    }
}

class DecThread extends Thread {
    public void run() {
        for (int i=0; i<10000; i++) {
            synchronized(Counter.lock) {
                Counter.count -= 1;
            }
        }
    }
}

// 线程安全类
class Counter2 {
    private int count = 0;

    public void add(int n) {
        synchronized(this) {    // 使用本对象的实例this作为锁
            count += n;
        }
    }
    public synchronized void dec(int n) {   // 等同于add，锁住this
        count -= n;
    }

    public int get() {
        return count;
    }
    /*
        1. static方法是没有实例this的，所以锁的是虚拟机为Counter2类创建的唯一class对象实例Counter2.class
     */
    public synchronized static void test(int n) {
        return;
    }
}

//重入锁，线程获得一个锁之后可以继续获得该锁
class Counter3 {
    private int count = 0;

    public synchronized void add(int n) {
        if (n < 0) {
            dec(-n);    // 再次获取相同锁
        } else {
            count += n;
        }
    }

    public synchronized void dec(int n) {
        count += n;
    }
}


/*
    wait() 和 notify()
    1. lock.wait()
        1.1 当前线程释放锁，并等待唤醒
        1.2 唤醒之后重新尝试获取锁，再次获取锁之后再继续执行后续操作
    2. lock.notify/notifyAll()
        2.1 唤醒等待该lock的线程
        2.2 有多个线程等待唤醒

 */
class TaskQueue1 {
    Queue<String> queue = new LinkedList<>();

    public synchronized void addTask(String s) {
        this.queue.add(s);
        // this.notify();   唤醒一个wait()线程，可能会导致有些线程一直无法唤醒，不建议使用
        this.notifyAll();   // 唤醒所有wait()线程
    }

    public synchronized String getTask() throws InterruptedException{
        while (queue.isEmpty()) {
            // 释放锁，当前线程进入等待状态
            this.wait();
            // 重新尝试获取锁，还是等待状态，获取到锁之后才是运行状态，获取锁之后接着之前的代码运行
        }
        return queue.remove();
    }
}
