<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<title>Process Registration Confirmation</title>
</head>
<body>
	<h1>Process Registration Confirmation</h1>
	<c:if test="${not empty requestScope.message }">
		<p>${requestScope.message }</p>
	</c:if>
	<br>
	<a href="manageRegistrationRequests">Manage other registration requests</a>
</body>
</html>