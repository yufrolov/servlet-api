<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login page</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/login" method="post">
    <input name="login"> login
    <br>
    <input name="password" type="password"> password
    <br>
    <button type="submit">Login</button>
</form>

<form action="${pageContext.servletContext.contextPath}/signup" method="get">
    <button type="submit">SignUp</button>
</form>
</body>
</html>
