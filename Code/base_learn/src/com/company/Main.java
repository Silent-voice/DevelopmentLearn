package com.company;


import java.io.IOException;
import java.util.Scanner;

/*
    1. java方法参数传递是值传递，无论基本变量还是引用，引用传的也是地址的拷贝

 */

public class Main {

    public static void main(String[] args) throws Exception {
        int[] nums = new int[]{2,2,2,1,2,2,1,2,2,2};
        int k = 2;

        Solution s = new Solution();
        System.out.println(s.numberOfSubarrays(nums, k));


    }
}
