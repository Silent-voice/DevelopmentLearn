package com.itranswarp.learnjava.servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;


/*
    Servlet
    1. 第三方工具包(如Tomcat)实现了HTTP交互底层的方法，它们也是一种Java程序，叫做Web服务器或者Servlet容器
    2. 我们只需要实现各种Servlet类，去处理不同URL连接的请求
    3. 将我们的代码打包成 .war 文件放入Servlet容器(如Tomcat)的webapps目录下
    4. Servlet容器会去实例化我们定义的Servlet类，然后根据请求情况调用我们定义的处理方法
    5. 服务器会禁止浏览器对 webapp/WEB-INF 内文件的访问


    因此：
    1. 我们定义的Servlet类由Servlet容器去实例化，我们的代码中不能实例化
    2. Servlet容器只会给每个Servlet类创建唯一实例
    3. Servlet容器会使用多线程执行doGet()或doPost()方法


    所以：
    1. Servlet类内的代码要注意线程安全
    2. HttpServletRequest和HttpServletResponse实例是由Servlet容器传入的局部变量，它们只能被当前线程访问，不存在多个线程访问的问题
    3. 如果使用了 Threadlocal 而没有清除，可能会影响后续请求，因为Servlet容器可能使用的是进程池，下次还会有别的请求使用该进程

 */


// WebServlet注解表示这是一个Servlet，并映射到地址 /hello
@WebServlet(urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    /*
        1. 重写 doGet 方法，处理GET请求
        2. 两个参数，一个是请求，一个是相应

     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 设置响应数据类型
        resp.setContentType("text/html");

        Enumeration<String> gns = req.getHeaderNames();

        String name = req.getParameter("name");
        if (name == null) {
            name = "world";
        }

        // 获取输出流
        PrintWriter pw = resp.getWriter();
        // 写入响应内容
        pw.write("<h1>Hello, " + name + "!</h1>");
        // 最后不要忘记flush强制输出
        pw.flush();

    }
}

