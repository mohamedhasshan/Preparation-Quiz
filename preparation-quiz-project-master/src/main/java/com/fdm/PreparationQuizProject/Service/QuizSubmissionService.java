package com.fdm.PreparationQuizProject.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.PreparationQuizProject.Dal.QuizSubmissionRepository;
import com.fdm.PreparationQuizProject.Model.QuizSubmission;
import com.fdm.PreparationQuizProject.Model.User;

@Service
public class QuizSubmissionService {
	
	private QuizSubmissionRepository quizSubmissionRepo;

	@Autowired
	public QuizSubmissionService(QuizSubmissionRepository quizSubmissionRepo) {
		super();
		this.quizSubmissionRepo = quizSubmissionRepo;
	}
	
	public QuizSubmission save(QuizSubmission toBeSaved) {
		return quizSubmissionRepo.save(toBeSaved);
	}
	
	public List<QuizSubmission> findAllQuizSubmissions() {
		return quizSubmissionRepo.findAll();
	}
	
	public void updateQuizSubmission(QuizSubmission currentQuizSubmission) {
		quizSubmissionRepo.save(currentQuizSubmission);
	}
	
	public void deleteQuizSubmission(QuizSubmission QuizSubmission) {
		quizSubmissionRepo.delete(QuizSubmission);
	}

	public QuizSubmission findById(int quizSubmissionId) {
		Optional<QuizSubmission> foundQuizSubmission = quizSubmissionRepo.findById(quizSubmissionId);
		if(foundQuizSubmission.isEmpty())
			return null;
		return foundQuizSubmission.get();
	}

	public List<QuizSubmission> findByStudent(User student) {
		return quizSubmissionRepo.findByStudent(student);
		
	}
	

}
