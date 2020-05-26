package com.itranswarp.learnjava.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    重定向分两种 ：302临时重定向 和 301 永久重定向
    302 ：
        1. 浏览器访问 hi + 参数
        2. 服务端重新构造URL hello + 参数，返回给浏览器302和新的URL
        3. 浏览器访问新的URL

    301 ：
        1. 浏览器进行缓存，将 hi 永久重定向到 hello，参数怎么传递？


    重定向本质上是两次不同访问，不适合在两次访问中传递参数
 */


@WebServlet(urlPatterns = "/hi")
public class HiServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 构造重定向的路径，将第一次请求的参数传回去
        String name = req.getParameter("name");
        String redirectToUrl = "/hello" + (name == null ? "" : "?name=" + name);

        // 发送临时重定向响应
        resp.sendRedirect(redirectToUrl);


        // 301 永久重定向
        resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY); // 301
        resp.setHeader("Location", "/hello");

    }
}
