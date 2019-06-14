<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SSO Server login</title>
</head>
<body>
<form method="post" action="login" enctype="application/x-www-form-urlencoded">

<input type="hidden" name="origUrl" value="${origUrl}" />

<c:if test="${not empty errInfo}">
<p style="color:red;">${errInfo}</p>
</c:if>

<p><input type="text" name="account" value="${account}"/></p>
<p><input type="password" name="passwd" /></p>
<p><input type="submit" value="login" /></p>
</form>
</body>
</html>