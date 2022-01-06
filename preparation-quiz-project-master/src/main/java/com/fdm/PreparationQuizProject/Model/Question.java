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

@Entity
public class Question {
	@Id
	@GeneratedValue
	private int id;
	private String subject;
	private String category;
	private String format;
	private String name;
	private int mark;
	private String answer;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "question_choices", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "choices")
	private List<String> choices;
	
	@ManyToOne(fetch = FetchType.EAGER)
	User user;

	public Question() {
		super();
	}

	public Question(String subject, String category, String format, String name, int mark, String answer,
			List<String> choices, User user) {
		super();
		this.subject = subject;
		this.category = category;
		this.format = format;
		this.name = name;
		this.mark = mark;
		this.answer = answer;
		this.choices = choices;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", subject=" + subject + ", category=" + category + ", format=" + format
				+ ", name=" + name + ", mark=" + mark + ", answer=" + answer + ", choices=" + choices + ", user=" + user
				+ "]";
	}
	
}