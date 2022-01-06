package com.fdm.PreparationQuizProject.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.PreparationQuizProject.Dal.QuestionSubmissionRepository;
import com.fdm.PreparationQuizProject.Model.QuestionSubmission;

@Service
public class QuestionSubmissionService {
	
	private QuestionSubmissionRepository questionSubmissionRepo;
	
	@Autowired
	public QuestionSubmissionService(QuestionSubmissionRepository questionSubmissionRepo) {
		super();
		this.questionSubmissionRepo = questionSubmissionRepo;
	}
	
	public QuestionSubmission saveQuestionSubmission(QuestionSubmission toBeSaved) {
		return questionSubmissionRepo.save(toBeSaved);
	}
	
	public List<QuestionSubmission> findAllQuestionSubmission() {
		return questionSubmissionRepo.findAll();
	}
	
	public void updateQuestionSubmission(QuestionSubmission currentQuestion) {
		questionSubmissionRepo.save(currentQuestion);
	}
	
	public void deleteQuestionSubmission(QuestionSubmission currentQuestion) {
		questionSubmissionRepo.delete(currentQuestion);
	}

}
