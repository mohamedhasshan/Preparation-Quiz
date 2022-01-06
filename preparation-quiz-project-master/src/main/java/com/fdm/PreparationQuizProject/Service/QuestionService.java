package com.fdm.PreparationQuizProject.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.PreparationQuizProject.Dal.QuestionRepository;
import com.fdm.PreparationQuizProject.Model.Question;

@Service
public class QuestionService {
	
	private QuestionRepository questionRepo;
	
	@Autowired
	public QuestionService(QuestionRepository questionRepo) {
		super();
		this.questionRepo = questionRepo;
	}
	
	public Question saveQuestion(Question toBeSaved) {
		return questionRepo.save(toBeSaved);
	}
	
	public List<Question> findAllQuestion() {
		return questionRepo.findAll();
	}
	
	public void updateQuestion(Question currentQuestion) {
		questionRepo.save(currentQuestion);
	}
	
	public void deleteQuestion(Question currentQuestion) {
		questionRepo.delete(currentQuestion);
	}
	
	public Optional<Question> findById(int id){
		return questionRepo.findById(id);
	}
	
	public List<Question> findBySubject(String subject){
		return questionRepo.findBySubject(subject);
	}
	
	public List<Question> findByCategory(String category){
		return questionRepo.findByCategory(category);
	}
	
	public List<Question> findByFormat(String format){
		return questionRepo.findByFormat(format);
	}
	
	public List<Question> findBySubjectAndCategory(String subject, String category){
		return questionRepo.findBySubjectAndCategory(subject, category);
	}
	
	public List<Question> findBySubjectAndFormat(String subject, String category){
		return questionRepo.findBySubjectAndFormat(subject, category);
	}
	
	public List<Question> findByCategoryAndFormat(String category, String format){
		return questionRepo.findByCategoryAndFormat(category, format);
	}
	
	public List<Question> findBySubjectAndCategoryAndFormat(String subject, String category, String format){
		return questionRepo.findBySubjectAndCategoryAndFormat(subject, category, format);
	}
}
