package com.fdm.PreparationQuizProject.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String role;
	private String status;
	private boolean isRegistered;
	
	@OneToMany(mappedBy = "user")
	private List<Quiz> createdQuizzes;
	
	@OneToMany(mappedBy = "student")
	private List<QuizSubmission> submittedQuizzes;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Question> createdQuestion;
	
	public User() {
		super();
	}

	public User(String username, String password, String firstName, String lastName, String email, String role, String status,
			boolean isRegistered) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.status = status;
		this.isRegistered = isRegistered;
		this.createdQuizzes = new ArrayList<Quiz>();
		this.submittedQuizzes = new ArrayList<QuizSubmission>();
		this.createdQuestion = new ArrayList<Question>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	public List<Quiz> getCreatedQuizzes() {
		return createdQuizzes;
	}

	public void setCreatedQuizzes(List<Quiz> createdQuizzes) {
		this.createdQuizzes = createdQuizzes;
	}

	public List<QuizSubmission> getSubmittedQuizzes() {
		return submittedQuizzes;
	}

	public void setSubmittedQuizzes(List<QuizSubmission> submittedQuizzes) {
		this.submittedQuizzes = submittedQuizzes;
	}

	public List<Question> getCreatedQuestion() {
		return createdQuestion;
	}

	public void setCreatedQuestion(List<Question> createdQuestion) {
		this.createdQuestion = createdQuestion;
	}

	public void addCreatedQuiz(Quiz quiz) {
		this.createdQuizzes.add(quiz);
	}
	
	public void removeCreatedQuiz(Quiz quiz) {
		this.createdQuizzes.remove(quiz);
	}
	
	public void addSubmittedQuiz(QuizSubmission quizSubmission) {
		this.submittedQuizzes.add(quizSubmission);
	}
	
	public void removeSubmittedQuiz(QuizSubmission quizSubmission) {
		this.submittedQuizzes.remove(quizSubmission);
	}
	
	public void updateSubmittedQuiz(QuizSubmission quizSubmissionToUpdate) {
		int updateId = quizSubmissionToUpdate.getId();
		for(QuizSubmission quizSubmission:submittedQuizzes) {
			int id = quizSubmission.getId();
			if(id==updateId)
				quizSubmission=quizSubmissionToUpdate;
		}
	}
	
	public void addCreatedQuestion(Question question) {
		this.createdQuestion.add(question);
	}
	
	public void removeCreatedQuestion(Question question) {
		this.createdQuestion.remove(question);
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", role=" + role + ", status=" + status + ", isRegistered="
				+ isRegistered + "]";
	}


	
	
}
