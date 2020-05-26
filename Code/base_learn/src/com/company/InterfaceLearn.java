package com.company;

/*
    接口
    1. 弥补一个类只能继承一个父类的不足
    2. 一个类可以实现多个接口
    3. 接口所有方法都默认public abstract修饰，变量默认是public static final修饰，所以可以不写前面这些描述
    4. 方法用public修饰，这样具体实现类才能访问到这个方法，进而对其进行修改，和继承机制一样，能够访问到才能重写
    5. 也可以定义为default方法，这样具体实现类可以不实现该方法，也能调用
    6. 接口只定义需要实现的方法，所有拥有该接口的类都得实现这些方法，而且也要用public修饰
 */


public class InterfaceLearn {

}


interface HelloInterface {
    void hello();
}

/*
    1. 接口可以继承，此时实现PersonInterface接口的类得实现3个方法
    2. 对于default方法，子类不一定要去重写
 */
interface PersonInterface extends HelloInterface{
    public static final int MALE = 1;
    int FEMALE = 2;     // public static final可以不写

    abstract void run();
    String getName();   // abstract 可以不写

    default void dm(){
        System.out.println("Default method");
    }
}

/*
    @Override : 标签，类似注释，说明这个方法是重写方法，编译器会检查父类和接口中有没有该方法，没有就报错
 */

class StudentInt implements PersonInterface {
    private String name;

    public StudentInt(String name) {
        this.name = name;
    }

    @Override
    public void hello() {
        System.out.println("hello");
    }

    @Override
    public void run() {
        System.out.println(this.name + " run");
    }

    @Override
    public String getName() {
        return this.name;
    }

}
