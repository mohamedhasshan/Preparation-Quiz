package com.fdm.PreparationQuizProject.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
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

import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.UserService;

class RegisterControllerTest {
	private RegisterController registerController;

	@Mock
	UserService mockUserService;

	@Mock
	private HttpSession mockSession;

	@Mock
	private Model mockModel;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		registerController = new RegisterController(mockUserService);
	}

	@Test
	void fetchRegisterPage_returns_register_page_when_no_session_user() {
		// Arrange

		// Act
		String result = registerController.fetchRegisterPage(mockModel, mockSession);

		// Assert
		verify(mockSession, times(1)).getAttribute(eq(RegisterController.USER));
		assertEquals(RegisterController.REGISTER, result);
	}

	@Test
	void fetchRegisterPage_returns_homepage_when_there_is_sessionUser() {
		// Arrange
		User testUser = new User();
		when(mockSession.getAttribute(RegisterController.USER)).thenReturn(testUser);

		// Act
		String result = registerController.fetchRegisterPage(mockModel, mockSession);

		// Assert
		InOrder order = inOrder(mockSession, mockModel);
		order.verify(mockSession, times(1)).getAttribute(eq(RegisterController.USER));
		order.verify(mockModel, times(1)).addAttribute(RegisterController.ERROR_MESSAGE,
				RegisterController.LOGOUT_MESSAGE);
		assertEquals(RegisterController.HOME, result);
	}

	@Test
	void processRegisterPage_returns_registerPage_when_username_password_checkk_fail() {
		// Arrange
		User testUser = new User("assd", "asff", "test", "test", "test@email.com", "Student", null, false);
		when(mockUserService.checkUsernameAndPassword(testUser)).thenReturn(false);

		// Act
		String result = registerController.processRegister(testUser, mockModel, mockSession);

		// Assert
		InOrder order = inOrder(mockSession, mockModel, mockUserService);
		order.verify(mockUserService, times(1)).checkUsernameAndPassword(testUser);
		order.verify(mockModel, times(1)).addAttribute(RegisterController.ERROR_MESSAGE,
				RegisterController.INVALID_USERNAME_PASSWORD_MSG);
		order.verify(mockModel, times(1)).addAttribute(RegisterController.USER, testUser);
		assertEquals(RegisterController.REGISTER, result);

	}
	
	@Test
	void processRegisterPage_returns_registerPage_when_email_check_fail() {
		//Arrange
		User testUser = new User("assdf", "asfff", "test", "test", "test@emailcom", "Student", null, false);
		when(mockUserService.checkUsernameAndPassword(testUser)).thenReturn(true);
		when(mockUserService.checkEmail(testUser)).thenReturn(false);
		
		//Act
		String result = registerController.processRegister(testUser, mockModel, mockSession);
		
		//Assert
		InOrder order = inOrder(mockSession, mockModel, mockUserService);
		order.verify(mockUserService, times(1)).checkUsernameAndPassword(testUser);
		order.verify(mockUserService, times(1)).checkEmail(testUser);
		order.verify(mockModel, times(1)).addAttribute(RegisterController.ERROR_MESSAGE,
				RegisterController.INVALID_EMAIL_MSG);
		order.verify(mockModel, times(1)).addAttribute(RegisterController.USER, testUser);
		assertEquals(RegisterController.REGISTER, result);
	
	}
	
	
	@Test
	void processRegisterPage_rightEmailUsernamePassword_returnStdentRole() {
		//Arrange
		User testUser = new User("assdf", "asfff", "test", "test", "test@email.com", "Student", null, false);
		User testStudent = new User("assdf", "asfff", "test", "test", "test@email.com", "Student", null, true);	
		when(mockUserService.checkUsernameAndPassword(testUser)).thenReturn(true);
		when(mockUserService.checkEmail(testUser)).thenReturn(true);
		when(mockUserService.registerUser(testUser)).thenReturn(testStudent);
		
		//Act
		String result = registerController.processRegister(testUser, mockModel, mockSession);
		
		//Assert
		InOrder order = inOrder(mockSession, mockModel, mockUserService);
		order.verify(mockUserService, times(1)).checkUsernameAndPassword(testUser);
		order.verify(mockUserService, times(1)).checkEmail(testUser);
		order.verify(mockUserService, times(1)).registerUser(testUser);
		order.verify(mockModel, times(1)).addAttribute(RegisterController.USER, testStudent);
		order.verify(mockModel, times(1)).addAttribute(RegisterController.MESSAGE, RegisterController.COMPLETED_REG_MSG);
		assertEquals(result, RegisterController.HOME);
	}
	
	@Test
	void processRegisterPage_rightEmailUsernamePassword_returnTrainer_sales_Role() {
		//Arrange
		User testUser = new User("assdf", "asfff", "test", "test", "test@email.com", "Trainer", null, false);
		User testTrainer = new User("assdf", "asfff", "test", "test", "test@email.com", "Trainer", null, false);	
		when(mockUserService.checkUsernameAndPassword(testUser)).thenReturn(true);
		when(mockUserService.checkEmail(testUser)).thenReturn(true);
		when(mockUserService.registerUser(testUser)).thenReturn(testTrainer);
		
		//Act
		String result = registerController.processRegister(testUser, mockModel, mockSession);
		
		//Assert
		InOrder order = inOrder(mockSession, mockModel, mockUserService);
		order.verify(mockUserService, times(1)).checkUsernameAndPassword(testUser);
		order.verify(mockUserService, times(1)).checkEmail(testUser);
		order.verify(mockUserService, times(1)).registerUser(testUser);
		order.verify(mockModel, times(1)).addAttribute(RegisterController.USER, testTrainer);
		order.verify(mockModel, times(1)).addAttribute(RegisterController.MESSAGE, RegisterController.TRAINERSALES_REG_MSG);
		assertEquals(result, RegisterController.HOME);
	}
	
	@Test
	void processRegisterPage_return_used_usernameNull_return_registerPage() {
		//Arrange
		User testUser = new User("assdf", "asfff", "test", "test", "test@email.com", "Trainer", null, false);
		when(mockUserService.checkUsernameAndPassword(testUser)).thenReturn(true);
		when(mockUserService.checkEmail(testUser)).thenReturn(true);
		when(mockUserService.registerUser(testUser)).thenReturn(null);
		
		//Act
		String result = registerController.processRegister(testUser, mockModel, mockSession);
		
		//Assert
		InOrder order = inOrder(mockSession, mockModel, mockUserService);
		order.verify(mockUserService, times(1)).checkUsernameAndPassword(testUser);
		order.verify(mockUserService, times(1)).checkEmail(testUser);
		order.verify(mockUserService, times(1)).registerUser(testUser);
		order.verify(mockModel, times(1)).addAttribute(RegisterController.USER, testUser);
		order.verify(mockModel, times(1)).addAttribute(RegisterController.ERROR_MESSAGE, RegisterController.USERNAME_TAKEN_MSG);
		assertEquals(result, RegisterController.REGISTER);
	}
}
