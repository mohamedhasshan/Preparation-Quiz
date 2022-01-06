package com.fdm.PreparationQuizProject.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fdm.PreparationQuizProject.Dal.QuestionSubmissionRepository;
import com.fdm.PreparationQuizProject.Model.QuestionSubmission;

class QuestionSubmissionServiceTest {
	private QuestionSubmissionService questionSubmissionService;
	private QuestionSubmission questionSubmission;
	private QuestionSubmission questionSubmission2;
	private List<QuestionSubmission> questionSubmissions;
	
	@Mock
	QuestionSubmissionRepository mockQuestionSubmissionRepo;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		questionSubmissionService = new QuestionSubmissionService(mockQuestionSubmissionRepo);
		questionSubmission = new QuestionSubmission();
		questionSubmission2 = new QuestionSubmission();
		questionSubmissions = new ArrayList<>(Arrays.asList(questionSubmission,questionSubmission2));
	}
	
	@Test
	void saveQuestionSubmission_return_questionSubmission() {
		// ARRANGE
		when(mockQuestionSubmissionRepo.save(questionSubmission)).thenReturn(questionSubmission);
		// ACT
		QuestionSubmission result = questionSubmissionService.saveQuestionSubmission(questionSubmission);
		//ASSERT
		verify(mockQuestionSubmissionRepo, times(1)).save(questionSubmission);
	
		assertEquals(questionSubmission,result);
	}
	
	@Test
	void findAllQuestionSubmission_return_questionSubmissions() {
		// ARRANGE
		when(mockQuestionSubmissionRepo.findAll()).thenReturn(questionSubmissions);
		// ACT
		List<QuestionSubmission> result = questionSubmissionService.findAllQuestionSubmission();
		//ASSERT
		verify(mockQuestionSubmissionRepo, times(1)).findAll();
	
		assertEquals(questionSubmissions,result);
	}
	
	@Test
	void updateQuestionSubmission() {
		// ARRANGE
		// ACT
		questionSubmissionService.updateQuestionSubmission(questionSubmission);
		//ASSERT
		verify(mockQuestionSubmissionRepo, times(1)).save(questionSubmission);
	}
	
	@Test
	void deleteQuestionSubmission() {
		// ARRANGE
		// ACT
		questionSubmissionService.deleteQuestionSubmission(questionSubmission);
		//ASSERT
		verify(mockQuestionSubmissionRepo, times(1)).delete(questionSubmission);
	}

}
