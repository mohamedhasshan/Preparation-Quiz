<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Take Quiz Page</title>
</head>
<body>
	<h2>Quiz Name: ${requestScope.quizTaking.name }</h2>
	<sf:form action="processSubmitQuiz" method="POST"
		modelAttribute="quizTaking">
		<sf:input type="hidden" path="student"
			var="${requestScope.quizTaking.student}" />
		<sf:input type="hidden" path="name"
			var="${requestScope.quizTaking.name}" />
		<sf:input type="hidden" path="subjects"
			var="${requestScope.quizTaking.subjects}" />
		<sf:input type="hidden" path="categories"
			var="${requestScope.quizTaking.categories}" />
		<sf:input type="hidden" path="questionFormats"
			var="${requestScope.quizTaking.questionFormats}" />
		<sf:input type="hidden" path="numOfQuestions"
			var="${requestScope.quizTaking.numOfQuestions}" />
		<sf:input type="hidden" path="totalMark"
			var="${requestScope.quizTaking.totalMark}" />

		<c:forEach items="${quizTaking.questionSubmissions}"
			var="questionToDo" varStatus="status">
			<hr>
			<sf:input type="hidden"
				path="questionSubmissions[${status.index}].question"
				var="${questionToDo.question}" />
			<c:choose>
				<c:when test="${questionToDo.question.format == 'Multiple Choice'}">
					<sf:label path="questionSubmissions[${status.index}].studentAnswer"> ${questionToDo.question.name} Mark:${questionToDo.question.mark} </sf:label>
					<br>
					<c:forEach items="${questionToDo.question.choices}" var="choice">
						<sf:radiobutton
							path="questionSubmissions[${status.index}].studentAnswer"
							value="${choice }" id="${choice }"
							name="${questionToDo.question.name}" />
						<sf:label path="${questionToDo.studentAnswer}" for="${choice }"> ${choice } </sf:label>
						<br>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<sf:label path="questionSubmissions[${status.index}].studentAnswer"> ${questionToDo.question.name} </sf:label>
					<br>
					<sf:input type="text"
						path="questionSubmissions[${status.index}].studentAnswer" />
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<hr>
		<input type="submit" value="Submit Quiz" />
	</sf:form>


</body>
</html>