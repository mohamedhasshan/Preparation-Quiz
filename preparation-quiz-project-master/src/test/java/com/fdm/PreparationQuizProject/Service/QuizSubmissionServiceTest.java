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

import com.fdm.PreparationQuizProject.Dal.QuizSubmissionRepository;
import com.fdm.PreparationQuizProject.Model.QuizSubmission;
import com.fdm.PreparationQuizProject.Model.User;

class QuizSubmissionServiceTest {
	private QuizSubmissionService quizSubmissionService;
	private QuizSubmission quizSubmission;
	private QuizSubmission quizSubmission2;
	private List<QuizSubmission> quizSubmissions;
	
	@Mock
	QuizSubmissionRepository mockQuizSubmissionRepo;
	
	@Mock
	User mockUser;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		quizSubmissionService = new QuizSubmissionService(mockQuizSubmissionRepo);
		quizSubmission = new QuizSubmission();
		quizSubmission2 = new QuizSubmission();
		quizSubmissions = new ArrayList<>(Arrays.asList(quizSubmission,quizSubmission2));
	}
	
	@Test
	void save_return_quizSubmission() {
		// ARRANGE
		when(mockQuizSubmissionRepo.save(quizSubmission)).thenReturn(quizSubmission);
		// ACT
		QuizSubmission result = quizSubmissionService.save(quizSubmission);
		// ASSERT
		verify(mockQuizSubmissionRepo,times(1)).save(quizSubmission);
		
		assertEquals(quizSubmission,result);
	}
	
	@Test
	void findAllQuizSubmissions_return_quizSubmissions() {
		// ARRANGE
		when(mockQuizSubmissionRepo.findAll()).thenReturn(quizSubmissions);
		// ACT
		List<QuizSubmission> result = quizSubmissionService.findAllQuizSubmissions();
		// ASSERT
		verify(mockQuizSubmissionRepo,times(1)).findAll();
		
		assertEquals(quizSubmissions,result);
	}
	
	@Test
	void updateQuizSubmission() {
		// ACT
		quizSubmissionService.updateQuizSubmission(quizSubmission);
		// ASSERT
		verify(mockQuizSubmissionRepo,times(1)).save(quizSubmission);
	}
	
	@Test
	void deleteQuizSubmission() {
		// ACT
		quizSubmissionService.deleteQuizSubmission(quizSubmission);
		// ASSERT
		verify(mockQuizSubmissionRepo,times(1)).delete(quizSubmission);
	}
	
	@Test
	void findById_return_null_quizSubmissionNotFound() {
		// ARRANGE
		Optional <QuizSubmission> foundQuizSubmission = Optional.ofNullable(null);
		when(mockQuizSubmissionRepo.findById(1)).thenReturn(foundQuizSubmission);
		// ACT
		QuizSubmission result = quizSubmissionService.findById(1);
		// ASSERT
		verify(mockQuizSubmissionRepo,times(1)).findById(1);
		
		assertEquals(null,result);
	}
	
	@Test
	void findById_return_null_quizSubmissionIsFound() {
		// ARRANGE
		Optional <QuizSubmission> foundQuizSubmission = Optional.of(quizSubmission);
		when(mockQuizSubmissionRepo.findById(1)).thenReturn(foundQuizSubmission);
		// ACT
		QuizSubmission result = quizSubmissionService.findById(1);
		// ASSERT
		verify(mockQuizSubmissionRepo,times(1)).findById(1);
		
		assertEquals(quizSubmission,result);
	}
	
	@Test
	void findByStudent_return_quizSubmissions() {
		// ARRANGE
		when(mockQuizSubmissionRepo.findByStudent(mockUser)).thenReturn(quizSubmissions);
		// ACT
		List<QuizSubmission> result = quizSubmissionService.findByStudent(mockUser);
		// ASSERT
		verify(mockQuizSubmissionRepo,times(1)).findByStudent(mockUser);
		
		assertEquals(quizSubmissions,result);
	}

}
