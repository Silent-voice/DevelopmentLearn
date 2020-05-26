package com.itranswarp.learnjava.servlet;

/*
    除了 Servlet 和 Filter 之外，JAVA EE还提供了 Listener 组件用于监听Web服务器的事件
    1. 标注为 @WebListener，会被Web服务器自动初始化、
    2. 监听事件有很多种
        ServletContextListener 监听ServletContext的创建和销毁，每个APP，服务器都会为其创建一个唯一的ServletContext实例
        HttpSessionListener：监听HttpSession的创建和销毁事件；
        ServletRequestListener：监听ServletRequest请求的创建和销毁事件；
        ServletRequestAttributeListener：监听ServletRequest请求的属性变化事件（即调用ServletRequest.setAttribute()方法）；
        ServletContextAttributeListener：监听ServletContext的属性变化事件（即调用ServletContext.setAttribute()方法）；

 */


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ListenerLearn implements ServletContextListener {
    // Web服务初始化事件，可以在这里写一些初始化操作，比如连接数据库之类
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("WebApp initialized.");
    }

    // Web服务关闭事件，可以在这里写一些清理操作，关闭数据库连接等
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("WebApp destroyed.");
    }
}