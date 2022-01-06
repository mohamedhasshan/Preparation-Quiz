<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
table {
  width: 25%;
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
<title>Create Quiz</title>
</head>
<body>
	<h1>Create Quiz</h1>
	<sf:form action="processSortCreateQuiz" method="POST"
		modelAttribute="sortQuestion">
Sort by: <label for="subject">Subject</label>
		<sf:select id="subject" path="subject" size="1">
			<option value="ALL">All</option>
			<option value="UNIX">UNIX</option>
			<option value="SQL">SQL</option>
			<option value="OOD">OOD</option>
			<option value="Java">Java</option>
			<option value="Soft Skill">Soft Skill</option>
		</sf:select>

		<label for="category">Category</label>
		<sf:select id="category" path="category" size="1">

			<option value="ALL">All</option>
			<option value="Course Content">Course Content</option>
			<option value="Interview Preparation">Interview Preparation</option>
		</sf:select>

		<label for="format">Format</label>
		<sf:select id="format" path="format" size="1">
			<option value="ALL">All</option>
			<option value="Short Answer">Short Answer</option>
			<option value="Multiple Choice">Multiple Choice</option>
		</sf:select>

		<input type="submit" value="Filter" />
	</sf:form>
	<hr>
	<h3>Search Result:</h3>
	<table>
		<tr>
			<th>Question</th>
			<th>Mark</th>
			<th></th>
		</tr>
			<c:forEach items="${sessionScope.sortedQuestions }"
				var="currentQuestion">
				<tr>
					<td>${currentQuestion.name}</td>
					<td>${currentQuestion.mark}</td>
					<td>
						<form action="processAddQuestionToQuiz" method="POST">
							<input type="hidden" name="id" value=${currentQuestion.id }>
							<input type="submit" value="Add" class="button">
						</form>
					</td>
				</tr>

			</c:forEach>
	</table>
	<hr>
	<h3>Added Questions:</h3>
	<table>
		<tr>
			<th>Question</th>
			<th>Mark</th>
			<th></th>
		<tr>
			<c:forEach items="${sessionScope.addedQuestionsList }"
				var="currentQuestion">
				<tr>
					<td>${currentQuestion.name}</td>
					<td>${currentQuestion.mark}</td>
					<td>
						<form action="processRemoveQuestionFromQuiz" method="POST">
							<input type="hidden" name="id" value=${currentQuestion.id }>
							<input type="submit" value="Remove" class="button">
						</form>
					</td>
				</tr>

			</c:forEach>
	</table>
	<hr>
	<br>
	<form action="processQuizCreation" method="POST">
		<label for="name">Quiz Name:</label> <input type="text" name="name"
			value="${sessionScope.quizToEdit.name}" required> <input
			type="submit" value="Save Quiz" class="button">
	</form>
	<br>
	<a href="cancelAndReturn"> Cancel and return home </a>
</body>
</html>