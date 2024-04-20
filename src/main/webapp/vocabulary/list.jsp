<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login page</title>
</head>
<body>
<c:forEach items="${vocabularies}" var="vocabulary">
    <form action="${pageContext.servletContext.contextPath}/vocabulary/edit" method="get">
        <c:out value="${vocabulary}"/>
        <br>
        <button type="submit">Edit</button>
        <input name="id" type="hidden" value="${vocabulary.id()}">
    </form>
    <form action="${pageContext.servletContext.contextPath}/vocabulary/delete" method="post">
        <button type="submit">Delete</button>
        <input name="id" type="hidden" value="${vocabulary.id()}">
        <p></p>
    </form>

</c:forEach>

<form action="${pageContext.servletContext.contextPath}/vocabulary/list" method="post">
    <input name="word"> Word
    <br>
    <input name="translate_word"> Translate word
    <br>
    <button type="submit">Add words</button>
    <br>
    <input name="id" type=hidden value="${sessionScope.profile.id()}">
</form>

</body>
</html>
