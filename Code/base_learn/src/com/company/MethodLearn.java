package com.company;

/*
    方法
    1. 参数传递
        1.1 基本类型参数传递，传的是值的复制，方法内和方法外互不影响
        1.2 引用的传递，传的是引用的复本(指针)，指向内存中同一个对象，方法内和方法外互相影响

 */


public class MethodLearn {

    private String name = null;
    // this：当前实例
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String[] names;
    /*
        可变参数： 类型...
        setNames1("Xiao Ming", "Xiao Hong")
        setNames1() -> this.names = []
     */
    public void setNames1(String... names) {
        this.names = names;
    }
    // 可以传入null  setNames2(null) -> this.names = null
    public void setNames2(String[] names) {
        this.names = names;
    }

}

// 构造函数
class People {
    private String name;
    private int age;

    /*
        1. 构造方法，与类同名，对象实例化自动调用
        2. 可以有多个参数不同的构造方法
     */
    public People(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public People(String name) {
        this.name = name;
        this.age = 12;
        // 等同于 this(name,12);
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }
}


// 方法重载，方法名相同，参数不同或者返回值不同
class Hello {
    public void hello() {
        System.out.println("Hello, world!");
    }

    public void hello(String name) {
        System.out.println("Hello, " + name + "!");
    }

    public void hello(String name, int age) {
        if (age < 18) {
            System.out.println("Hi, " + name + "!");
        } else {
            System.out.println("Hello, " + name + "!");
        }
    }
}