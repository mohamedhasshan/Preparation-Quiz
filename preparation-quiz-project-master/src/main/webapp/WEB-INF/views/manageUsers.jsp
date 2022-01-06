<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
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
<title>Manage Users</title>
</head>
<body>
	
	<h1 align="left">Manage Students</h1>
	
	<form action="processManageStudentSearch" method="POST">
		<label for="firstNameTextbox">First Name: </label> 
		<input type="text" name="firstName" id="firstNameTextbox"> 
		<label for="lastNameTextbox">Last Name: </label> 
		<input type="text" name="lastName" id="lastNameTextbox">
		<input type="submit" value="Search Student">
	</form>
	<br>
	<hr>
	<c:if test="${not empty requestScope.error }">
		<p>${requestScope.error }</p>
	</c:if>
	
	<table>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Username</th>
			<th>Email</th>
			<th>Status</th>
		</tr>
		
		<c:if test="${not empty requestScope.students }">
			<c:forEach items="${requestScope.students }" var="currentStudent">
				<tr>
					<td>${currentStudent.firstName }</td>
					<td>${currentStudent.lastName }</td>
					<td>${currentStudent.username }</td>
					<td>${currentStudent.email }</td>
					<td>${currentStudent.status }</td>
					<td>
					<form action="editStudent" method="POST">
						<input type="hidden" name=id value=${currentStudent.id }>
						<input type="submit" value="Edit" class="button">
					</form>
					</td>
				<tr>
			</c:forEach>
		</c:if>
	</table>

	<hr>
	<br>
	<a href="manageRegistrationRequests"><button>Manage Registration Requests</button></a>
	<br>
	<br>
	<a href="getHome">Click here to return to the home page</a>
</body>
</html>