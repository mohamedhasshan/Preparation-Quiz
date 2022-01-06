<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<style>
.container {
	font-family: arial;
	font-size: 24px;
	margin: auto;
	width: 350px;
	display: flex;
	justify-content: center;
	align-items: center;
}

input[type=submit] {
	background-color: #04AA6D;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

input[type=password], select, textarea {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	margin-top: 6px;
	margin-bottom: 16px;
	resize: vertical
}

input[type=text], select, textarea {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	margin-top: 6px;
	margin-bottom: 16px;
	resize: vertical
}

input[type=submit]:hover {
	background-color: #45a049;
}

h4 {
	text-align: center;
}

a {
	font-size: 15px;
	text-decoration: none;
}
</style>
<title>REGISTER</title>
</head>
<body>
	<div class="container">
		<div class="child">
			<h4>REGISTER</h4>
			<br>
			<form:form method="POST" action="processRegister"
				modelAttribute="currentUser">
				<table>
					<tr>
						<td><form:label path="username">Username: </form:label></td>
						<td><form:input path="username" required="true" /></td>
					</tr>
					<tr>
						<td><form:label path="firstName">First Name: </form:label></td>
						<td><form:input path="firstName" required="true" /></td>
					</tr>
					<tr>
						<td><form:label path="lastName">Last Name: </form:label></td>
						<td><form:input path="lastName" required="true" /></td>
					</tr>
					<tr>
						<td><form:label path="email">Email: </form:label></td>
						<td><form:input path="email" required="true" /></td> 
					</tr>
					<tr>
						<td><form:label path="password">Password: </form:label></td>
						<td><form:input path="password" type="password" id="passwordInput"
								required="true" /><br>
							<input type="checkbox" onclick="myFunction()">Show Password
								</td>
					</tr>
					<tr>
						<td>Role</td>
						<td><form:select path="role">
								<form:option value="Sales" label="Sales" />
								<form:option value="Student" label="Student" />
								<form:option value="Trainer" label="Trainer" />
							</form:select></td>
					</tr>
					<tr>
						<td><input type="submit" value="Submit" /></td>
					</tr>
				</table>
			</form:form>
			<p style="color: red; font-size: 15px; text-align: center;">${errorMessage}</p>
		</div>
	</div>
	<div class="container">
		<a href="getHome"> Back to Home </a>
	</div>
		<script>
		function myFunction() {
			var x = document.getElementById("passwordInput");
			if (x.type === "password") {
				x.type = "text";
			} else {
				x.type = "password";
			}
		}
	</script>
</body>
</html>