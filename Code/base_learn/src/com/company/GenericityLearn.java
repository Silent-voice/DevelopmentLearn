package com.company;

/*
    泛型
    1. 泛型是一种模板，为了能够动态的支持不同类型的数据格式(不包括基本数据类型)
    2. 比如 ArrayList 类，内部是通过一个数组实现的，但是存储不同类型的数据需要不同的类型数值
    3. 泛型使用的类型必须是一个引用类型(Integer,int[])，不能是基本的数据类型(int)

 */

/*
    public class ArrayList<T> {
        private T[] array;
        private int size;
        public void add(T e) {...}
        public void remove(int index) {...}
        public T get(int index) {...}
    }
*/

import java.util.ArrayList;

public class GenericityLearn {

    ArrayList<String> l1 = new ArrayList<String>();
    ArrayList<Integer> l2 = new ArrayList<Integer>();

}

/*
    泛型使用
 */
class Pair<T> {
    private T first;
    private T last;
    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }
    public T getFirst() { return first; }
    public T getLast() { return last; }

    /*
        1. 静态方法不能直接使用泛型，因为静态方法创建时间和类内普通成员不一致，写法比较特殊

        public static Pair<T> create(T first, T last) {
            return new Pair<T>(first, last);
        }
     */
    public static <K> Pair<K> create(K first, K last) {
        return new Pair<K>(first, last);
    }
}

/*
    多泛型的使用， Map(K, V) 就是这样定义的
 */

class Pair2<T, K> {
    private T first;
    private K last;
    public Pair2(T first, K last) {
        this.first = first;
        this.last = last;
    }
    public T getFirst() { return first; }
    public K getLast() { return last; }
}


