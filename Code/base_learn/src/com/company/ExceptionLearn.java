package com.company;

/*
    Exception
    1. 异常是class，继承于Throwable，分为两大类Error和Exception
    2. Error : OutOfMemoryError、StackOverflowError 等严重异常，程序无法解决，不需要处理
    3. Exception : RuntimeException、IOException等
    4. JAVA规定：Error和RuntimeException及其子类不强制捕捉；Exception及其非RuntimeException子类必须要捕捉

 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ExceptionLearn {

    /*
        捕获异常
        1. try {} catch() {}
        2. String.getBytes() 定义时抛出了异常，所以外面调用时必须处理该异常，否则会报错
     */

    public byte[] fun1(String s){
        try {
            return s.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            // 如果系统不支持GBK编码，会捕获到UnsupportedEncodingException:
            System.out.println(e);  // 打印异常信息
            e.printStackTrace();    // 打印异常详细信息，打印信息比较多
            return s.getBytes();    // 尝试使用用默认编码
        }
    }

    /*
       抛出异常
       1. 可以为自己的方法定义需要处理的异常
       2. 对于方法内调用的其他方法，他们抛出的异常，如果不想处理，也可以抛给外面处理，像 String.getBytes() 抛出的异常我们可以往外继续抛出
    */
    public byte[] fun2(String s) throws UnsupportedEncodingException{
        return s.getBytes("GBK");
    }


    /*
        1. 捕获多个异常时，子类在前面，否则子类永远不会被捕捉，Exception可以用于捕获所有异常
        2. 可以将多个异常放在一起
        3. finally 可选，不管有没有发生异常，都会执行finally内的语句，然后再执行抛出异常部分的语句
        4. finally 块中如果抛出异常，其他地方的异常就不会抛出，异常只会抛出一次
     */

    public byte[] fun3(String s){
        try {
            byte [] b_l = s.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Bad encoding");
        } catch (IOException | NumberFormatException e) {
            System.out.println("IO error");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            System.out.println("END");
            return s.getBytes();
        }
    }

    /*
        1. 异常是类，也可以自行抛出
        2. 可以把原始异常的信息传入新的异常，新异常会包含两者的信息，原始异常一定要包含，不然无法定位错误位置
     */
    void fun4(String s) {
        process1();
    }

    void process1() {
        try {
            process2();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e);  // 将之前的异常实例传入新的异常
        }
    }
    void process2() {
        throw new NullPointerException();
    }

}
