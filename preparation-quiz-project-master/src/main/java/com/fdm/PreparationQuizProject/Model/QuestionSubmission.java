package com.fdm.PreparationQuizProject.Model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class QuestionSubmission {
	@Id
	@GeneratedValue
	private int id;
	private String studentAnswer;
	private String trainerFeedback;
	private int studentMark;
	
	@ManyToOne(fetch = FetchType.EAGER)
	Question question;
	
	public QuestionSubmission() {
		super();
	}

	public QuestionSubmission(String studentAnswer, String trainerFeedback, int studentMark, Question question) {
		super();
		this.studentAnswer = studentAnswer;
		this.trainerFeedback = trainerFeedback;
		this.studentMark = studentMark;
		this.question = question;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStudentAnswer() {
		return studentAnswer;
	}

	public void setStudentAnswer(String studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

	public String getTrainerFeedback() {
		return trainerFeedback;
	}

	public void setTrainerFeedback(String trainerFeedback) {
		this.trainerFeedback = trainerFeedback;
	}

	public int getStudentMark() {
		return studentMark;
	}

	public void setStudentMark(int studentMark) {
		this.studentMark = studentMark;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}


	@Override
	public String toString() {
		return "QuestionSubmission [id=" + id + ", studentAnswer=" + studentAnswer + ", trainerFeedback="
				+ trainerFeedback + ", studentMark=" + studentMark + ", question=" + question + "]";
	}
	
	
	
	
	
}
