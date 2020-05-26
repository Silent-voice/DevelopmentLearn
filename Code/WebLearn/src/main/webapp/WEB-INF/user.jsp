
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.itranswarp.learnjava.jsp.*"%>  <%-- 导入包 --%>
<%
    User user = (User) request.getAttribute("user");    <%-- 实例化User对象--%>
%>
<html>
<head>
    <title>Hello World - JSP</title>
</head>
<body>
    <h1>Hello <%= user.name %>!</h1>    <%-- 调用user对象--%>
    <p>School Name:
        <span style="color:red">
            <%= user.school.name %>
        </span>
    </p>
    <p>School Address:
        <span style="color:red">
            <%= user.school.address %>
        </span>
    </p>
</body>
</html>
