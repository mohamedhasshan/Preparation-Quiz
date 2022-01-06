<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Review Quiz Page</title>
</head>
<body>
	<h2>Quiz Name: ${requestScope.quizToReview.name } (Mark: ${requestScope.quizToReview.studentMark})</h2>
	<sf:form action="processMarkQuiz" method="POST"
		modelAttribute="quizToReview">
		<c:forEach items="${requestScope.quizToReview.questionSubmissions }"
			var="questionToReview" varStatus="status">
			<hr>
			<c:choose>
				<c:when test="${questionToReview.question.format == 'Multiple Choice'}">
					<sf:label path="questionSubmissions[${status.index}].studentAnswer"> ${questionToReview.question.name} </sf:label>
					<c:forEach items="${questionToReview.question.choices}" var="choice">
						<c:choose>
							<c:when
								test="${questionToReview.studentAnswer==choice }">
								<sf:radiobutton path="questionSubmissions[${status.index}].studentAnswer"
									value="${choice }" id="${choice }"
									name="${questionToReview.question.name}" checked="checked" disabled="true" />
								<sf:label path="questionSubmissions[${status.index}].studentAnswer" for="${choice }"> ${choice } </sf:label>
							</c:when>
							<c:otherwise>
								<sf:radiobutton path="questionSubmissions[${status.index}].studentAnswer"
									value="${choice }" id="${choice }"
									name="${questionToReview.question.name}" disabled="true" />
								<sf:label path="questionSubmissions[${status.index}].studentAnswer" for="${choice }"> ${choice } </sf:label>
							</c:otherwise>
						</c:choose>
					</c:forEach>
							<p>Answer: ${questionToReview.question.answer}</p>
							<p>Mark: ${questionToReview.studentMark }
								/${questionToReview.question.mark }</p>
				</c:when>
				<c:otherwise>
					<sf:label path="questionSubmissions[${status.index}].studentAnswer"> ${questionToReview.question.name} </sf:label>
					<sf:input type="text" path="questionSubmissions[${status.index}].studentAnswer"
						var="${questionToReview.studentAnswer }" disabled="true" />
					<c:if test="${requestScope.quizToReview.isMarked()==true}">
					<p>Suggested Answer: ${questionToReview.question.answer}</p>
					<sf:label path="questionSubmissions[${status.index}].trainerFeedback"> feedback: </sf:label>
					<sf:input type="text" path="questionSubmissions[${status.index}].trainerFeedback"
						var="${questionToReview.trainerFeedback }" disabled="true" />
					<p>Mark: ${questionToReview.studentMark }
						/${questionToReview.question.mark }</p>
					</c:if>
				</c:otherwise>
			</c:choose>
			
		</c:forEach>
	</sf:form>
<hr>
	<form action="getStudentSubmissions" method="POST">
		<input type="hidden" value="${requestScope.quizToReview.student.id }" name="id" />
		<input type="submit" value="Return" />
	</form>

</body>
</html>