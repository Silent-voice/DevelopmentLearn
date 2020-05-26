package com.itranswarp.learnjava.servlet;

/*
    Session 和 cookies
    1. 用户每次访问服务器认证成功后都有一个唯一 session id 标识，session失效后需要重新认证
    2. 第一次调用getSession()会创建一个新的session实例，并将其 JSESSIONID (cookies)字段发送给浏览器
    3. 浏览器之后的请求中也会带有这个JSESSIONID，用于标识是哪个用户

    Coolies 也可以自行添加

 */


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/signin")
public class SignInServlet extends HttpServlet {
    // 模拟一个数据库
    private Map<String, String> users = Map.of("bob", "bob123", "alice", "alice123", "tom", "tomcat");

    // GET请求时显示登录页
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
        pw.write("<h1>Sign In</h1>");
        pw.write("<form action=\"/signin\" method=\"post\">");
        pw.write("<p>Username: <input name=\"username\"></p>");
        pw.write("<p>Password: <input name=\"password\" type=\"password\"></p>");
        pw.write("<p><button type=\"submit\">Sign In</button> <a href=\"/\">Cancel</a></p>");
        pw.write("</form>");
        pw.flush();
    }

    // POST请求时处理用户登录
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("username");
        String password = req.getParameter("password");
        String expectedPassword = users.get(name.toLowerCase());
        if (expectedPassword != null && expectedPassword.equals(password)) {
            // 登录成功
            HttpSession session = req.getSession();     // 应该是创建或者获取一个session
            session.setAttribute("user", name);       // 向session实例里添加一个user字段

            resp.sendRedirect("/index"); // 重定向到初始页面
        } else {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
