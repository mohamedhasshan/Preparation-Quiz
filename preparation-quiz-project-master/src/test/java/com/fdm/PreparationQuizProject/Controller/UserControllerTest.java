package com.fdm.PreparationQuizProject.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.UserService;

class UserControllerTest {
	private UserController userController;

	@Mock
	private UserService mockUserService;

	@Mock
	private HttpSession mockSession;

	@Mock
	private Model mockModel;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		userController = new UserController(mockUserService);
	}

	@Test
	void test_homePage() {
		String result = userController.getHome();
		assertEquals(result, UserController.HOME);
	}

	@Test
	void test_getUserUpdate_returns_updateView() {
		// Arrange
		// Act
		String result = userController.getUserUpdate(mockModel);
		// Assert
		assertEquals(result, UserController.UPDATE_USER_VIEW);
	}

	@Test
	void processUserInfoUpdate_return_false_forallusernamePasswordEmail_return_update() {
		// Arrange
		User testOriginalUser = new User("assdf", "asfff", "test", "test", "test@email.com", "Student", null, true);
		User testUserToUpdate = new User("as", "as", "test", "test", "testemail.com", "Student", null, true);
		when(mockSession.getAttribute(UserController.USER_ATTR)).thenReturn(testOriginalUser);
		when(mockUserService.checkUsername(testUserToUpdate)).thenReturn(false);
		when(mockUserService.checkPassword(testUserToUpdate)).thenReturn(false);
		when(mockUserService.checkEmail(testUserToUpdate)).thenReturn(false);
		// Act
		String result = userController.processUserInfoUpdate(testUserToUpdate, mockSession, mockModel);

		// Assert
		InOrder order = inOrder(mockModel, mockSession, mockUserService);
		order.verify(mockSession, times(1)).getAttribute(UserController.USER_ATTR);
		order.verify(mockUserService, times(1)).checkUsername(testUserToUpdate);
		order.verify(mockModel, times(1)).addAttribute(UserController.ERROR_MESSAGE, UserController.INVALID_INPUT_MSG);
		assertEquals(result, UserController.UPDATE_USER_VIEW);

	}

	@Test
	void processUserInfoUpdate_return_for_username_true_PasswordEmail_false_return_update() {
		// Arrange
		User testOriginalUser = new User("assdf", "asfff", "test", "test", "test@email.com", "Student", null, true);
		User testUserToUpdate = new User("asasd", "as", "test", "test", "testemail.com", "Student", null, true);
		when(mockSession.getAttribute(UserController.USER_ATTR)).thenReturn(testOriginalUser);
		when(mockUserService.checkUsername(testUserToUpdate)).thenReturn(true);
		when(mockUserService.checkPassword(testUserToUpdate)).thenReturn(false);
		when(mockUserService.checkEmail(testUserToUpdate)).thenReturn(false);
		// Act
		String result = userController.processUserInfoUpdate(testUserToUpdate, mockSession, mockModel);

		// Assert
		InOrder order = inOrder(mockModel, mockSession, mockUserService);
		order.verify(mockSession, times(1)).getAttribute(UserController.USER_ATTR);
		order.verify(mockUserService, times(1)).checkUsername(testUserToUpdate);
		order.verify(mockUserService, times(1)).checkPassword(testUserToUpdate);
		order.verify(mockModel, times(1)).addAttribute(UserController.ERROR_MESSAGE, UserController.INVALID_INPUT_MSG);
		assertEquals(result, UserController.UPDATE_USER_VIEW);
	}

	@Test
	void processUserInfoUpdate_return_for_usernamePassword_true_Email_false_return_update() {
		// Arrange
		User testOriginalUser = new User("assdf", "asfff", "test", "test", "test@email.com", "Student", null, true);
		User testUserToUpdate = new User("asasd", "asasd", "test", "test", "testemail.com", "Student", null, true);
		when(mockSession.getAttribute(UserController.USER_ATTR)).thenReturn(testOriginalUser);
		when(mockUserService.checkUsername(testUserToUpdate)).thenReturn(true);
		when(mockUserService.checkPassword(testUserToUpdate)).thenReturn(true);
		when(mockUserService.checkEmail(testUserToUpdate)).thenReturn(false);
		// Act
		String result = userController.processUserInfoUpdate(testUserToUpdate, mockSession, mockModel);

		// Assert
		InOrder order = inOrder(mockModel, mockSession, mockUserService);
		order.verify(mockSession, times(1)).getAttribute(UserController.USER_ATTR);
		order.verify(mockUserService, times(1)).checkUsername(testUserToUpdate);
		order.verify(mockUserService, times(1)).checkPassword(testUserToUpdate);
		order.verify(mockUserService, times(1)).checkEmail(testUserToUpdate);
		order.verify(mockModel, times(1)).addAttribute(UserController.ERROR_MESSAGE, UserController.INVALID_INPUT_MSG);
		assertEquals(result, UserController.UPDATE_USER_VIEW);
	}

	@Test
	void processUserInfoUpdate_return_for_usernamePasswordEmailTrue_return_update() {
		// Arrange
		User testOriginalUser = new User("assdf", "asfff", "test", "test", "test@email.com", "Student", null, true);
		User testUserToUpdate = new User("asasd", "asasd", "test", "test", "test@email.com", "Student", null, true);
		when(mockSession.getAttribute(UserController.USER_ATTR)).thenReturn(testOriginalUser);
		when(mockUserService.checkUsername(testUserToUpdate)).thenReturn(true);
		when(mockUserService.checkPassword(testUserToUpdate)).thenReturn(true);
		when(mockUserService.checkEmail(testUserToUpdate)).thenReturn(true);
		when(mockUserService.checkAndSave(testOriginalUser)).thenReturn(null);
		// Act
		String result = userController.processUserInfoUpdate(testUserToUpdate, mockSession, mockModel);

		// Assert
		InOrder order = inOrder(mockModel, mockSession, mockUserService);
		order.verify(mockSession, times(1)).getAttribute(UserController.USER_ATTR);
		order.verify(mockUserService, times(1)).checkUsername(testUserToUpdate);
		order.verify(mockUserService, times(1)).checkPassword(testUserToUpdate);
		order.verify(mockUserService, times(1)).checkEmail(testUserToUpdate);
		order.verify(mockUserService, times(1)).checkAndSave(testOriginalUser);
		assertEquals(result, UserController.HOME);
	}

	@Test
	void processUserInfoUpdate_return_for_usernamePasswordEmailTrue_return_HOME() {
		// Arrange
		User testOriginalUser = new User("assdf", "asfff", "test", "test", "test@email.com", "Student", null, true);
		User testUserToUpdate = new User("asasd", "as", "test", "test", "test@email.com", "Student", null, true);
		User savedVersion = new User("asasd", "as", "test", "test", "test@email.com", "Student", null, true);
		savedVersion.setId(1);
		when(mockSession.getAttribute(UserController.USER_ATTR)).thenReturn(testOriginalUser);
		when(mockUserService.checkUsername(testUserToUpdate)).thenReturn(true);
		when(mockUserService.checkPassword(testUserToUpdate)).thenReturn(true);
		when(mockUserService.checkEmail(testUserToUpdate)).thenReturn(true);
		when(mockUserService.checkAndSave(testUserToUpdate)).thenReturn(savedVersion);
		// Act
		String result = userController.processUserInfoUpdate(testUserToUpdate, mockSession, mockModel);

		// Assert
		InOrder order = inOrder(mockModel, mockSession, mockUserService);
		order.verify(mockSession, times(1)).getAttribute(UserController.USER_ATTR);
		order.verify(mockUserService, times(1)).checkUsername(testUserToUpdate);
		order.verify(mockUserService, times(1)).checkPassword(testUserToUpdate);
		order.verify(mockUserService, times(1)).checkEmail(testUserToUpdate);
		order.verify(mockSession, times(1)).setAttribute(UserController.USER_ATTR, testOriginalUser);
		order.verify(mockModel, times(1)).addAttribute(UserController.MESSAGE, UserController.DETAILS_UPDATED_MSG);
		assertEquals(result, UserController.HOME);
	}
}