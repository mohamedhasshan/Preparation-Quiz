package com.fdm.PreparationQuizProject.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.PreparationQuizProject.Dal.QuizRepository;
import com.fdm.PreparationQuizProject.Model.Quiz;
import com.fdm.PreparationQuizProject.Model.User;

@Service
public class QuizService {
	
	private QuizRepository quizRepo;

	@Autowired
	public QuizService(QuizRepository quizRepo) {
		super();
		this.quizRepo = quizRepo;
	}
	
	public Quiz save(Quiz toBeSaved) {
		return quizRepo.save(toBeSaved);
	}
	
	public List<Quiz> findAllQuizzes() {
		return quizRepo.findAll();
	}

	public void updateQuiz(Quiz currentQuiz) {
		quizRepo.save(currentQuiz);
	}
	
	public void deleteQuiz(Quiz Quiz) {
		quizRepo.delete(Quiz);
	}

	public Quiz findById(int quizId) {
		Optional<Quiz> foundQuiz = quizRepo.findById(quizId);
		if(foundQuiz.isEmpty())
			return null;
		return foundQuiz.get();
	}
	
	public List<Quiz> findByUser(User user) {
		return quizRepo.findByUser(user);
	}
	
	public List<Quiz> findByCreaterRole(String createrRole){
		return quizRepo.findByCreaterRole(createrRole);
	}
}
