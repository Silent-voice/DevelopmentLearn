package com.company;

/*
    基础变量与数据类型
*/

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Code2 {
    byte x1 = 12;   // 1byte -128~127
    short x2 = 1;   // 2 byte
    int x3 = 100;   // 4 byte
    long x4 = 100;  // 8 byte

    /*
    1. 很多浮点数(如0.1)无法精确表示，计算机中存储的都是近似值
    2. 所以浮点数运算经常会有误差
     */
    float f1 = 1.1f;    // 4 byte
    double d1 = 2.2;    // 8 byte

    double d2 = 1 - 9.0 / 10;   // d2 != 0.1

    boolean b1 = true;  // 4 byte       关系运算符：&&  ||  !


    char c1 = 'a';      // 2 byte, 单引号标识，Unicode编码
    char c2 = 97;       // 'a'
    char c3 = 'a' + 1;  // 'b'
    char c4 = '中';

    int i1 = 'A';       // 65
    char c5 = '\u0041';     // 16进制，等同于65 'A'


    /*
        String
        1. 是一个类，内部以 char [] 实现，所以字符串定义之后不可改变
        2. 字符串改变的操作是在内存中重新申请一个 String ，然后当前对象指向新的String
        3. 常用功能函数：contains(), indexOf(), substring(), trim()/strip(), replace(), split(), join(),
     */
    String s1 = "abc";      // 引用型变量，类似指针，指向字符串 "abc"
    String s2 = "asd\"saf";     // \ 转义符
    String s3 = "\u0041BC";  // 也可以用Unicode字符
    String s4 = null;



    // 常量标识符 final
    final double PI = 3.14;
    // final只是声明引用变量myList不可改变，即引用地址不可改变，但引用指向的具体对象还是可以改变的
    final List<String> myList = new ArrayList<>();


    // 变量生命周期：定义~{}结束
    public void fun1(){
        myList.add("aaa");

        int i = 1;
        for(int j = 0; j < 100; j++){
           int  y = 1;
           y += 1;
           System.out.println(y);
        }
    }

    //变量类型转换
    public void fun2(){
        //自动(隐式)类型转换    低字节 自动转 高字节
        int a = 3;
        double b = a + 1.1;

        //强制(显示)类型转换    会损失精度
        int c = (int)b;
    }

    // 运算
    public void fun3(){
        int a = 1231;
        int b = 21;
        int c = a / b;  // 自动取整

        int d = 2147483640;
        int e = d + a;  // 溢出，不会报错

    }

    /*
        == 和 equal
        == : 引用是否相等，比较具体的值
        equal : 各个数据类型都进行了重写，用于比较存储的内容
    */

    public void fun4(){
        String str1 = "hello";  // 创建一个String对象，str1指向该对象，类似于一个指针
        String str2 = "hello";

        // false
        System.out.println(str1 == str2);
        // true
        System.out.println(str1.equals(str2));

    }

}

/*
    Integer(包装类) 与 int(基本数据类型)的区别
    1. int是一个基本数据类型，Integer是一个类，是对int的封装
    2. int可以直接使用，Integer必须得实例化对象(new)才能使用
    3. Integer实例化对象是一个引用， int存储的是整数值
    4. Integer类是不变类，类和类内成员都是final，实例化之后不可改变

    包装类共有方法：
        转换为byte：byteValue()
        转换为short：shortValue()
        转换为int：intValue()
        转换为long：longValue()
        转换为float：floatValue()
        转换为double：doubleValue()

*/

class Code2_2 {
    Integer i1;     // i1 = null
    int i2;         // i2 = 0


    /*
        String 与其他类型之间的相互转换
     */

    public void fun1(){
        // 转 String
        String.valueOf(123); // "123"
        String.valueOf(45.67); // "45.67"
        String.valueOf(true); // "true"
        String.valueOf(new Object());

        // 转int
        int n1 = Integer.parseInt("123"); // 123
        int n2 = Integer.parseInt("ff", 16); // 按十六进制转换，255

        boolean b1 = Boolean.parseBoolean("true"); // true
        boolean b2 = Boolean.parseBoolean("FALSE"); // false

        // 转 char[]
        char[] cs = "Hello".toCharArray(); // String -> char[]
        String s = new String(cs);      // 创建时String内部char[]会复制一份

        // 转String[]
        String s2 = "abc";
        String[] s_list = s2.split(""); // ["a", "b", "c"]

    }

    /*
        StringBuilder
        1. String 每次修改都得重写创建新的对象，浪费内存和时间，因此有了StringBuilder，可变对象，可以预分配空间，不用重复创建
        2. 内部通过byte []实现
        3. 非线程安全，但速度较快

        StringBuffer
        1. 与StringBuilder类似，线程安全，速度较慢，一般不使用


     */

    public void fun2(){
        StringBuilder sb = new StringBuilder(1024);   // 初始大小
        for (int i = 0; i < 1000; i++) {
            sb.append(',');
            sb.append(i);   // 自动转换类型
        }
        String s = sb.toString();

    }

    /*
        var 关键字
        1. 根据等式右半部分自动推断类型
     */
    public void fun3(){
        var s = new String("asds");
        var nums = new int[] {1,2,3};

        for(var n : nums){
            System.out.println(n);
        }

        // var foo; foo = 1;    不能分开写

    }

    /*
        BigInteger
        1. 超大整数类，没有整数范围限制，使用 int[] 实现，速度较慢

        BigDecimal
        1. 超大浮点数
        2. 使用compareTo()进行比较，不能使用equals()

     */
    public void fun4(){
        BigInteger i1 = new BigInteger("1234567890");
        BigInteger i2 = new BigInteger("12345678901234567890");
        BigInteger sum = i1.add(i2);

        System.out.println(i1.longValue());



    }


}


/*
    枚举 enum
    1. enum 定义的是一个类， 类内属性都是public static，所以可以直接访问，构造方法是private，所以不允许使用new创建实例

 */
enum Weekday {
    SUN, MON, TUE, WED, THU, FRI, SAT;
}

class Code2_3{
    Weekday x1 = Weekday.SUN;
    Weekday x2 = Weekday.FRI;

    // enum虽然是个引用，但也可以直接使用 == 比较，因为enum的所有取值都在内存中拥有一个固定的位置，所有引用都指向这个相同的内存地址
    public void fun1(){
        if (x1 == Weekday.SUN){
            String s = Weekday.SUN.name();  // 常量名 “SUN”
            int n = Weekday.MON.ordinal();  // 常量在ebum中的顺序 1
        }
    }
}