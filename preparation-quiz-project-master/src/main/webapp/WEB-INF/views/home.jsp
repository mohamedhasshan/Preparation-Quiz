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
<title>Home Page</title>
</head>
<body>
	<section class="header">
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

						<ul>
							<li><a href="getHome">Home</a></li>
							<li><a href="register">Register</a></li>
							<li><a href="login">Login</a></li>
						</ul>
					</div>

				</c:otherwise>
			</c:choose>
		</nav>
	</section>
	<div class="text-box">
		<h1>Best Quiz System</h1>
		<c:choose>
			<c:when test="${not empty sessionScope.currentUser }">
				<p>Welcome ${sessionScope.currentUser.role }
					${sessionScope.currentUser.firstName }!</p>
			</c:when>
			<c:otherwise>
				<p>Hello! Please login or register</p>
			</c:otherwise>
		</c:choose>
		<p>

			<c:if test="${ not empty errorMessage }">
				<p>${ errorMessage }</p>
			</c:if>
			<c:if test="${ not empty message }">
				<p>${ message }</p>
			</c:if>
			<c:if test="${ not empty error }">
				<p>${ error }</p>
			</c:if>
		</p>
	</div>
</body>
</html>