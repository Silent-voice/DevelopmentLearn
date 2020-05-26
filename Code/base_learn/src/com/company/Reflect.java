package com.company;

/*
    反射

 */


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {

    public void fun1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        //获取class对象的3种方法
        Class cls1 = String.class;

        String s1 = "Hello";
        Class cls2 = s1.getClass();

        Class cls3 = Class.forName("java.lang.String");


        // String对象:
        String s = "Hello world";
        // 获取String substring(int)方法，参数为int:
        Method m = String.class.getMethod("substring", int.class);
        // 在s对象上调用该方法并获取结果:
        String r = (String) m.invoke(s, 6);
        // 打印调用结果:
        System.out.println(r);
    }


    public void fun2() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 获取构造方法Integer(int):
        Constructor cons1 = Integer.class.getConstructor(int.class);
        // 调用构造方法:
        Integer n1 = (Integer) cons1.newInstance(123);
        System.out.println(n1);

        // 获取构造方法Integer(String)
        Constructor cons2 = Integer.class.getConstructor(String.class);
        Integer n2 = (Integer) cons2.newInstance("456");
        System.out.println(n2);
    }

}
