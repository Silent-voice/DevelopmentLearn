package com.itranswarp.learnjava.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/*
    / 是默认转发，会接收所有未匹配的路径
 */

/*
    HttpServletRequest
        getMethod()：返回请求方法，例如，"GET"，"POST"；
        getRequestURI()：返回请求路径，但不包括请求参数，例如，"/hello"；
        getQueryString()：返回请求参数，例如，"name=Bob&a=1&b=2"；
        getParameter(name)：返回请求参数，GET请求从URL读取参数，POST请求从Body中读取参数；
        getContentType()：获取请求Body的类型，例如，"application/x-www-form-urlencoded"；
        getContextPath()：获取当前Webapp挂载的路径，对于ROOT来说，总是返回空字符串""；
        getCookies()：返回请求携带的所有Cookie；
        getHeader(name)：获取指定的Header，对Header名称不区分大小写；
        getHeaderNames()：返回所有Header名称；
        getInputStream()：如果该请求带有HTTP Body，该方法将打开一个输入流用于读取Body；
        getReader()：和getInputStream()类似，但打开的是Reader；
        getRemoteAddr()：返回客户端的IP地址；
        getScheme()：返回协议类型，例如，"http"，"https"；

    HttpServletResponse     先设置header字段内容，再设置content字段内容
    设置header字段
        setStatus(sc)：设置响应代码，默认是200；
        setContentType(type)：设置Body的类型，例如，"text/html"；
        setCharacterEncoding(charset)：设置字符编码，例如，"UTF-8"；
        setHeader(name, value)：设置一个Header的值；
        addCookie(cookie)：给响应添加一个Cookie；
        addHeader(name, value)：给响应添加一个Header，因为HTTP协议允许有多个相同的Header；
    设置content字段
        1. 先获取输出流字节流或字符流 getOutputStream() 或 getWriter()
        2. 往流中写入content内容
        3. 调用flush()
        4. 不要调用close()关闭输出流，这会将TCP连接断开

 */


@WebServlet(urlPatterns = "/")
public class IndexServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 从HttpSession获取当前用户名
        String user = (String) req.getSession().getAttribute("user");

        // 设置头部字段
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("X-Powered-By", "JavaEE Servlet");

        // 设置内容字段
        PrintWriter pw = resp.getWriter();
        pw.write("<h1>Welcome, " + (user != null ? user : "Guest") + "</h1>");
        if (user == null) {
            // 未登录，显示登录链接:
            pw.write("<p><a href=\"/signin\">Sign In</a></p>");
        } else {
            // 已登录，显示登出链接:
            pw.write("<p><a href=\"/signout\">Sign Out</a></p>");
        }
        pw.flush();

    }

    // 处理 cookie
    private String parseLanguageFromCookie(HttpServletRequest req) {
        // 获取请求附带的所有Cookie
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lang")) {
                    return cookie.getValue();
                }
            }
        }
        // 返回默认值:
        return "en";
    }
}
