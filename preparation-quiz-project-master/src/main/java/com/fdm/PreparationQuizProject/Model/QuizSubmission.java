package com.fdm.PreparationQuizProject.Model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class QuizSubmission {
	@Id
	@GeneratedValue
	private int id;
	protected String name;
	private boolean isMarked;
	private int studentMark;
	
	@ManyToOne(fetch = FetchType.EAGER)
	User student;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<QuestionSubmission> questionSubmissions;
	
	@ElementCollection
	@CollectionTable(name = "quizSubmission_subjects", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "subjects")
	protected List<String> subjects;
	
	@ElementCollection
	@CollectionTable(name = "quizSubmission_categories", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "categories")
	protected List<String> categories;
	
	@ElementCollection
	@CollectionTable(name = "quizSubmission_questionFormats", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "questionFormats")
	protected List<String> questionFormats;
	
	protected int numOfQuestions;
	protected int totalMark;
	
	public QuizSubmission() {
		super();
	}

	public QuizSubmission(String name, boolean isMarked, int studentMark, User student,
			List<QuestionSubmission> questionSubmissions, List<String> subjects, List<String> categories,
			List<String> questionFormats, int numOfQuestions, int totalMark) {
		super();
		this.name = name;
		this.isMarked = isMarked;
		this.studentMark = studentMark;
		this.student = student;
		this.questionSubmissions = questionSubmissions;
		this.subjects = subjects;
		this.categories = categories;
		this.questionFormats = questionFormats;
		this.numOfQuestions = numOfQuestions;
		this.totalMark = totalMark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public boolean isMarked() {
		return isMarked;
	}

	public void setMarked(boolean isMarked) {
		this.isMarked = isMarked;
	}

	public int getStudentMark() {
		return studentMark;
	}

	public void setStudentMark(int studentMark) {
		this.studentMark = studentMark;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public List<QuestionSubmission> getQuestionSubmissions() {
		return questionSubmissions;
	}

	public void setQuestionSubmissions(List<QuestionSubmission> questionSubmissions) {
		this.questionSubmissions = questionSubmissions;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getQuestionFormats() {
		return questionFormats;
	}

	public void setQuestionFormats(List<String> questionFormats) {
		this.questionFormats = questionFormats;
	}

	public int getNumOfQuestions() {
		return numOfQuestions;
	}

	public void setNumOfQuestions(int numOfQuestions) {
		this.numOfQuestions = numOfQuestions;
	}

	public int getTotalMark() {
		return totalMark;
	}

	public void setTotalMark(int totalMark) {
		this.totalMark = totalMark;
	}
	
	public void update() {
		for(int i = 0; i < questionSubmissions.size(); i++) {
			QuestionSubmission q = questionSubmissions.get(i);
			for(int j = i + 1; j < questionSubmissions.size(); j++) {
				if(questionSubmissions.get(j).getId() == q.getId()) {
					questionSubmissions.remove(j);
					j -= 1;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "QuizSubmission [id=" + id + ", name=" + name + ", getIsMarked=" + isMarked + ", studentMark=" + studentMark
				+ ", student=" + student + ", questionSubmissions=" + questionSubmissions + ", subjects=" + subjects
				+ ", categories=" + categories + ", questionFormats=" + questionFormats + ", numOfQuestions="
				+ numOfQuestions + ", totalMark=" + totalMark + "]";
	}

	


}
