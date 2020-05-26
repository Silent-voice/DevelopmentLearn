package com.company;

/*
    数组
*/

import java.util.*;

public class Array {

    // 普通一维数组
    public void fun1(){
        int[] a1 = new int[10];     // 定义时要指定大小，有默认值，定义之后大小不可变，引用型变量
        a1[0] = 1;
        a1[1] = 2;
        System.out.println(a1.length);

        int[] a2 = new int[] {1, 2, 3};
        int[] a3 = {1, 2, 3};
        a3 = new int[] {1, 2, 3, 4, 5};     // 重新指向另一个新数组

        String[] names = {"ABC", "XYZ", "zoo"};     // 引用的引用，names 指向三个字符串的引用
        String s = names[1];    // s指向 "XYZ"
        names[1] = "cat";   // names[1] 重新指向 "cat"
        System.out.println(s);     //s不变


        //数组排序
        Arrays.sort(a3);

    }

    // 二维数组，引用的引用
    public void fun2(){
        // 每个子数组不需要统一维度
        int [] A[] = {{1,2}, {2,3,4}, {3,4,5,6}};

        int [][] B = new int[3][];
        B[0] = new int[3];  //3
        B[1] = new int[]{1,2,3,4};  //3
        B[2] = new int[2];  //2

        int [] C[] = new int[3][4];
    }

    // 动态数组

    /*
    ArrayList
    1. 非线程安全
    2. 使用数组实现，容量不够时自动扩容

    */
    public void fun3(){
        // 创建ArrayList
        ArrayList<String> list1 = new ArrayList<String>();  //指定数据类型
        ArrayList<String> list2 = new ArrayList<String>(100);   //指定数组初始大小
        ArrayList list3 = new ArrayList();  // 也可以不知道数据类型，默认是Object类型

        // 增删改查
        list1.add("1");
        list1.add("2");
        list1.add(0, "5");

        String s1 = list1.get(0);
        list1.remove("2");      //按值删除，删除第一个相同的值
        list1.remove(0);

        list1.set(1, "10");
        boolean b1 = list1.contains(3);

        // 遍历
        for (Iterator iter = list1.iterator(); iter.hasNext(); ) {
            System.out.println("next is: "+ iter.next());
        }

        // 随机访问速度较快
        String value = null;
        for (int i = 0; i < list1.size(); i++) {
            value = list1.get(i);
        }

        // 大小
        int len = list1.size();
        // 清空
        list1.clear();
        boolean b2 = list1.isEmpty();

        // 将ArrayList转换为数组
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);

        Integer i = list.get(0);

        /*
            1. 必须使用Integer类型
            2. toArray()的所有构造方法都会返回转换后的数组
          */
        Integer [] a1 = (Integer [])list.toArray();     // a1 = [1, 2, 3]

        // a2.length == list.size   => a2 = a3 = [1,2,3]
        Integer [] a2 = new Integer[3];
        Integer [] a3 = list.toArray(a2);

        // a4.length < list.size   => a4 = [null, null],  a5 = [1,2,3]
        Integer [] a4 = new Integer[2];
        Integer [] a5 = list.toArray(a4);


        // a6.length > list.size   => a6 = a7 = [1,2,3, null,null]
        Integer [] a6 = new Integer[5];
        Integer [] a7 = list.toArray(a6);


    }

    /*
    Vector
    1. 和ArrayList类似
    2. 线程安全，支持线程同步，同一时刻只有一个线程可以写
    3. 因为支持线程安全，开销较大，速度较慢

    */
    public void fun4(){
        Vector<Integer> v1 = new Vector<Integer>();     // 指定数据类型
        Vector<Integer> v2 = new Vector<Integer>(10);   //指定初始大小
        Vector<Integer> v3 = new Vector<Integer>(10, 5);    //指定增量大小
        Vector v4 = new Vector();   //不指定类型

        //

    }

    /*
    LinkedList
    1. 链表结构的动态数组，增删较快，改较慢


    Queue
    1. 队列，先入先出
    2. Queue是一个接口，具体的实现有LinkedList等
    */
    public void fun5(){
        LinkedList<Integer> l = new LinkedList<>();
        Queue<Integer> q = new LinkedList<>();



        // 增
        l.add(1);
        l.add(1,1);
        l.offer(1);

        // 删
        l.poll();   // 删除并返回第一个
        l.pop();
        l.removeFirst();
        l.removeLast();


        l.getFirst();

        l.size();
        l.isEmpty();

    }


    /*
    HashMap
    1. 数组+链表(解决hash碰撞的一种方法)
    2. 基本元素是Entry，一个内部类，包含key/value/hash/next这几个属性，next指向相同hash值的下个Entry
    3. key -> 计算hash值 f(key) -> 在Entry数组中找到对应的初始Entry -> 对比key和value找到或创建最终的Entry
    4. 相同hash扩链表，不同hash扩数组
    */
    public void fun6(){
        // key为String类型，value为Integer类型，不准使用基本类型int等
        HashMap<String, Integer> map = new HashMap<String, Integer> ();

        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        Integer i1 = map.get("b");
        Integer i2 = map.remove("a");
        map.replace("b", 5);

        map.containsKey("a");
        map.isEmpty();
        map.size();
        map.clear();

        // 获取所有key
        Set s = map.keySet();
        map.values();


    }


    /*
    Set
    1. 不重复元素存储，无序
    2. Set是一个接口，不是类，创建实例化对象得用HashSet或者TreeSet
    3. HashSet借助HashMap实现，key就是set里的值，value统一赋值

     */
    public void fun7(){
        Set<String> hashSet = new HashSet<>();

        //元素添加：
        hashSet.add("my");
        hashSet.add("name");
        hashSet.add("is");
        hashSet.add("jiaboyan");
        hashSet.add(",");
        hashSet.add("hello");
        hashSet.add("world");
        hashSet.add("!");

        //元素删除：
        hashSet.remove("jiaboyan");
        hashSet.clear();

        //集合判断：
        int l = hashSet.size();
        boolean isEmpty = hashSet.isEmpty();
        boolean isContains = hashSet.contains("hello");


        //迭代器遍历：
        Iterator<String> iterator = hashSet.iterator();
        while (iterator.hasNext()){
            String str = iterator.next();
            System.out.println(str);
        }
        //增强for循环
        for(String str:hashSet){
            if("jiaboyan".equals(str)){
                System.out.println("你就是我想要的元素:"+str);
            }
            System.out.println(str);
        }
    }


    /*
    线程安全集合
    1. 大部分集合Collections都是非线程安全的，因此Collections类提供了线程安全的包装方法将各种集合转换成线程安全集合
    2. 使用synchronized锁住增删改查这些操作

     */
    public void fun8(){
        List<Integer> synArrayList = Collections.synchronizedList(new ArrayList<Integer>());

        Set<Integer> synHashSet = Collections.synchronizedSet(new HashSet<Integer>());

        Map<Integer,String> synHashMap = Collections.synchronizedMap(new HashMap<Integer,String>());

    }

}
