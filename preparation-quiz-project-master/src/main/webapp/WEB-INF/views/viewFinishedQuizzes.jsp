<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Finished Quizzes</title>
</head>
<body>
	<h1>Finished Quizzes:</h1>
	<c:choose>
		<c:when test="${sessionScope.currentUser.role == 'Student'}">
			<p></p>
		</c:when>
		<c:otherwise>
			<h1>Apply Filter</h1>
			<form action="filterFinishedQuizzes" method="POST">
				<p>Category:</p>
				<input type="checkbox" id="Interview" name="Category"
					value="Interview"> <label for="Interview"> <span>Interview</span></label>
				<input type="checkbox" id="Course Content" name="Category"
					value="Course Content"> <label for="Course Content">
					<span>Course Content</span>
				</label> <br>
				<p>Subject:</p>
				<input type="checkbox" id="OOD" name="Subject" value="OOD">
				<label for="OOD"> <span>OOD</span></label> <input type="checkbox"
					id="SQL" name="Subject" value="SQL"> <label for="SQL">
					<span>SQL</span>
				</label> <input type="checkbox" id="Unix" name="Subject" value="Unix">
				<label for="Unix"> <span>Unix</span></label> <input type="checkbox"
					id="Java" name="Subject" value="Java"> <label for="Java">
					Java</label>
				<p>Marked:</p>
				<input type="checkbox" id="IsMarked" name="IsMarked" value="true">
				<input type="submit" value="Filter" />
			</form>

		</c:otherwise>
	</c:choose>
	<table>
		<c:forEach items="${sessionScope.quizList}" var="currentQuiz">
			<tr>
				<td>${currentQuiz.name}</td>
				<td>${currentQuiz.categories}</td>
				<td>${currentQuiz.subjects}</td>
				<td>${currentQuiz.questionFormats}</td>
				<td>${currentQuiz.isMarked}</td>
				<td>${currentQuiz.studentMark}</td>
				<td><c:choose>
						<c:when test="${sessionScope.currentUser.role == 'Student'}">
							<form action="getFinishedQuiz" method="POST">
								<input type="hidden" name="id" value=${currentQuiz.id}>
								<input type="submit" value="View Quiz" class="button">
							</form>
						</c:when>
						<c:otherwise>
							<form action="getMarkQuiz" method="POST">
								<input type="hidden" name="quizSubmissionId" value=${currentQuiz.id}>
								<input type="submit" value="Mark Quiz" class="button">
							</form>
						</c:otherwise>
					</c:choose></td>
			</tr>
		</c:forEach>
	</table>
	<a href="getHome"> Back to home </a>
</body>
</html>