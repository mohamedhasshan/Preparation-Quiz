<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
table {
  width: 30%;
}

td, th {
  text-align: center;
  padding: 5px;
}
</style>
<title>View Submissions</title>
</head>
<body>
	<h1>User Submissions</h1>
	<c:if test="${not empty requestScope.submissionsToPrint}">
		<c:forEach items="${requestScope.submissionsToPrint}" var="submission">
			<hr>
			<table>
				<tr>
					<th>Quiz Name:</th>
					<td>${submission.name }</td>
				</tr>
				<tr>
					<th>Marked:</th>
					<c:if test="${submission.isMarked() == true }">
						<td>Yes</td>
					</c:if>
					<c:if test="${submission.isMarked() == false }">
						<td>No</td>
					</c:if>
				</tr>
				<tr>
					<th>Mark:</th>
					<td>${submission.studentMark }/${submission.totalMark }</td>
				</tr>
				<tr>
					<th>Categories:</th>
					<c:forEach items="${submission.categories}" var="category">
						<td>${category }</td>
					</c:forEach>
				</tr>
				<tr>
					<th>Subjects:</th>
					<c:forEach items="${submission.subjects}" var="subject">
						<td>${subject }</td>
					</c:forEach>
				</tr>
				<tr>
					<th>Formats:</th>
					<c:forEach items="${submission.questionFormats}" var="format">
						<td>${format }</td>
					</c:forEach>
				</tr>
				<c:choose>
					<c:when test="${sessionScope.currentUser.role=='Student' }">
						<tr>
							<th>
								<form action="getReviewQuiz" method="POST">
									<input type="hidden" name="id" value="${submission.id}" /> <input
										type="submit" value="View">
								</form>
							</th>
						</tr>
					</c:when>
					<c:when test="${sessionScope.currentUser.role=='Trainer' }">
						<tr>
							<th>
								<form action="getReviewQuiz" method="POST">
									<input type="hidden" name="id" value="${submission.id}" /> <input
										type="submit" value="View">
								</form>
								<c:if test="${submission.isMarked() == false }">
									<br>
									<form action="getMarkQuiz" method="POST">
										<input type="hidden" name="id" value="${submission.id}" /> <input
											type="submit" value="Mark">
									</form>
								</c:if>

							</th>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th>
								<form action="getReviewQuiz" method="POST">
									<input type="hidden" name="id" value="${submission.id}" /> <input
										type="submit" value="View">
								</form> <c:if test="${submission.isMarked() == false }">
								<c:forEach items="${submission.categories}" var="category">
								<c:if test="${category == 'Interview Preparation' }">
									<form action="getMarkQuiz" method="POST">
										<input type="hidden" name="id" value="${submission.id}" /> <input
											type="submit" value="Mark">
									</form>
								</c:if>
								</c:forEach>
								
								
								</c:if>

							</th>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</c:forEach>
		<hr>
	</c:if>
	<br>
	<a href="getHome">Click here to return to the home page</a>
</body>
</html>