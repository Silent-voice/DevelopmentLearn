package com.company;
/*
    TCP 通信
    1. ServerSocket(int port, int backlog, InetAddress bindAddr)    port : 端口   backlog : 客户端请求连接队列长度   bindAddr : 监听IP

 */


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPLearn {

}


class TCPServer{
    public void fun1() throws IOException{
        // 创建线程池
        ExecutorService es = Executors.newFixedThreadPool(5);

        ServerSocket ss = new ServerSocket(6666);   // 没有指定IP，监听所有网络接口上的指定端口
        System.out.println("server is running...");
        for (;;) {
            Socket sock = ss.accept();      // 阻塞式监听客户端连接，有一个连接就返回一个Socket对象实例
            System.out.println("connected from " + sock.getRemoteSocketAddress());
            es.submit(new TCPHandler(sock));
        }

    }
}

/*
    服务端处理方法
 */
class TCPHandler implements Runnable {
    Socket sock;

    public TCPHandler(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        // 获取输入输出字节流
        try (InputStream input = this.sock.getInputStream()) {
            try (OutputStream output = this.sock.getOutputStream()) {
                handle(input, output);
            }
        } catch (Exception e) {
            try {
                this.sock.close();      // 关闭本次连接
            } catch (IOException ioe) {
            }
            System.out.println("client disconnected.");
        }
    }

    private void handle(InputStream input, OutputStream output) throws IOException {
        // 将字节流转换成缓冲字符流，便于操作
        var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        writer.write("hello\n");
        writer.flush();
        for (;;) {
            String s = reader.readLine();
            if (s.equals("bye")) {
                writer.write("bye\n");
                writer.flush();
                break;
            }
            writer.write("ok: " + s + "\n");
            writer.flush();
        }
    }
}


/*
    客户端处理方法
 */
class TCPClient{
    public void fun1() throws IOException{
        Socket sock = new Socket("localhost", 6666);        // 连接指定服务器和端口
        // 创建输入输出流
        try (InputStream input = sock.getInputStream()) {
            try (OutputStream output = sock.getOutputStream()) {
                handle(input, output);
            }
        }
        sock.close();
        System.out.println("disconnected.");
    }

    private void handle(InputStream input, OutputStream output) throws IOException {
        var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        // 从控制台读取输入，传给服务器
        Scanner scanner = new Scanner(System.in);
        System.out.println("[server] " + reader.readLine());
        for (;;) {
            System.out.print(">>> "); // 打印提示
            String s = scanner.nextLine(); // 读取一行输入
            writer.write(s);
            writer.newLine();
            writer.flush();
            String resp = reader.readLine();
            System.out.println("<<< " + resp);
            if (resp.equals("bye")) {
                break;
            }
        }
    }
}











