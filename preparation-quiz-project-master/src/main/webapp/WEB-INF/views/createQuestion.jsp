<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
label{
	display: inline-block;
	padding: 5px 6px;
	text-align: left;
	width: 60px;
}
input[type=text] {
  width: 15%;
  padding: 5px 10px;
  margin: 6px 0;
  box-sizing: border-box;
}
input[type=number] {
  width: 5%;
  padding: 5px 10px;
  margin: 6px 0;
  box-sizing: border-box;
}
</style>
<meta charset="ISO-8859-1">
<title>Create A Question</title>
</head>
<body>
<h1>Create A Question</h1>

	<form action="processCreateShortQuestion" method="POST">
	
		<label for="subjectSelect">Subject: </label>
		<select name = "subject" id="subjectSelect" required="required">
			<option value="UNIX">UNIX</option>
			<option value="SQL">SQL</option>
			<option value="OOD">OOD</option>
			<option value="Java">Java</option>
			<option value="Soft Skill">Soft Skill</option>
		</select>
		
		<label for="categorySelect">Subject: </label>
		<select name = "category" id="categorySelect" required="required">
			<option value="Course Content">Course Content</option>
			<option value="Interview Preparation">Interview Preparation</option>
		</select> <br>
		
		<label for="questionInput">Question: </label>
		<input type="text" required="required" name="name" id="questionInput"/> <br>
		
		<label for="markInput">Mark: </label>
		<input type="number" required="required" name="mark" id="markInput"/> <br>
		
		<label for="answerInput">Answer: </label>
		<input type="text" required="required" name="answer" id="answerInput"/> <br><br>
		
		<input type="hidden" name="format" value="Short Answer">
		<input type="submit" value="Create Short Answer Question" class="button">
	</form>
	
	<hr>
	
	<form action="processCreateMCQuestion" method="POST">
	
		<label for="subjectSelect">Subject: </label>
		<select name = "subject" id="subjectSelect" required="required">
			<option value="UNIX">UNIX</option>
			<option value="SQL">SQL</option>
			<option value="OOD">OOD</option>
			<option value="Java">Java</option>
			<option value="Soft Skill">Soft Skill</option>
		</select>
		
		<label for="categorySelect">Subject: </label>
		<select name = "category" id="categorySelect" required="required">
			<option value="Course Content">Course Content</option>
			<option value="Interview Preparation">Interview Preparation</option>
		</select> <br>
		
		<label for="questionInput">Question: </label>
		<input type="text" required="required" name="name" id="questionInput"/> <br>
		
		<label for="markInput">Mark: </label>
		<input type="number" required="required" name="mark" id="markInput"/> <br>
		
		<label>Choices: </label> <br>
        <input type="radio" name="answerIndex" value=0 required="required">
		<input type="text" required="required" name = "choices"/> <br>
        
        <input type="radio" name="answerIndex" value=1>
		<input type="text" required="required" name = "choices"/> <br>
        
        <input type="radio" name="answerIndex" value=2>
		<input type="text" required="required" name = "choices"/> <br>
        
        <input type="radio" name="answerIndex" value=3>
		<input type="text" required="required" name = "choices"/> <br><br>
		
		<input type="hidden" name="format" value="Multiple Choice">
		<input type="submit" value="Create Multiple Choice Question" class="button">
	</form>
	
	<c:if test="${not empty requestScope.error }">
		<p>${requestScope.error }</p>
	</c:if>
	
	<br>
	<a href="getHome">Click here to return to the home page</a>
	
</body>
</html>