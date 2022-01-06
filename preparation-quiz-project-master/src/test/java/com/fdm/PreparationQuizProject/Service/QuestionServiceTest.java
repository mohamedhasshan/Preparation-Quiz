package com.fdm.PreparationQuizProject.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fdm.PreparationQuizProject.Dal.QuestionRepository;
import com.fdm.PreparationQuizProject.Model.Question;

class QuestionServiceTest {
	private QuestionService questionService;
	private Question question;
	private Question question2;
	private List<Question> questions;
	
	@Mock
	QuestionRepository mockQuestionRepo;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		questionService = new QuestionService(mockQuestionRepo);
		question = new Question();
		question2 = new Question();
		questions = new ArrayList<>(Arrays.asList(question,question2));
	}
	
	@Test
	void saveQuestion_return_question() {
		// ARRANGE
		when(mockQuestionRepo.save(question)).thenReturn(question);
		// ACT
		Question result = questionService.saveQuestion(question);
		// ASSERT
		verify(mockQuestionRepo, times(1)).save(question);
		
		assertEquals(question, result);
	}
	
	@Test
	void findAllQuestion_return_questions() {
		// ARRANGE
		when(mockQuestionRepo.findAll()).thenReturn(questions);
		// ACT
		List<Question> result = questionService.findAllQuestion();
		// ASSERT
		verify(mockQuestionRepo, times(1)).findAll();
		
		assertEquals(questions, result);
	}
	
	@Test
	void updateQuestion() {
		// ARRANGE
		// ACT
		questionService.updateQuestion(question);
		// ASSERT
		verify(mockQuestionRepo, times(1)).save(question);
	}
	
	@Test
	void deleteQuestion() {
		// ARRANGE
		// ACT
		questionService.deleteQuestion(question);
		// ASSERT
		verify(mockQuestionRepo, times(1)).delete(question);
	}
	
	@Test
	void findById_return_Optionalquestion() {
		// ARRANGE
		Optional<Question> foundQuestion = Optional.of(question);
		when(mockQuestionRepo.findById(1)).thenReturn(foundQuestion);
		// ACT
		Optional<Question> result = questionService.findById(1);
		// ASSERT
		verify(mockQuestionRepo, times(1)).findById(1);
		
		assertEquals(foundQuestion, result);
	}
	
	@Test
	void findBySubject_return_questions() {
		// ARRANGE
		when(mockQuestionRepo.findBySubject("Subject")).thenReturn(questions);
		// ACT
		List<Question> result = questionService.findBySubject("Subject");
		// ASSERT
		verify(mockQuestionRepo, times(1)).findBySubject("Subject");
		
		assertEquals(questions, result);
	}
	
	@Test
	void findByCategory_return_questions() {
		// ARRANGE
		when(mockQuestionRepo.findByCategory("Category")).thenReturn(questions);
		// ACT
		List<Question> result = questionService.findByCategory("Category");
		// ASSERT
		verify(mockQuestionRepo, times(1)).findByCategory("Category");
		
		assertEquals(questions, result);
	}
	
	@Test
	void findByFormat_return_questions() {
		// ARRANGE
		when(mockQuestionRepo.findByFormat("Format")).thenReturn(questions);
		// ACT
		List<Question> result = questionService.findByFormat("Format");
		// ASSERT
		verify(mockQuestionRepo, times(1)).findByFormat("Format");
		
		assertEquals(questions, result);
	}
	
	@Test
	void findBySubjectAndCategory_return_questions() {
		// ARRANGE
		when(mockQuestionRepo.findBySubjectAndCategory("Subject","Category")).thenReturn(questions);
		// ACT
		List<Question> result = questionService.findBySubjectAndCategory("Subject","Category");
		// ASSERT
		verify(mockQuestionRepo, times(1)).findBySubjectAndCategory("Subject","Category");
		
		assertEquals(questions, result);
	}
	
	@Test
	void findBySubjectAndFormat_return_questions() {
		// ARRANGE
		when(mockQuestionRepo.findBySubjectAndFormat("Subject","Format")).thenReturn(questions);
		// ACT
		List<Question> result = questionService.findBySubjectAndFormat("Subject","Format");
		// ASSERT
		verify(mockQuestionRepo, times(1)).findBySubjectAndFormat("Subject","Format");
		
		assertEquals(questions, result);
	}
	
	@Test
	void findByCategoryAndFormat_return_questions() {
		// ARRANGE
		when(mockQuestionRepo.findByCategoryAndFormat("Category","Format")).thenReturn(questions);
		// ACT
		List<Question> result = questionService.findByCategoryAndFormat("Category","Format");
		// ASSERT
		verify(mockQuestionRepo, times(1)).findByCategoryAndFormat("Category","Format");
		
		assertEquals(questions, result);
	}
	
	@Test
	void findBySubjectAndCategoryAndFormat_return_questions() {
		// ARRANGE
		when(mockQuestionRepo.findBySubjectAndCategoryAndFormat("Subject","Category","Format")).thenReturn(questions);
		// ACT
		List<Question> result = questionService.findBySubjectAndCategoryAndFormat("Subject","Category","Format");
		// ASSERT
		verify(mockQuestionRepo, times(1)).findBySubjectAndCategoryAndFormat("Subject","Category","Format");
		
		assertEquals(questions, result);
	}
	
	
}
