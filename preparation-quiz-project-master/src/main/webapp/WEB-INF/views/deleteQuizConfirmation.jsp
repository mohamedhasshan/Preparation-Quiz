<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<title>Delete Quiz Confirmation</title>
</head>
<body>
<h1>Delete Quiz</h1>
<p>You are deleting quiz: ${sessionScope.quizToDelete.name }</p>
<a href="deleteQuiz"><button>Confirm Delete</button></a>
<br>
<br>
<a href="cancelAndReturn"><button>Cancel</button></a>
</body>
</html>