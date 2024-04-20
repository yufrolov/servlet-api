<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login page</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/vocabulary/edit" method="post">
    <c:out value="${vocabulary}"/>
    <br>
    <p>Изменение слов</p>
    <input name="word"> Word
    <br>
    <input name="translate_word"> Translate word
    <br>
    <button type="submit">Edit</button>
    <input name="id" type="hidden" value="${vocabulary.id()}">
</form>
<form action="${pageContext.servletContext.contextPath}/vocabulary/list" method="get">
    <button type="submit">Close</button>
    <br>
</form>

</body>
</html>
