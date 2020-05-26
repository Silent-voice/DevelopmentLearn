package com.itranswarp.learnjava.servlet;

/*
    内部转发 forward
    1. 本质上是调用其他servlet实例进行处理，是在服务器内部进行的
    2. 因为将HttpServletRequest和HttpServletResponse实例都传递了过去，调用的servlet实例可以获取本次请求的所有参数
 */


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/morning")
public class ForwardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/hello").forward(req, resp);
    }
}
