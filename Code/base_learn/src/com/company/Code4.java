package com.company;

/*
            流程控制
 */

import java.util.Scanner;

public class Code4 {

    // 输入输出
    public void fun1(){
        // 输出
        System.out.print("A,");
        System.out.println();
        System.out.println("END");

        double d = 3.1415926;
        // %d整数、%f浮点数、%s字符串
        System.out.printf("%.4f\n", d); // 显示4位小数3.1416

        // 输入
        Scanner scanner = new Scanner(System.in); // 创建Scanner对象，传入标准输入System.in
        String name = scanner.nextLine();       //读取一行输入，返回字符串，最后没有换行符
        int age = scanner.nextInt();            // 读取一个整数，以空格分隔
        System.out.printf("Hi, %s, you are %d\n", name, age); // 格式化输出


    }


    // if for while
    public void fun2(int n, int option){
        // if
        if (n >= 90) {
            System.out.println("优秀");
        } else if (n >= 60) {
            System.out.println("及格了");
        } else {
            System.out.println("挂科了");
        }

        int [] list = new int[] {1,2,3,4,5};

        // for
        for (int i = 0; i < list.length; i ++){
            System.out.println(list[i]);
        }
        // for each
        for (int e : list){
            System.out.println(e);
        }

        // while
        int sum = 0;
        while (n <= 100) {
            sum = sum + n;
            n ++;
        }

        do {
            sum = sum + n;
            n ++;
        } while (n <= 100);

        // switch
        switch (option) {
            case 1:
                System.out.println("Selected 1");
                break;
            case 2:
                System.out.println("Selected 2");
                break;
            case 3:
                System.out.println("Selected 3");
                break;
            default:
                System.out.println("Not selected");
                break;
        }

    }

}
