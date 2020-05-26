package com.itranswarp.learnjava.servlet;

/*
    在用户请求到达不同的Servlet之前，可以对其进行统一的预处理操作Filter
    1. 用户请求 -> Web Server -> Filter 1 -> Filter 2 -> ... -> 相应的Servlet

 */

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")      // Filter需要预处理的URL
public class FilterLearn implements Filter {

    // 重写doFilter方法
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("EncodingFilter:doFilter");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 必须调用doFilter()方法，不然上述操作不会生效，数据也无法继续传递给后面的Filter和Servlet
        chain.doFilter(request, response);
    }
}
