<%--
  Created by IntelliJ IDEA.
  User: yxl
  Date: 2021/8/20
  Time: 下午7:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>success page</title>
</head>
<body>
<h5>login was successful</h5>
<p>
    name：${requestScope.user.username}<br /> password：${requestScope.user.password}<br /> age：${requestScope.user.age}<br />
</p>
</body>
</html>
