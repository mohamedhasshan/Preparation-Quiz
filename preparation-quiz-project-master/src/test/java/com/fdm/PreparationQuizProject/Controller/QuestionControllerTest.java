package com.fdm.PreparationQuizProject.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.fdm.PreparationQuizProject.Model.Question;
import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.QuestionService;
import com.fdm.PreparationQuizProject.Service.UserService;

class QuestionControllerTest {

	QuestionController questionController;
	
	@Mock
	private Model mockModel;
	
	@Mock
	private HttpSession mockSession;
	
	@Mock
	private UserService mockUserService;
	
	@Mock
	private QuestionService mockQuestionService;
	
	@Mock
	private User mockUser;
	
	@Mock
	private Question mockQuestion;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		questionController = new QuestionController(mockUserService, mockQuestionService);
	}
	
	@Test
	public void getCreateQuestionPage_noLogin_returns_URL_accessError() {
		// ARRANGE
		when(mockSession.getAttribute(QuestionController.SESSION_CURRENT_USER_ATTR)).thenReturn(null);
		// ACT
		String result = questionController.getCreateQuestionPage(mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute(QuestionController.ERROR_MODEL_ATTR, QuestionController.REQUIRE_LOGIN_MESSAGE);
		assertEquals(QuestionController.GET_ACCESS_ERROR_VIEW, result);
	}
	
	@Test
	public void getCreateQuestionPage_absentStudent_returns_URL_accessError() {
		// ARRANGE
		when(mockSession.getAttribute(QuestionController.SESSION_CURRENT_USER_ATTR)).thenReturn(mockUser);
		when(mockUser.getRole()).thenReturn("Student");
		when(mockUser.getStatus()).thenReturn("Absent");
		// ACT
		String result = questionController.getCreateQuestionPage(mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute(QuestionController.ERROR_MODEL_ATTR, QuestionController.NO_ACCESS_MESSAGE);
		assertEquals(QuestionController.GET_ACCESS_ERROR_VIEW, result);
	}
	
	@Test
	public void getCreateQuestionPage_success_returns_URL_createQuestion() {
		// ARRANGE
		when(mockSession.getAttribute(QuestionController.SESSION_CURRENT_USER_ATTR)).thenReturn(mockUser);
		when(mockUser.getRole()).thenReturn("Student");
		when(mockUser.getStatus()).thenReturn("Ongoing");
		// ACT
		String result = questionController.getCreateQuestionPage(mockModel, mockSession);
		// ASSERT
		assertEquals(QuestionController.GET_CREATE_QUESTION_VIEW, result);
	}
	
	@Test
	public void processCreateShortQuestion_returns_URL_createQuestionConfirmation() {
		// ARRANGE
		String mockSubject = "mockSubject";
		String mockCategory = "mockCategory";
		String mockFormat = "mockFormat";
		String mockName = "mockName";
		int mockMark = 1;
		String mockAnswer = "mockAnswer";
		
		when(mockSession.getAttribute(QuestionController.SESSION_CURRENT_USER_ATTR)).thenReturn(mockUser);
		when(mockUserService.save(mockUser)).thenReturn(mockUser);
		// ACT
		String result = questionController.processCreateShortQuestion(mockSubject, mockCategory, mockFormat, mockName, mockMark, mockAnswer, mockModel, mockSession);
		// ASSERT
		InOrder order = inOrder(mockQuestionService, mockUser, mockUserService, mockSession);
		order.verify(mockQuestionService, times(1)).saveQuestion(isA(Question.class));
		order.verify(mockUser, times(1)).addCreatedQuestion(null);
		order.verify(mockUserService, times(1)).save(mockUser);
		order.verify(mockSession, times(1)).setAttribute(QuestionController.SESSION_CURRENT_USER_ATTR, mockUser);
		assertEquals(QuestionController.GET_CREATE_QUESTION_CONFIRMATION_VIEW, result);
	}
	
	@Test
	public void processCreateMCQuestion_returns_URL_createQuestionConfirmation() {
		// ARRANGE
		String mockSubject = "mockSubject";
		String mockCategory = "mockCategory";
		String mockFormat = "mockFormat";
		String mockName = "mockName";
		int mockMark = 1;
		int mockAnswerIndex = 0;
		String[] mockChoices = {"1","2","3"};
		
		when(mockSession.getAttribute(QuestionController.SESSION_CURRENT_USER_ATTR)).thenReturn(mockUser);
		when(mockUserService.save(mockUser)).thenReturn(mockUser);
		// ACT
		String result = questionController.processCreateMCQuestion(mockSubject, mockCategory, mockFormat, mockName, mockMark, mockAnswerIndex, mockChoices, mockModel, mockSession);
		// ASSERT
		InOrder order = inOrder(mockQuestionService, mockUser, mockUserService, mockSession);
		order.verify(mockQuestionService, times(1)).saveQuestion(isA(Question.class));
		order.verify(mockUser, times(1)).addCreatedQuestion(null);
		order.verify(mockUserService, times(1)).save(mockUser);
		order.verify(mockSession, times(1)).setAttribute(QuestionController.SESSION_CURRENT_USER_ATTR, mockUser);
		assertEquals(QuestionController.GET_CREATE_QUESTION_CONFIRMATION_VIEW, result);
	}
	
	
}
