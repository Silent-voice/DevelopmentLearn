package com.company;

/*
    继承
    1. 子类继承父类的所有方法和参数
    2. 子类可以增加新的方法和参数，也可以重写父类的方法
    3. 每个类只能继承于一个父类
    4. 因为子类比父类功能多，所以支持向上转型：Father f = new Son(); 不支持向下转型： Son s = new Father();

 */

/*
    private
    1. 只有类内部能访问，比如Person的两个属性name和age，只有类内部方法能访问
    2. 实例化的对象是无法访问这些变量的，比如：Person p = new Person(); p无法访问这些private变量
    3. 子类内部也无法访问父类的private成员
*/

public class ExtendLearn {
}

class Person {
    private String name;
    private int age;
    protected String name2;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        if (age < 0 || age > 100) {
            throw new IllegalArgumentException("invalid age value");
        }
        this.age = age;
    }

    // final方法不允许子类重写，final类不允许继承，final变量不允许修改
    public final void setName2(String name){
        this.name2 = name;
    }

}

class Student extends Person {
    protected int score;

    public Student(String name, int age, int score) {
        // 所有类的构造方法，第一行必须调用父类的构造方法
        super(name, age);
        this.score = score;
    }

    // 新方法
    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
    }


    public String getName() {
        // 子类无法访问父类的private成员
        //return "Son name : " + this.name;
        return "Son name : " + this.name2;
    }

}

/*
    抽象类
    1. 没有任何内容的方法可以定义为抽象方法，由子类去填充方法内容
    2. 带有抽象方法的类无法被实例化，因此类也要定义为抽象类
 */
abstract class FatherClass{
    public abstract int run();

}