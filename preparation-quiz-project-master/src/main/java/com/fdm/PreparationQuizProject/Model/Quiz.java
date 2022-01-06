package com.fdm.PreparationQuizProject.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Quiz {
	@Id
	@GeneratedValue
	protected int id;
	protected String name;
	protected String createrRole;
	
	@ElementCollection
	@CollectionTable(name = "quiz_subjects", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "subjects")
	protected List<String> subjects;
	
	@ElementCollection
	@CollectionTable(name = "quiz_categories", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "categories")
	protected List<String> categories;
	
	@ElementCollection
	@CollectionTable(name = "quiz_questionFormats", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "questionFormats")
	protected List<String> questionFormats;
	
	protected int numOfQuestions;
	protected int totalMark;
	
	@ManyToOne(fetch = FetchType.EAGER)
	User user;
	
	@ManyToMany(fetch = FetchType.EAGER)
	List<Question> questions;

	public Quiz() {
		super();
	}

	public Quiz(String name, User user, List<Question> questions) {
		super();
		this.name = name;
		this.createrRole = user.getRole();
		this.user = user;
		this.questions = questions;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreaterRole() {
		return createrRole;
	}

	public void setCreaterRole(String createrRole) {
		this.createrRole = createrRole;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public void addQuestion(Question question) {
		this.questions.add(question);
	}
	
	public void removeQuestion(Question question) {
		this.questions.remove(question);
	}

	public List<String> loadSubjects(){
		List<String> temp = new ArrayList<String>();
		for (Question question: questions) {
			if(!temp.contains(question.getSubject())) {
				temp.add(question.getSubject());
			}
		}
		return temp;
	}
	
	public List<String> loadCategories(){
		List<String> temp = new ArrayList<String>();
		for (Question question: questions) {
			if(!temp.contains(question.getCategory())) {
				temp.add(question.getCategory());
			}
		}
		return temp;
	}
	
	public List<String> loadQuestionFormats(){
		List<String> temp = new ArrayList<String>();
		for (Question question: questions) {
			if(!temp.contains(question.getFormat())) {
				temp.add(question.getFormat());
			}
		}
		return temp;
	}
	
	public int loadTotalMark(){
		int total = 0;
		for (Question question: questions) {
			total += question.getMark();
		}
		return total;
	}
	
	public void update() {
		for(int i = 0; i < questions.size(); i++) {
			Question q = questions.get(i);
			for(int j = i + 1; j < questions.size(); j++) {
				if(questions.get(j).getId() == q.getId()) {
					questions.remove(j);
					j -= 1;
				}
			}
		}
		
		this.subjects = loadSubjects();
		this.categories = loadCategories();
		this.questionFormats = loadQuestionFormats();
		this.numOfQuestions = questions.size();
		this.totalMark = loadTotalMark();
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", name=" + name + ", createrRole=" + createrRole + ", subjects=" + subjects
				+ ", categories=" + categories + ", questionFormats=" + questionFormats + ", numOfQuestions="
				+ numOfQuestions + ", totalMark=" + totalMark + ", user=" + user + ", questions=" + questions + "]";
	}
	
}
