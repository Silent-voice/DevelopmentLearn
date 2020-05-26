package com.company;


import java.util.concurrent.*;

public class ThreadLearn {
    /*
        线程创建方法
        1. 创建Thread的自定义子类，覆写run()方法
        2. 创建Thread实例，传入一个自定义的Runnable实例
        3. run()方法不能有参数

        线程状态
        1. 线程run()方法执行完就结束了
        2. 线程的不同状态
            New     新创建，尚未执行
            Runnable    Blocked(阻塞)     Waiting(等待)     Timed Waiting(sleep等待)
            Terminated  终止

        守护线程
        1. JVM退出时不需要关心守护线程是否结束
        2. 守护线程不能持有任何需要关闭的资源，如文件等。因为JVM退出时，这些资源不会被释放，会造成数据缺失

        线程间的共享变量：
        1. 静态变量，位于共享内存
        2. 主线程内定义的变量，也位于共享内存，子线程获取到了该变量的引用之后就可以共享操作





     */

    public void fun1() throws InterruptedException{
        Thread t1 = new MyThread1();
        Thread t2 = new Thread(new MyRunnable1());


        // 设置线程优先级 1~10, 默认Thread.NORM_PRIORITY = 5
        t1.setPriority(Thread.NORM_PRIORITY + 3);
        // 设置线程为守护线程
        t1.setDaemon(true);

        // 启动线程，执行run()方法，直接调用run()方法只是一个普通的方法调用，不会创建新的线程
        t1.start();

        // 当前线程阻塞，等待t1线程执行结束
        t1.join();      // join(long millis) 设置等待时间，超时就不等待了

        t2.start();
    }

    /*
        线程终止
        1. 使用t.interrupt()方法向线程t发送停止信号，线程t如果正确处理了该信号，就会停止
     */
    public void fun2() throws InterruptedException{
        Thread t = new MyThread2();
        t.start();
        Thread.sleep(1000);
        t.interrupt();      // 发送停止信号
        t.join();
        System.out.println("end");
    }

    /*
        volatile 关键字
        1. 解决变量的可见性
        2. 每个线程都直接从内存中读取该变量的最新值，写也是直接写到内存，而不是使用cache进行缓存
        3. 并不能解决数据冲突，只能确保读的是最新数据，但多个线程可以同时读，然后各自写回，产生冲突
     */


}

class MyThread1 extends Thread {
    @Override
    public void run() {

        try {
            Thread.sleep(10);       // 线程sleep，单位ms
        } catch (InterruptedException e){}

        System.out.println("start new thread!");
    }
}
class MyRunnable1 implements Runnable {
    @Override
    public void run() {
        System.out.println("start new thread!");
    }
}


class MyThread2 extends Thread {
    public void run() {
        Thread hello = new HelloThread();
        hello.start(); // 启动hello线程
        try {
            hello.join();       // 在join()等待时收到interrupt()信号会抛出InterruptedException异常
        } catch (InterruptedException e) {
            System.out.println("interrupted!");
        }
        hello.interrupt();      // 一定要发送停止信号interrupt()，才会停止hello线程
    }
}

class HelloThread extends Thread {
    public void run() {
        int n = 0;
        while (!isInterrupted()) {      // 正确处理停止信号
            n++;
            System.out.println(n + " hello!");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}


/*
        线程池
        1. ExecutorService接口封装了线程池对象需要实现的方法，线程池具体的实现在Executors类中
        2. 线程池分三种
            2.1 FixedThreadPool：线程数固定的线程池；
            2.2 CachedThreadPool：线程数根据任务动态调整的线程池；
            2.3 SingleThreadExecutor：仅单线程执行的线程池。
            2.4 ScheduledThreadPool：定时执行任务的线程池
 */

class ThreadPool{

    public void fun1(){
        // 创建线程数为4的固定线程池
        ExecutorService es = Executors.newFixedThreadPool(4);

        /*
            向线程池添加任务，参数是一个实现runnable接口的对象实例
         */
        for (int i = 0; i < 6; i++) {
            es.submit(new Task1("" + i));
        }
        // 关闭线程池:
        es.shutdown();      // 等待所有线程执行完再关闭
        // es.shutdownNow();    立即停止所有线程
        // es.awaitTermination(long, unit);   全部线程执行完，或者达到等待时间，或者当前线程收到终止信号，三者满足一个就关闭线程池
    }

    // 定时任务，3种执行方式
    public void fun2(){
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(4);

        // 执行一次 : 1s后开始执行
        ses.schedule(new Task1("one-time"), 1, TimeUnit.SECONDS);

        //FixedRate模式： 2s后开始执行，每隔3s启动一个任务，不管之前启动的任务有没有完成，所以这里可能会占用多个线程
        ses.scheduleAtFixedRate(new Task1("fixed-rate"), 2, 3, TimeUnit.SECONDS);

        // FixedDelay模式：2s后开始执行，上一个任务完成后3s启动下一个任务，每次只有一个线程在执行任务
        ses.scheduleWithFixedDelay(new Task1("fixed-delay"), 2, 3, TimeUnit.SECONDS);

    }

}


class Task1 implements Runnable {
    private final String name;

    // run()方法不能传参，所以在构造方法里传参
    public Task1(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("start task " + name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("end task " + name);
    }
}


/*
        线程返回值：Future
        1. 使用 Callable 接口封装线程任务，返回Future对象
        2. Future 对象方法
            2.1 get()：获取结果，子线程未执行完会阻塞等待
            2.2 get(long timeout, TimeUnit unit)：获取结果，但只等待指定的时间；
            2.3 cancel(boolean mayInterruptIfRunning)：取消当前任务；
            2.4 isDone()：判断任务是否已完成。
 */

class CallableFuture{
    public void fun1() throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Callable<String> task = new Task2("111");
        // 提交任务并获得Future:
        Future<String> future = executor.submit(task);


        while (!future.isDone()){
            Thread.sleep(1000);
        }
        // 从Future获取异步执行返回的结果:
        String result = future.get();
    }

}

// 可以定义线程返回值类型 Callable<V>
class Task2 implements Callable<String>{
    private final String name;
    public Task2(String name) {
        this.name = name;
    }

    public String call() throws Exception {
        return this.name;
    }
}




/*
        ThreadLocal
        1. 为每个线程单独创建一个集合，该线程内的所有方法都可以访问这个集合内的元素，不再需要使用参数传递给各个方法
        2. 不同线程之间是独立的，各自维护自己的集合

        Thread类内部定义了一个变量ThreadLocal.ThreadLocalMap threadLocals = null;
        ThreadLocal类封装了对上述变量的管理方法

 */

class ThreadLocalLearn{


}

class Task3 implements Runnable{
    static ThreadLocal<String> localVar1 = new ThreadLocal<>();
    static ThreadLocal<Long> localVar2 = new ThreadLocal<>();

    @Override
    public void run() {
        try {
            String cn = Thread.currentThread().getName();   // 获取当前线程名
            localVar1.set(cn);       // 将cn设置为整个线程的共享值

            Long id = Thread.currentThread().getId();
            localVar2.set(id);

            fun1();
            fun2();

        }finally {
            localVar1.remove();      // 清除设置的值，否则会发生内存泄漏
            localVar2.remove();
        }
    }

    // 此时不需要传参，就可以直接获取想要的数据
    public void fun1(){
        String cn = localVar1.get();
        System.out.println(cn + " : fun1");
    }

    public void fun2(){
        Long id = localVar2.get();
        System.out.println(id.toString() + " : fun2");
    }

}
