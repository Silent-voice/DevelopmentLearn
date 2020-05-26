package com.itranswarp.learnjava;

/*
    为了方便调试我们定义的Servlet代码，可以在IDE中启动Tomcat服务，这样就可以进行断点调试了

    1. 先修改 pom.xml
 */


import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Main {

    public static void main(String[] args) throws Exception {
        // 启动tomcat
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.getInteger("port", 8080));
        // 创建或查找Connector实例，用于接收和响应请求，内部会将该Connector绑定到当前Service上
        tomcat.getConnector();

        // 创建webapp
        // 加载当前工程作为根webapp
        Context ctx = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(
                new DirResourceSet(resources, "/WEB-INF/classes", new File("target/classes").getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}