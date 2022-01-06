<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
table {
  width: 60%;
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
<title>View Quizzes</title>
</head>
<body>
<h1>View Quizzes</h1>
		<form action="processSortViewQuiz" method="POST">
			Category: <input type="hidden" id="check" name="categoryCheckbox"
				value="check"> <input type="checkbox"
				id="Interview Preparation" name="categoryCheckbox"
				value="Interview Preparation"> <label for="Interview">
				Interview</label> <input type="checkbox" id="Course Content"
				name="categoryCheckbox" value="Course Content"> <label
				for="Course Content"> Course Content</label> <br> <br>
			Subject: <input type="hidden" id="check" name="subjectCheckbox"
				value="check"> <input type="checkbox" id="OOD"
				name="subjectCheckbox" value="OOD"> <label for="OOD">
				OOD</label> <input type="checkbox" id="SQL" name="subjectCheckbox"
				value="SQL"> <label for="SQL"> SQL</label> <input
				type="checkbox" id="UNIX" name="subjectCheckbox" value="UNIX">
			<label for="UNIX"> Unix</label> <input type="checkbox" id="Java"
				name="subjectCheckbox" value="Java"> <label for="Java">
				Java</label> <input type="checkbox" id="Soft Skill" name="subjectCheckbox"
				value="Soft Skill"> <label for="Soft Skill"> Soft
				Skill</label> <br> <br> Question Format: <input type="hidden"
				id="check" name="formatCheckbox" value="check"> <input
				type="checkbox" id="Multiple Choice" name="formatCheckbox"
				value="Multiple Choice"> <label for="Multiple Choice">
				Multiple Choice</label> <input type="checkbox" id="Short Answer"
				name="formatCheckbox" value="Short Answer"> <label
				for="Short Answer"> Short Answer</label> <input type="submit"
				value="Filter" />
		</form>
		
		<hr>
		
		<table>
			<tr>
				<th>Quiz Name</th>
				<th>Category</th>
				<th>Subject</th>
				<th>Question Format</th>
			<tr>
				<c:forEach items="${sessionScope.quizList }" var="currentQuiz">
					<tr>
						<td>${currentQuiz.name}</td>
						<td>${currentQuiz.categories}</td>
						<td>${currentQuiz.subjects}</td>
						<td>${currentQuiz.questionFormats}</td>
						<td>
							<form action="getTakeQuiz" method="POST">
								<input type="hidden" name="quizId" value=${currentQuiz.id }>
								<input type="submit" value="Take Quiz" class="button">
							</form>
						</td>
					</tr>

				</c:forEach>
		</table>
		<hr>
		<br>
		<a href="cancelAndReturn"> Back to home </a>
</body>
</html>