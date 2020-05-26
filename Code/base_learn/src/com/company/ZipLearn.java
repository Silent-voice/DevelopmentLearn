package com.company;

/*
    IOStream中ZipInputStream/ZipOutputStream的使用
    1. 基本元素是 ZipEntry，类似于 File和FileInputStream的结合体
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipLearn {

    // 读取压缩文件
    public void fun1() throws IOException {
        // 创建ZipInputStream
        try (ZipInputStream zip = new ZipInputStream(new FileInputStream("test.zip"))) {
            ZipEntry entry = null;
            // 读取压缩包中的文件/文件夹
            while ((entry = zip.getNextEntry()) != null) {
                // 文件/文件夹 名
                String name = entry.getName();
                if (!entry.isDirectory()) {
                    int n;
                    // 读取内容
                    while ((n = zip.read()) != -1) {
                        System.out.println(n);
                    }
                }
            }
        }
    }

    /*
        写压缩文件
        1. putNextEntry() 输入的路径就是文件在压缩文件里的路径
        2. putNextEntry()创建一个ZipEntry，后续操作都是在这个ZipEntry下进行的
     */
    public void fun2() throws IOException {
        // 待写入文件
        File[] files = {new File("test1.txt"), new File("test2.txt"),};

            try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream("test.zip"))) {
            for (File file : files) {
                // 创建一个ZipEntry，后续操作都是在这个ZipEntry下进行的
                zip.putNextEntry(new ZipEntry(file.getName()));

                // 读取原文件数据
                FileInputStream fi = new FileInputStream(file);
                int tmp = 0;
                while ((tmp = fi.read()) != -1) {
                    // 写入ZipEntry
                    zip.write(tmp);
                }

                // 关闭ZipEntry
                zip.closeEntry();
            }
        }


    }


}
