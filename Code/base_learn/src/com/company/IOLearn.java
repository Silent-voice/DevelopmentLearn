package com.company;

/*

    InputStream / OutputStream
    1. 字节流
    2. 数据来源子类：FileInputStream、ServletInputStream、Socket.getInputStream ...
    3. 功能子类：FilterInputStream，包含多种封装好的功能子类：BufferedInputStream、DataInputStream、CheckedInputStream


    Reader / Writer
    1. 字符流，读和写操作的还是字节，不过会自动对字节进行编码，转换成字符，借助InputStream实现

    上述都是抽象类
 */


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

public class IOLearn {

    /*
        File 对象
        1. File对象实例化时不会真正访问磁盘，只有进行操作时才会访问，所以即使传入不存在的路径也不会报错
     */

    public void fun1() throws IOException {
        File f1 = new File("C:\\Windows\\test.txt");
        f1.createNewFile();
        /*
            方法
            getPath()   getAbsolutePath()   getCanonicalPath()
            boolean : isFile()    isDirectory()
            boolean : canRead()   canWrite()  canExecute()    判断权限
            long : length()
            boolean : createNewFile()     delete() 不能删除非空目录
            boolean : mkdir()       mkdirs() 父目录不存在也会创建
            File[] :  listFiles()
         */

        Path p1 = Paths.get(".", "project", "study"); // 构造一个Path对象
        Path p2 = p1.toAbsolutePath(); // 转换为绝对路径
        Path p3 = p2.normalize(); // 转换为规范路径

        File f2 = p3.toFile(); // 转换为File对象

    }


    /*
        InputStream  抽象类，需要使用其子类
        1.  read() 读取一个字节的数据，返回一个整数0~255，-1代表已读到末尾
        2. InputStream的子类还有很多
     */

    public void fun2() throws IOException{
        FileInputStream input = new FileInputStream("src/readme.txt");
        for (;;) {
            int n = input.read();   // 反复调用read()方法，直到返回-1
            if (n == -1) {
                break;
            }
            System.out.println(n); // 打印byte的值
        }
        input.close();      // 关闭 InputStream

        byte[] data = { 72, 101, 108, 108, 111, 33 };
        try (InputStream input2 = new ByteArrayInputStream(data)) {
            int n;
            while ((n = input2.read()) != -1) {
                System.out.println((char)n);
            }
        }

    }

    /*
        缓冲
        1. 一次读1个字节太慢，很多InputStream都支持缓冲，一次读多个字节
        2. 返回值是本次读取的字节数
     */
    public void fun3() throws IOException{
        try (FileInputStream input = new FileInputStream("src/readme.txt")) {       // 这种写法会自动close
            // 定义1000个字节大小的缓冲区:
            byte[] buffer = new byte[1000];
            int n;
            while ((n = input.read(buffer)) != -1) { // 读取到缓冲区
                System.out.println("read " + n + " bytes.");
            }
        }

    }

     /*
        OutputStream  抽象类，需要使用其子类
        1. write(int b) 写一个字节的数据
        2. FileOutputStream类重写了write方法 write(byte[])
        3. OutputStream有缓冲区，一般缓冲区满了才真正输出，可以用flush()强制输出，close()前会自动调用flush()

     */

    public void fun4() throws IOException{
        OutputStream output = new FileOutputStream("out/readme.txt");

        output.write(72);   // 写一个字节
        output.write("Hello".getBytes("UTF-8"));    // 写 byte[]

        output.flush();     //  将缓冲区的数据写入文件
        output.close();
    }

    /*
        Filter模式（或者装饰器模式：Decorator）
        1. 在确定数据来源的基础上，叠加各种附加功能组件(缓冲、加密、签名等)的方式
        2. 数据来源子类：FileInputStream、ServletInputStream、Socket.getInputStream 。。。
        3. 功能子类，继承于FilterInputStream类，可以使用java自带的，也可以自己定义

     */
    public void fun5() throws IOException{
        // 创建数据来源InputStream
        InputStream file = new FileInputStream("test.gz");
        // 缓冲功能组件，参数是 InputStream 实例
        InputStream buffered = new BufferedInputStream(file);
        // 解压缩功能组件
        InputStream gzip = new GZIPInputStream(buffered);

        gzip.close();   // 会自动调用内层InputStream的close()方法

    }

    // 自定义功能组件的使用
    public void fun6() throws IOException{
        byte[] data = "hello, world!".getBytes("UTF-8");
        try (CountInputStream input = new CountInputStream(new ByteArrayInputStream(data))) {
            int n;
            while ((n = input.read()) != -1) {
                System.out.println((char)n);
            }
            System.out.println("Total read " + input.getBytesRead() + " bytes");
        }
    }

    /*
        FileReader
        1.  创建时可以指定编码，否则以系统默认编码读取字符
        2. read() 每次读取一个字符，字符大小一般根据编码而且，返回值是整数，-1代表没有字符了
     */

    public void fun7() throws IOException{
        try (Reader reader = new FileReader("src/readme.txt", StandardCharsets.UTF_8)) {
            for (;;) {
                int n = reader.read(); // 反复调用read()方法，直到返回-1
                if (n == -1) {
                    break;
                }
                System.out.println((char)n); // 打印char
            }
        }

        try (Reader reader = new FileReader("src/readme.txt", StandardCharsets.UTF_8)) {
            char[] buffer = new char[1000];
            int n;
            while ((n = reader.read(buffer)) != -1) {
                System.out.println("read " + n + " chars.");
            }
        }

        // InputStreamReader 将一个 InputStream 实例转换成 Reader实例
        InputStream input = new FileInputStream("src/readme.txt");
        Reader reader = new InputStreamReader(input, "UTF-8");

        // 每次读取一行
        // 字节流FileInputStream -> 字符流InputStreamReader -> 加上缓冲功能BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/readme.txt"), "UTF-8"));

        String s = null;
        //使用readLine方法，一次读一行，每行末尾的换行符自动去除，没有返回null
        while((s = br.readLine()) != null){
            System.out.print(s + '\n');
        }
        br.close();


    }



}

// 自定义InputStream功能组件，统计已读取的字节数
class CountInputStream extends FilterInputStream {
    private int count = 0;
    CountInputStream(InputStream in) {
        super(in);
    }
    public int getBytesRead() {
        return this.count;
    }
    public int read() throws IOException {
        int n = in.read();
        if (n != -1) {
            this.count ++;
        }
        return n;
    }
    public int read(byte[] b, int off, int len) throws IOException {
        int n = in.read(b, off, len);
        this.count += n;
        return n;
    }
}