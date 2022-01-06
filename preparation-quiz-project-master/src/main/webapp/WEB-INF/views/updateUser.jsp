<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.4/css/fontawesome.min.css">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<title></title>
</head>
<body>
	<nav>
		<a href="getHome"><h1>Quiz System</h1> <img
			src="resources/img/logo.png" height="120" width="250"></a>
		<c:choose>
			<c:when test="${not empty sessionScope.currentUser }">
				<c:choose>
					<c:when test="${sessionScope.currentUser.role == 'Student' }">
						<div class="nav-links" id="navLinks">
							<ul>
								<li><a href="getHome">Home</a></li>
								<li><a href="viewQuizzes">View Quizzes </a>
								<li><a href="getMySubmissions">View My Submissions </a>
								<li><a href="createQuiz">Create Quiz </a></li>
								<li><a href="createQuestion">Create Question</a>
								<li><a href="manageQuizzes">Manage My Quizzes </a>
								<li><a href="userUpdate">Update My Profile</a></li>
								<li><a href="logout">Log out</a></li>
							</ul>
						</div>
					</c:when>
					<c:otherwise>
						<div class="nav-links" id="navLinks">
							<ul>
								<li><a href="getHome">Home</a></li>
								<li><a href="viewStudentSubmissions">View Student Submissions </a>
								<li><a href="createQuiz">Create Quiz </a></li>
								<li><a href="createQuestion">Create Question</a>
								<li><a href="manageQuizzes">Manage Quizzes </a>
								<li><a href="manageUsers">Manage Users</a></li>
								<li><a href="userUpdate">Update My Profile</a></li>
								<li><a href="logout">Log out</a></li>
							</ul>
						</div>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<div class="nav-links" id="navLinks">
					<i class="fa fa-times" onclick="hideMenu()"></i>
					<ul>
						<li><a href="getHome">Home</a></li>
						<li><a href="register">Register</a></li>
						<li><a href="login">Login</a></li>
					</ul>
				</div>
				<i class="fa fa-bars" onclick="showMenu()"></i>
			</c:otherwise>
		</c:choose>
	</nav>

	<div class="text-boxx">
		<p>
			<strong> Here are your current details </strong>
		<table>
			<tr>
				<th>Username</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Password</th>
				<th>Email</th>
				<th>Role</th>
			</tr>
			<tr>
				<td>${sessionScope.currentUser.username}</td>
				<th>${sessionScope.currentUser.firstName }</th>
				<th>${sessionScope.currentUser.lastName }</th>
				<td>${sessionScope.currentUser.password }</td>
				<td>${sessionScope.currentUser.email }</td>
				<td>${sessionScope.currentUser.role }</td>
			</tr>
		</table>
		</p>



		<p>
			<strong> Please edit <b>ONLY</b> what you wish to update
			</strong>
		</p>
		<sf:form action="updateUserInfo" method="POST"
			modelAttribute="bindUpdateUser">
			<p>
					<sf:label path="username">New Username(min 5 character): </sf:label>
					<sf:input type="text" path="username" id="usernameInput"
							value="${sessionScope.currentUser.username }" />
							
				</p>
				<p>
					<sf:label path="password">New Password(min 5 character): </sf:label>
					<sf:input type="password" path="password"
							id="passwordInput" value="${sessionScope.currentUser.password }" />
						<input type="checkbox" onclick="myFunction()">Show
						Password
				</p>
				<p>
					<sf:label path="email">New Email: </sf:label>
					<sf:input type="text" path="email" id="emailInput"
							value="${sessionScope.currentUser.email }" />
				</p>
				<p>
					<input type="submit" value="Update">
				</p>
		</sf:form>
		<c:if test="${ not empty errorMessage }">
			<p>
				<b>${ errorMessage }</b>
			</p>
		
		</c:if>
		<c:if test="${ not empty message }">
			<p>
				<strong>${ message }</strong>
			</p>
		</c:if>
	</div>
	<script>
		function myFunction() {
			var x = document.getElementById("passwordInput");
			if (x.type === "password") {
				x.type = "text";
			} else {
				x.type = "password";
			}
		}
	</script>

</body>
</html>