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

import com.fdm.PreparationQuizProject.Dal.QuizRepository;
import com.fdm.PreparationQuizProject.Model.Quiz;
import com.fdm.PreparationQuizProject.Model.User;

class QuizServiceTest {
	private QuizService quizService;
	private Quiz quiz;
	private Quiz quiz2;
	private List<Quiz> quizs;
	
	@Mock
	User mockUser;
	
	@Mock
	QuizRepository mockQuizRepo;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		quizService = new QuizService(mockQuizRepo);
		quiz = new Quiz();
		quiz2 = new Quiz();
		quizs = new ArrayList<>(Arrays.asList(quiz,quiz2));
	}
	
	@Test
	void save_return_quiz() {
		// ARRANGE
		when(mockQuizRepo.save(quiz)).thenReturn(quiz);
		// ACT
		Quiz result = quizService.save(quiz);
		// ASSERT
		verify(mockQuizRepo, times(1)).save(quiz);
		
		assertEquals(quiz, result);
	}
	
	@Test
	void findAllQuizzes_return_quizs() {
		// ARRANGE
		when(mockQuizRepo.findAll()).thenReturn(quizs);
		// ACT
		List<Quiz> result = quizService.findAllQuizzes();
		// ASSERT
		verify(mockQuizRepo, times(1)).findAll();
		
		assertEquals(quizs, result);
	}

	@Test
	void updateQuiz() {
		// ARRANGE
		// ACT
		quizService.updateQuiz(quiz);
		// ASSERT
		verify(mockQuizRepo, times(1)).save(quiz);
	}
	
	@Test
	void deleteQuiz() {
		// ARRANGE
		// ACT
		quizService.deleteQuiz(quiz);
		// ASSERT
		verify(mockQuizRepo, times(1)).delete(quiz);
	}
	
	@Test
	void findById_return_null_quizNotFound() {
		// ARRANGE
		Optional<Quiz> foundQuiz = Optional.ofNullable(null);
		when(mockQuizRepo.findById(1)).thenReturn(foundQuiz);
		// ACT
		Quiz result = quizService.findById(1);
		// ASSERT
		verify(mockQuizRepo, times(1)).findById(1);
		
		assertEquals(null, result);
	}
	
	@Test
	void findById_return_quiz_quizFound() {
		// ARRANGE
		Optional<Quiz> foundQuiz = Optional.of(quiz);
		when(mockQuizRepo.findById(1)).thenReturn(foundQuiz);
		// ACT
		Quiz result = quizService.findById(1);
		// ASSERT
		verify(mockQuizRepo, times(1)).findById(1);
		
		assertEquals(quiz, result);
	}
	
	@Test
	void findByUser_return_quizs() {
		// ARRANGE
		when(mockQuizRepo.findByUser(mockUser)).thenReturn(quizs);
		// ACT
		List<Quiz> result = quizService.findByUser(mockUser);
		// ASSERT
		verify(mockQuizRepo, times(1)).findByUser(mockUser);
		
		assertEquals(quizs, result);
	}
	
	@Test
	void findByCreaterRole_return_quizs() {
		// ARRANGE
		when(mockQuizRepo.findByCreaterRole("Student")).thenReturn(quizs);
		// ACT
		List<Quiz> result = quizService.findByCreaterRole("Student");
		// ASSERT
		verify(mockQuizRepo, times(1)).findByCreaterRole("Student");
		
		assertEquals(quizs, result);
	}
}
