package com.company;

/*
    public、static相关知识点
*/


/*
    public
    1. 一个.java源文件只能有一个public类，并且要与文件同名
    2. public类是该编译单元(文件)对外的唯一接口
    3. 编译单元可以没有public类，这样一来，编译时因为无法确定入口，该编译单元内每个非public类都会被编译为单独的class文件
*/


/*
    public : 公开，所有类都有访问权限
    protected : 保护，可以被同一个包内的类和子类访问
    default: 默认的修饰符，可以被同一包内的类访问
    private : 私有，只有定义的类本身可以访问，类内部嵌套的类也可以访问

 */


/*
    static
    1. java没有全局变量的概念，所以使用static静态变量来实现
    2. static修饰的成员可以直接通过 类名.访问，不需要实例化对象
    3. 实例化对象时，类内的static成员不会被拷贝副本，所有对象共享该static成员
    4. private static 只能被类内部的静态方法调用，不能被其他类调用
*/
public class KeyWordLearn {

    public void fun1(){
        PersonStatic ming = new PersonStatic("Xiao Ming", 12);
        PersonStatic hong = new PersonStatic("Xiao Hong", 15);
        ming.number = 88;
        System.out.println(hong.number);    // 88
        hong.number = 99;
        System.out.println(ming.number);    // 99

        PersonStatic.setNumber(100);        // 100
    }

}

/*
    静态变量
        1. 静态变量属于类，实例化对象共享同一个静态变量
        2. 最好使用 类.静态变量 调用，不建议使用 实例.静态变量 调用
    静态方法：
        1. 静态方法不需要借助实例化对象访问，可以直接使用 类.静态方法，常用于工具类
        2. 静态方法属于类，不能使用this字段，只能访问静态变量
 */
class PersonStatic {
    public String name;
    public int age;
    public static int number;

    public PersonStatic(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static void setNumber(int value) {
        number = value;
    }
}



/*
    final
    1. 修饰类
        1.1 final类无法被继承
        1.2 final类的成员变量必须是final类型，成员方法隐式的被声明为final
    2. 修饰方法
        1.1 final方法无法被子类重写
        1.2 private 方法隐式的指定为final
    3. 修饰变量
        1.1 final基本类型变量赋值后不可修改
        1.2 final引用变量赋值后引用地址不可修改，但引用对象可以修改
    4. static 是全局共享一个实例，final只是实例值不可以改变


 */