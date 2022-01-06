<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<meta charset="ISO-8859-1">
<title>View Student Submissions</title>
</head>
<body>
	
	<h1>View Student Submissions</h1>
	
	<form action="processStudentSubmissionSearch" method="POST">
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
					<form action="getStudentSubmissions" method="POST">
						<input type="hidden" name=id value=${currentStudent.id }>
						<input type="submit" value="View Submissions" class="button">
					</form>
					</td>
				<tr>
			</c:forEach>
		</c:if>
	</table>
	
	<hr>
	<br>
	<a href="getHome">Click here to return to the home page</a>
	
</body>
</html>