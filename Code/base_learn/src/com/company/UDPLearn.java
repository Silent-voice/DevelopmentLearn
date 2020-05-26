package com.company;
/*
    UDP可以和TCP公用一个端口
    1. DatagramSocket   用于发送和接收单一报文
    2. DatagramPacket 对象实例中不仅包含传输报文数据，还包含客户端IP和端口号，所以要想与正确的客户端通信，接收和发送要使用同一个对象实例
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPLearn {

}


class UDPServer{
    public void fun1() throws IOException {
        DatagramSocket ds = new DatagramSocket(6666); // 监听指定端口
        for (;;) { // 无限循环
            // 数据缓冲区:
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            ds.receive(packet);         // 阻塞等待，收取一个UDP数据包，包含数据和客户端IP端口
            // 收取到的数据存储在buffer中，由packet.getOffset(), packet.getLength()指定起始位置和长度
            // 将其按UTF-8编码转换为String:
            String s = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
            // 发送数据:
            byte[] data = "ACK".getBytes(StandardCharsets.UTF_8);
            packet.setData(data);       // packet中包含客户端IP和端口
            ds.send(packet);
        }
    }
}

class UDPClient{
    public void fun1() throws IOException{
        DatagramSocket ds = new DatagramSocket();
        ds.setSoTimeout(1000);      // 等待超时时间1s
        ds.connect(InetAddress.getByName("localhost"), 6666); // 用于指定服务器和端口，并不是真的连接

        // 发送报文
        byte[] data = "Hello".getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length);
        ds.send(packet);

        // 接收报文
        byte[] buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        ds.receive(packet);

        String resp = new String(packet.getData(), packet.getOffset(), packet.getLength());
        ds.disconnect();    // 清楚ds实例中记录的服务器IP和端口信息
    }
}