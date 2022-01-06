<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Mark Quiz Page</title>
</head>
<body>
	<h2>Quiz Name: ${requestScope.quizMarking.name }</h2>
	<sf:form action="processMarkQuiz" method="POST"
		modelAttribute="quizMarking">
		<sf:input type="hidden" path="id" var="${requestScope.quizMarking.id}" />
		<sf:input type="hidden" path="student"
			var="${requestScope.quizMarking.student}" />
		<sf:input type="hidden" path="name"
			var="${requestScope.quizMarking.name}" />
		<sf:input type="hidden" path="subjects"
			var="${requestScope.quizMarking.subjects}" />
		<sf:input type="hidden" path="categories"
			var="${requestScope.quizMarking.categories}" />
		<sf:input type="hidden" path="questionFormats"
			var="${requestScope.quizMarking.questionFormats}" />
		<sf:input type="hidden" path="numOfQuestions"
			var="${requestScope.quizMarking.numOfQuestions}" />
		<sf:input type="hidden" path="totalMark"
			var="${requestScope.quizMarking.totalMark}" />
		<sf:input type="hidden" path="studentMark"
			var="${requestScope.quizMarking.studentMark}" />

		<c:forEach items="${requestScope.quizMarking.questionSubmissions }"
			var="questionToMark" varStatus="status">
			<hr>
			<sf:input type="hidden"
				path="questionSubmissions[${status.index}].question"
				var="${questionToMark.question}" />
			<sf:input type="hidden"
				path="questionSubmissions[${status.index}].id"
				var="${questionToMark.id}" />
			<sf:input type="hidden"
				path="questionSubmissions[${status.index}].studentAnswer"
				var="${questionToMark.studentAnswer}" />
			<c:choose>
				<c:when
					test="${questionToMark.question.format == 'Multiple Choice'}">
					<sf:input type="hidden"
						path="questionSubmissions[${status.index}].studentMark"
						var="${questionToMark.studentMark}" />

					<sf:label path="questionSubmissions[${status.index}].studentAnswer"> ${questionToMark.question.name} </sf:label>
					<p>Mark: ${questionToMark.studentMark }
						/${questionToMark.question.mark }</p>
					<c:forEach items="${questionToMark.question.choices}" var="choice">
						<c:choose>
							<c:when
								test="${questionToMark.studentAnswer == choice }">
								<sf:radiobutton
									path="questionSubmissions[${status.index}].studentAnswer"
									value="${choice }" id="${choice }"
									name="${questionToMark.question.name}" checked="checked"
									disabled="true" />
								<sf:label
									path="questionSubmissions[${status.index}].studentAnswer"
									for="${choice }"> ${choice } </sf:label>
							</c:when>
							<c:otherwise>
								<sf:radiobutton
									path="questionSubmissions[${status.index}].studentAnswer"
									value="${choice }" id="${choice }"
									name="${questionToMark.question.name}" disabled="true" />
								<sf:label
									path="questionSubmissions[${status.index}].studentAnswer"
									for="${choice }"> ${choice } </sf:label>
							</c:otherwise>
						</c:choose>
						<br>
					</c:forEach>
					<p>Answer: ${questionToMark.question.answer}</p>
				</c:when>
				<c:otherwise>
					<sf:label path="questionSubmissions[${status.index}].studentAnswer"> ${questionToMark.question.name} </sf:label>
					<sf:input type="text"
						path="questionSubmissions[${status.index}].studentAnswer"
						var="${questionToMark.studentAnswer }" disabled="true" />
					<p>Suggested Answer: ${questionToMark.question.answer}</p>
					<sf:label
						path="questionSubmissions[${status.index}].trainerFeedback"> feedback: </sf:label>
					<sf:input type="text"
						path="questionSubmissions[${status.index}].trainerFeedback" />
					<sf:label path="questionSubmissions[${status.index}].studentMark"> Mark (Out of ${ questionToMark.question.mark}): </sf:label>
					<sf:input type="number"
						path="questionSubmissions[${status.index}].studentMark" />
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<hr>
		<input type="submit" value="Submit" />
	</sf:form>
</body>
</html>