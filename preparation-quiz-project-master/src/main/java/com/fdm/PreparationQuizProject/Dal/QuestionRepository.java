package com.fdm.PreparationQuizProject.Dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.PreparationQuizProject.Model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{

	List<Question> findBySubject(String subject);
	List<Question> findByCategory(String category);
	List<Question> findByFormat(String format);
	
	List<Question> findBySubjectAndCategory(String subject, String category);
	List<Question> findBySubjectAndFormat(String subject, String category);
	List<Question> findByCategoryAndFormat(String category, String format);
	
	List<Question> findBySubjectAndCategoryAndFormat(String subject, String category, String format);
	
}
