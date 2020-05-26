<%--
    JSP
    1. 通过HttpServletResponse返回HTML页面比较麻烦，可以返回一个 .jsp 的文件
    2. .jsp 文件类似于HTML，不过可以插入部分JAVA代码
    3.
        包含在<%和%>之间的是Java代码，可以编写任意Java代码；
        如果使用<%= xxx %>则可以快捷输出一个变量的值。
    4. .jsp 中内置了几个变量
        out：表示HttpServletResponse的PrintWriter
        session：表示当前HttpSession对象
        request：表示HttpServletRequest对象
    5. 浏览器访问时也是直接访问该jsp文件，启动tomcat时会自动生成该jsp文件对应的Servlet实例

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello World - JSP</title>
</head>
<body>
    <%-- 注释 --%>
    <h1>Hello World!</h1>
    <p>
        <% out.println("Your IP address is "); %>
        <span style="color:red">
            <%= request.getRemoteAddr() %>
        </span>
    </p>

    <p>
        Your IP address is <span style="color:red">10.0.0.127</span>
    </p>

</body>
</html>
