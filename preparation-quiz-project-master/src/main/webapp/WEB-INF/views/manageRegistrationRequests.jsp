<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
table {
  width: 50%;
}

td, th {
  border-bottom : 1px solid #dddddd;
  text-align: center;
  padding: 8px;
}

td:nth-child(even), th:nth-child(even) {
  background-color: #D6EEEE;
}
</style>
<title>Manage User Registration</title>
</head>
<body>
	<h1>Manage Registration Requests</h1>
	<hr>
	<c:if test="${not empty requestScope.error }">
		<p>${requestScope.error }</p>
	</c:if>
	
	<table>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Email</th>
			<th>Role</th>
		</tr>
		
		<c:if test="${not empty requestScope.registrationRequests }">
			<c:forEach items="${requestScope.registrationRequests }" var="currentRequest">
				<tr>
					<td>${currentRequest.firstName }</td>
					<td>${currentRequest.lastName }</td>
					<td>${currentRequest.email }</td>
					<td>${currentRequest.role }</td>
					<td>
					<form action="processApproveRegistration" method="POST">
						<input type="hidden" name=id value=${currentRequest.id }>
						<input type="submit" value="Approve" class="button">
					</form>
					</td>
					<td>
					<form action="processDenyRegistration" method="POST">
						<input type="hidden" name=id value=${currentRequest.id }>
						<input type="submit" value="Deny" class="button">
					</form>
					</td>
				<tr>
			</c:forEach>
		</c:if>
	</table>
	
	<hr>
	<br>
	<a href="manageUsers"><button>Manage Students</button></a>
	<br>
	<br>
	<a href="getHome">Click here to return to the home page</a>
</body>
</html>