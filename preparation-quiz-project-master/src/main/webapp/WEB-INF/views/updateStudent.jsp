<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<style>
body {
    text-align: center;
}
</style>
<title>Update Student</title>
</head>
<body>
	<h1>Update Student</h1>
	
	<form action="processUserUpdate" method="POST">

		<c:choose>
			<c:when test="${sessionScope.currentUser.role == 'Trainer'}">
				<label for="firstNameInput">First Name: </label>
				<input type="text" required="required" name="firstName" id="firstNameInput" value="${requestScope.student.firstName}"/><br><br>
		
				<label for="lastNameInput">Last Name: </label>
				<input type="text" required="required" name="lastName" id="lastNameInput" value="${requestScope.student.lastName}"/><br><br>
		
				<label for="usernameInput">Username: </label>
				<input type="text" required="required" name="username" id="usernameInput" value="${requestScope.student.username}"/><br><br>
		
				<label for="passwordInput">Password: </label>
				<input type="password" required="required" name="password" id="passwordInput" value="${requestScope.student.password}"/><br><br>
		
				<label for="emailInput">Email: </label>
				<input type="text" required="required" name="email" id="emailInput" value="${requestScope.student.email}"/><br><br>
				
				<label for="statusSelect">Status: </label>
				<select name = "status" id="statusSelect">
					<c:forEach items="${requestScope.allStatus }" var="currentStatus">
						<c:choose>
							<c:when test="${currentStatus == requestScope.student.status}">
								<option value=${currentStatus } selected="selected">${currentStatus }</option>
							</c:when>
							<c:otherwise>
								<option value=${currentStatus }>${currentStatus }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</c:when>
				
			<c:otherwise>
				<label for="firstNameInput">First Name: </label>
				<input type="text" required="required" name="firstName" id="firstNameInput" value="${requestScope.student.firstName}" readonly="readonly"/><br><br>
		
				<label for="lastNameInput">Last Name: </label>
				<input type="text" required="required" name="lastName" id="lastNameInput" value="${requestScope.student.lastName}" readonly="readonly"/><br><br>
		
				<label for="usernameInput">Username: </label>
				<input type="text" required="required" name="username" id="usernameInput" value="${requestScope.student.username} " readonly="readonly"/><br><br>
		
				<label for="passwordInput">Password: </label>
				<input type="password" required="required" name="password" id="passwordInput" value="${requestScope.student.password}" readonly="readonly"/><br><br>
		
				<label for="emailInput">Email: </label>
				<input type="text" required="required" name="email" id="emailInput" value="${requestScope.student.email}" readonly="readonly"/><br><br>
				
				<label for="statusSelect">Status: </label>
				<select name = "status" id="statusSelect">
					<c:forEach items="${requestScope.salesStatusOptions }" var="currentStatus">
						<c:choose>
							<c:when test="${currentStatus == requestScope.student.status}">
								<option value=${currentStatus } selected="selected">${currentStatus }</option>
							</c:when>
							<c:otherwise>
								<option value=${currentStatus }>${currentStatus }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</c:otherwise>
		</c:choose>
		<br><br>
		<c:if test="${not empty requestScope.error }">
			<p style="color: red; font-size: 15px; text-align: center;">${requestScope.error }</p>
		</c:if>
		
		<c:if test="${not empty requestScope.message }">
			<p style="font-size: 15px; text-align: center;">${requestScope.message }</p>
		</c:if>
		<input type="hidden" name=id value=${requestScope.student.id }>
		<input type="submit" value="Save" class="button">
	</form>
	
	<br>
	<a href="manageUsers"><button class="button">Cancel</button></a>
	
</body>
</html>