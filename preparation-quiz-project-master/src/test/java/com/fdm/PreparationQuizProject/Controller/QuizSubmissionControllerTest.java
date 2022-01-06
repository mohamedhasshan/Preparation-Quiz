package com.fdm.PreparationQuizProject.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.fdm.PreparationQuizProject.Model.Question;
import com.fdm.PreparationQuizProject.Model.QuestionSubmission;
import com.fdm.PreparationQuizProject.Model.QuizSubmission;
import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.QuestionSubmissionService;
import com.fdm.PreparationQuizProject.Service.QuizSubmissionService;
import com.fdm.PreparationQuizProject.Service.UserService;

class QuizSubmissionControllerTest {
	private QuizSubmissionController quizSubmissionController; 
	private QuizSubmission quizSubmission;
	private List<QuizSubmission> quizSubmissions;
	private QuestionSubmission questionSubmission;
	private QuestionSubmission questionSubmission2;
	private List<QuestionSubmission> questionSubmissions;
	private Question question;
	private Question question2;
	private User user;

	
	@Mock
	QuizSubmissionService mockQuizSubmissionService;
	
	@Mock
	UserService mockUserService;
	
	@Mock
	QuestionSubmissionService mockQuestionSubmissionService;
	
	@Mock
	Model mockModel;
	
	@Mock
	HttpSession mockSession;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		quizSubmissionController = new QuizSubmissionController(mockQuizSubmissionService, mockUserService, mockQuestionSubmissionService);
		
		user = new User();
		
		question = new Question("SQL","Course Content","Multiple Choice","Q1",4,"a",Arrays.asList("a","b","c","d"),user);
		question2 = new Question("OOD","Course Content","Short Answer","Q1",6,"a",null,user);
		
		questionSubmission = new QuestionSubmission(null, null, 0, question);
		questionSubmission2 = new QuestionSubmission(null, null, 0, question2);
		questionSubmissions = new ArrayList<>(Arrays.asList(questionSubmission,questionSubmission2));
		
		quizSubmission = new QuizSubmission();
		quizSubmissions = new ArrayList<>(Arrays.asList(quizSubmission));


	}
	
	@Test
	void getMarkQuizPage_return_markQuiz() {
		// Arrange
		InOrder inOrder = inOrder(mockQuizSubmissionService,mockModel);
		
		quizSubmission.setQuestionSubmissions(questionSubmissions);
		when(mockQuizSubmissionService.findById(1)).thenReturn(quizSubmission);
		// ACT
		String result = quizSubmissionController.getMarkQuizPage(1,mockModel);
		// ASSERT
		inOrder.verify(mockQuizSubmissionService,times(1)).findById(1);
		inOrder.verify(mockModel,times(1)).addAttribute(QuizSubmissionController.QUIZ_MARKING_ATTR,quizSubmission);
		
		assertEquals(QuizSubmissionController.MARK_QUIZ_VIEW,result);
	}
	
	@Test
	void processMarkQuizPage_return_home() {
		// Arrange
		InOrder inOrder = inOrder(mockQuizSubmissionService,mockQuestionSubmissionService,mockUserService);

		questionSubmission.setStudentAnswer("a");
		questionSubmissions = new ArrayList<>(Arrays.asList(questionSubmission,questionSubmission2));
		quizSubmission.setQuestionSubmissions(questionSubmissions);
		quizSubmission.setStudent(user);
		user.setSubmittedQuizzes(Arrays.asList(quizSubmission));
		when(mockQuizSubmissionService.findById(1)).thenReturn(quizSubmission);
		when(mockQuizSubmissionService.save(quizSubmission)).thenReturn(quizSubmission);
		// ACT
		String result = quizSubmissionController.processMarkQuizPage(quizSubmission);
		// ASSERT
		inOrder.verify(mockQuestionSubmissionService,times(1)).saveQuestionSubmission(questionSubmission);
		inOrder.verify(mockQuestionSubmissionService,times(1)).saveQuestionSubmission(questionSubmission2);
		inOrder.verify(mockQuizSubmissionService,times(1)).save(quizSubmission);
		inOrder.verify(mockUserService,times(1)).save(user);
		
		assertEquals(QuizSubmissionController.HOME_VIEW,result);
	}
	
	@Test
	void getReviewQuizPage_return_reviewQuizSubmission() {
		// Arrange
		InOrder inOrder = inOrder(mockQuizSubmissionService,mockModel);
		
		quizSubmission.setQuestionSubmissions(questionSubmissions);
		when(mockQuizSubmissionService.findById(1)).thenReturn(quizSubmission);
		// ACT
		String result = quizSubmissionController.getReviewQuizPage(1,mockModel);
		// ASSERT
		inOrder.verify(mockQuizSubmissionService,times(1)).findById(1);
		inOrder.verify(mockModel,times(1)).addAttribute(QuizSubmissionController.QUIZ_TO_REVIEW_ATTR,quizSubmission);
		
		assertEquals(QuizSubmissionController.REVIEW_QUIZ_SUBMISSION_VIEW,result);
	}
	
	@Test
	void getMySubmissionsPage_return_viewMySubmission() {
		// Arrange
		InOrder inOrder = inOrder(mockSession, mockUserService, mockQuizSubmissionService,mockModel);
		user.setId(1);
		when(mockSession.getAttribute(QuizSubmissionController.CURRENT_USER_ATTR)).thenReturn(user);
		when(mockUserService.findById(1)).thenReturn(user);
		when(mockQuizSubmissionService.findByStudent(user)).thenReturn(quizSubmissions);
		// ACT
		String result = quizSubmissionController.getMySubmissionsPage(mockSession,mockModel);
		// ASSERT
		inOrder.verify(mockSession,times(1)).getAttribute(QuizSubmissionController.CURRENT_USER_ATTR);
		inOrder.verify(mockUserService,times(1)).findById(1);
		inOrder.verify(mockQuizSubmissionService,times(1)).findByStudent(user);
		inOrder.verify(mockModel,times(1)).addAttribute(QuizSubmissionController.SUBMISSIONS_TO_PRINT_ATTR,quizSubmissions);
		
		assertEquals(QuizSubmissionController.VIEW_MY_SUBMISSIONS_VIEW,result);
	}

	@Test
	void getStudentSubmissionsPage_return_viewMySubmission() {
		// Arrange
		InOrder inOrder = inOrder( mockUserService, mockQuizSubmissionService,mockModel);
		when(mockUserService.findById(1)).thenReturn(user);
		when(mockQuizSubmissionService.findByStudent(user)).thenReturn(quizSubmissions);
		// ACT
		String result = quizSubmissionController.getStudentSubmissionsPage(1,mockModel);
		// ASSERT
		inOrder.verify(mockUserService,times(1)).findById(1);
		inOrder.verify(mockQuizSubmissionService,times(1)).findByStudent(user);
		inOrder.verify(mockModel,times(1)).addAttribute(QuizSubmissionController.SUBMISSIONS_TO_PRINT_ATTR,quizSubmissions);
		
		assertEquals(QuizSubmissionController.VIEW_MY_SUBMISSIONS_VIEW,result);
	}
}
