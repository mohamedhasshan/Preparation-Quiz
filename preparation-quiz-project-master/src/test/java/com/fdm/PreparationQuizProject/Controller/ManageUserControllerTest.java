package com.fdm.PreparationQuizProject.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.UserService;

class ManageUserControllerTest {
	
	private ManageUserController manageUserController;
	
	@Mock
	private Model mockModel;
	
	@Mock
	private HttpSession mockSession;

	@Mock
	private UserService mockUserService;
	
	@Mock
	private User mockUser;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		manageUserController = new ManageUserController(mockUserService);
	}
	
	@Test
	public void getManageUsersPage_noLogin_returns_URL_accessError() {
		// ARRANGE
		when(mockSession.getAttribute(ManageUserController.SESSION_CURRENT_USER_ATTR)).thenReturn(null);
		// ACT
		String result = manageUserController.getManageUsersPage(mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute(ManageUserController.ERROR_MODEL_ATTR, ManageUserController.REQUIRE_LOGIN_MESSAGE);
		assertEquals(ManageUserController.GET_ACCESS_ERROR_VIEW, result);
	}
	
	@Test
	public void getManageUsersPage_userIsStudent_returns_URL_accessError() {
		// ARRANGE
		when(mockSession.getAttribute(ManageUserController.SESSION_CURRENT_USER_ATTR)).thenReturn(mockUser);
		when(mockUser.getRole()).thenReturn("Student");
		// ACT
		String result = manageUserController.getManageUsersPage(mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute(ManageUserController.ERROR_MODEL_ATTR, ManageUserController.NO_ACCESS_MESSAGE);
		assertEquals(ManageUserController.GET_ACCESS_ERROR_VIEW, result);
	}
	
	@Test
	public void getManageUsersPage_success_returns_URL_manageUsers() {
		// ARRANGE
		when(mockSession.getAttribute(ManageUserController.SESSION_CURRENT_USER_ATTR)).thenReturn(mockUser);
		when(mockUser.getRole()).thenReturn("Trainer");
		// ACT
		String result = manageUserController.getManageUsersPage(mockModel, mockSession);
		// ASSERT
		assertEquals(ManageUserController.GET_MANAGE_USERS_VIEW, result);
	}
	
	@Test
	public void processManageStudentSearch_noInput_returns_URL_manageUsers() {
		// ARRANGE
		String mockFname = "";
		String mockLname = "";
		List<User> mockStudents = new ArrayList<User>();
		when(mockUserService.findByRole("Student")).thenReturn(mockStudents);
		// ACT
		String result = manageUserController.processManageStudentSearch(mockFname, mockLname, mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute("students", mockStudents);
		assertEquals(ManageUserController.GET_MANAGE_USERS_VIEW, result);
	}
	
	@Test
	public void processManageStudentSearch_fname_returns_URL_manageUsers() {
		// ARRANGE
		String mockFname = "mockFname";
		String mockLname = "";
		List<User> mockStudents = new ArrayList<User>();
		when(mockUserService.findByRoleAndFirstNameIgnoreCase("Student", mockFname) ).thenReturn(mockStudents);
		// ACT
		String result = manageUserController.processManageStudentSearch(mockFname, mockLname, mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute("students", mockStudents);
		assertEquals(ManageUserController.GET_MANAGE_USERS_VIEW, result);
	}
	
	@Test
	public void processManageStudentSearch_lname_returns_URL_manageUsers() {
		// ARRANGE
		String mockFname = "";
		String mockLname = "mockLname";
		List<User> mockStudents = new ArrayList<User>();
		when(mockUserService.findByRoleAndLastNameIgnoreCase("Student", mockLname) ).thenReturn(mockStudents);
		// ACT
		String result = manageUserController.processManageStudentSearch(mockFname, mockLname, mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute("students", mockStudents);
		assertEquals(ManageUserController.GET_MANAGE_USERS_VIEW, result);
	}
	
	@Test
	public void processManageStudentSearch_fnameAndLname_returns_URL_manageUsers() {
		// ARRANGE
		String mockFname = "mockFname";
		String mockLname = "mockLname";
		List<User> mockStudents = new ArrayList<User>();
		when(mockUserService.findByRoleAndFirstNameAndLastNameIgnoreCase("Student", mockFname, mockLname)).thenReturn(mockStudents);
		// ACT
		String result = manageUserController.processManageStudentSearch(mockFname, mockLname, mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute("students", mockStudents);
		assertEquals(ManageUserController.GET_MANAGE_USERS_VIEW, result);
	}
	
	@Test
	public void getEditStudentPage_noLogin_returns_URL_accessError() {
		// ARRANGE
		int mockId = 1;
		when(mockSession.getAttribute(ManageUserController.SESSION_CURRENT_USER_ATTR)).thenReturn(null);
		// ACT
		String result = manageUserController.getEditStudentPage(mockId, mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute(ManageUserController.ERROR_MODEL_ATTR, ManageUserController.REQUIRE_LOGIN_MESSAGE);
		assertEquals(ManageUserController.GET_ACCESS_ERROR_VIEW, result);
	}
	
	@Test
	public void getEditStudentPage_userIsStudent_returns_URL_accessError() {
		// ARRANGE
		int mockId = 1;
		when(mockSession.getAttribute(ManageUserController.SESSION_CURRENT_USER_ATTR)).thenReturn(mockUser);
		when(mockUser.getRole()).thenReturn("Student");
		// ACT
		String result = manageUserController.getEditStudentPage(mockId, mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute(ManageUserController.ERROR_MODEL_ATTR, ManageUserController.NO_ACCESS_MESSAGE);
		assertEquals(ManageUserController.GET_ACCESS_ERROR_VIEW, result);
	}
	
	@Test
	public void getEditStudentPage_success_returns_URL_updateStudent() {
		// ARRANGE
		int mockId = 1;
		
		when(mockSession.getAttribute(ManageUserController.SESSION_CURRENT_USER_ATTR)).thenReturn(mockUser);
		when(mockUser.getRole()).thenReturn("Trainer");
		when(mockUserService.findById(mockId)).thenReturn(mockUser);
		// ACT
		String result = manageUserController.getEditStudentPage(mockId, mockModel, mockSession);
		// ASSERT
		InOrder order = inOrder(mockModel);
		order.verify(mockModel, times(1)).addAttribute("student", mockUser);
		order.verify(mockModel, times(1)).addAttribute("allStatus", ManageUserController.ALL_STATUS_OPTIONS_STRINGS);
		order.verify(mockModel, times(1)).addAttribute("salesStatusOptions", ManageUserController.SALES_STATUS_OPTIONS_STRINGS);
		assertEquals(ManageUserController.GET_UPDATE_STUDENT_VIEW, result);
	}
	
	@Test
	public void processUserUpdate_usernameExists_returns_URL_updateStudent() {
		// ARRANGE
		int mockId = 1;
		String mockFirstName = "mockFirstname";
		String mockLastName = "mockLastName";
		String mockUsername = "mockUsername";
		String mockPassword = "mockPassword";
		String mockEmail = "mockEmail";
		String mockStatus = "mockStatus";
		
		when(mockUserService.findById(mockId)).thenReturn(mockUser);
		when(mockUser.getUsername()).thenReturn("differentUsername");
		when(mockUserService.findByUsername(mockUsername)).thenReturn(mockUser);
		// ACT
		String result = manageUserController.processUserUpdate(mockId, mockFirstName, mockLastName, mockUsername, mockPassword, mockEmail, mockStatus, mockModel, mockSession);
		// ASSERT
		InOrder order = inOrder(mockModel);
		order.verify(mockModel, times(1)).addAttribute(ManageUserController.ERROR_MODEL_ATTR, "The username already exists");
		order.verify(mockModel, times(1)).addAttribute("student", mockUser);
		order.verify(mockModel, times(1)).addAttribute("allStatus", ManageUserController.ALL_STATUS_OPTIONS_STRINGS);
		order.verify(mockModel, times(1)).addAttribute("salesStatusOptions", ManageUserController.SALES_STATUS_OPTIONS_STRINGS);
		assertEquals(ManageUserController.GET_UPDATE_STUDENT_VIEW, result);
	}
	
	@Test
	public void processUserUpdate_invalidUsernameAndPasswordLength_returns_URL_updateStudent() {
		// ARRANGE
		int mockId = 1;
		String mockFirstName = "mockFirstname";
		String mockLastName = "mockLastName";
		String mockUsername = "1";
		String mockPassword = "2";
		String mockEmail = "mockEmail";
		String mockStatus = "mockStatus";
		
		when(mockUserService.findById(mockId)).thenReturn(mockUser);
		when(mockUser.getUsername()).thenReturn("differentUsername");
		when(mockUserService.findByUsername(mockUsername)).thenReturn(null);
		// ACT
		String result = manageUserController.processUserUpdate(mockId, mockFirstName, mockLastName, mockUsername, mockPassword, mockEmail, mockStatus, mockModel, mockSession);
		// ASSERT
		InOrder order = inOrder(mockModel);
		order.verify(mockModel, times(1)).addAttribute(ManageUserController.ERROR_MODEL_ATTR, "Username and password should be at least 5 characters");
		order.verify(mockModel, times(1)).addAttribute("student", mockUser);
		order.verify(mockModel, times(1)).addAttribute("allStatus", ManageUserController.ALL_STATUS_OPTIONS_STRINGS);
		order.verify(mockModel, times(1)).addAttribute("salesStatusOptions", ManageUserController.SALES_STATUS_OPTIONS_STRINGS);
		assertEquals(ManageUserController.GET_UPDATE_STUDENT_VIEW, result);
	}
	
	@Test
	public void processUserUpdate_invalidEmail_returns_URL_updateStudent() {
		// ARRANGE
		int mockId = 1;
		String mockFirstName = "mockFirstname";
		String mockLastName = "mockLastName";
		String mockUsername = "mockUsername";
		String mockPassword = "mockPassword";
		String mockEmail = "mockEmail";
		String mockStatus = "mockStatus";
		
		when(mockUserService.findById(mockId)).thenReturn(mockUser);
		when(mockUser.getUsername()).thenReturn(mockUsername);
		when(mockUserService.findByUsername(mockUsername)).thenReturn(mockUser);
		// ACT
		String result = manageUserController.processUserUpdate(mockId, mockFirstName, mockLastName, mockUsername, mockPassword, mockEmail, mockStatus, mockModel, mockSession);
		// ASSERT
		InOrder order = inOrder(mockModel);
		order.verify(mockModel, times(1)).addAttribute(ManageUserController.ERROR_MODEL_ATTR, "The email you entered was invalid");
		order.verify(mockModel, times(1)).addAttribute("student", mockUser);
		order.verify(mockModel, times(1)).addAttribute("allStatus", ManageUserController.ALL_STATUS_OPTIONS_STRINGS);
		order.verify(mockModel, times(1)).addAttribute("salesStatusOptions", ManageUserController.SALES_STATUS_OPTIONS_STRINGS);
		assertEquals(ManageUserController.GET_UPDATE_STUDENT_VIEW, result);
	}
	
	@Test
	public void processUserUpdate_success_returns_URL_updateStudent() {
		// ARRANGE
		int mockId = 1;
		String mockFirstName = "mockFirstname";
		String mockLastName = "mockLastName";
		String mockUsername = "mockUsername";
		String mockPassword = "mockPassword";
		String mockEmail = "mockEmail@email.com";
		String mockStatus = "mockStatus";
		
		when(mockUserService.findById(mockId)).thenReturn(mockUser);
		when(mockUser.getUsername()).thenReturn(mockUsername);
		when(mockUserService.findByUsername(mockUsername)).thenReturn(null);
		when(mockUserService.save(mockUser)).thenReturn(mockUser);
		// ACT
		String result = manageUserController.processUserUpdate(mockId, mockFirstName, mockLastName, mockUsername, mockPassword, mockEmail, mockStatus, mockModel, mockSession);
		// ASSERT
		InOrder order = inOrder(mockUser, mockUserService, mockModel);
		order.verify(mockUser, times(1)).setFirstName(mockFirstName);
		order.verify(mockUser, times(1)).setLastName(mockLastName);
		order.verify(mockUser, times(1)).setUsername(mockUsername);
		order.verify(mockUser, times(1)).setPassword(mockPassword);
		order.verify(mockUser, times(1)).setStatus(mockStatus);
		order.verify(mockUser, times(1)).setEmail(mockEmail);
		order.verify(mockUserService, times(1)).save(mockUser);
		order.verify(mockModel, times(1)).addAttribute(ManageUserController.MESSAGE_MODEL_ATTR, "You have successfully updated the student");
		order.verify(mockModel, times(1)).addAttribute("student", mockUser);
		order.verify(mockModel, times(1)).addAttribute("allStatus", ManageUserController.ALL_STATUS_OPTIONS_STRINGS);
		order.verify(mockModel, times(1)).addAttribute("salesStatusOptions", ManageUserController.SALES_STATUS_OPTIONS_STRINGS);
		assertEquals(ManageUserController.GET_UPDATE_STUDENT_VIEW, result);
	}
	
	@Test
	public void getManageRegistrationRequests_noLogin_returns_URL_accessError() {
		// ARRANGE
		when(mockSession.getAttribute(ManageUserController.SESSION_CURRENT_USER_ATTR)).thenReturn(null);
		// ACT
		String result = manageUserController.getManageRegistrationRequests(mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute(ManageUserController.ERROR_MODEL_ATTR, ManageUserController.REQUIRE_LOGIN_MESSAGE);
		assertEquals(ManageUserController.GET_ACCESS_ERROR_VIEW, result);
	}
	
	@Test
	public void getManageRegistrationRequests_userIsStudent_returns_URL_accessError() {
		// ARRANGE
		when(mockSession.getAttribute(ManageUserController.SESSION_CURRENT_USER_ATTR)).thenReturn(mockUser);
		when(mockUser.getRole()).thenReturn("Student");
		// ACT
		String result = manageUserController.getManageRegistrationRequests(mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute(ManageUserController.ERROR_MODEL_ATTR, ManageUserController.NO_ACCESS_MESSAGE);
		assertEquals(ManageUserController.GET_ACCESS_ERROR_VIEW, result);
	}
	
	@Test
	public void getManageRegistrationRequests_trainerSuccess_returns_URL_manageRegistrationRequests() {
		// ARRANGE
		List<User> mockUsers = new ArrayList<User>();
		mockUsers.add(mockUser);
		
		when(mockSession.getAttribute(ManageUserController.SESSION_CURRENT_USER_ATTR)).thenReturn(mockUser);
		when(mockUser.getRole()).thenReturn("Trainer");
		when(mockUserService.findByRoleAndIsRegistered("Trainer", false)).thenReturn(mockUsers);
		// ACT
		String result = manageUserController.getManageRegistrationRequests(mockModel, mockSession);
		// ASSERT
		InOrder order = inOrder(mockUserService, mockModel);
		order.verify(mockUserService, times(1)).findByRoleAndIsRegistered("Trainer", false);
		order.verify(mockModel, times(1)).addAttribute("registrationRequests", mockUsers);
		assertEquals(ManageUserController.GET_MANAGE_REGISTRATION_REQUESTS_VIEW, result);
	}
	
	@Test
	public void getManageRegistrationRequests_salesSuccess_returns_URL_manageRegistrationRequests() {
		// ARRANGE
		List<User> mockUsers = new ArrayList<User>();
		mockUsers.add(mockUser);
		
		when(mockSession.getAttribute(ManageUserController.SESSION_CURRENT_USER_ATTR)).thenReturn(mockUser);
		when(mockUser.getRole()).thenReturn("Sales");
		when(mockUserService.findByRoleAndIsRegistered("Sales", false)).thenReturn(mockUsers);
		// ACT
		String result = manageUserController.getManageRegistrationRequests(mockModel, mockSession);
		// ASSERT
		InOrder order = inOrder(mockUserService, mockModel);
		order.verify(mockUserService, times(1)).findByRoleAndIsRegistered("Sales", false);
		order.verify(mockModel, times(1)).addAttribute("registrationRequests", mockUsers);
		assertEquals(ManageUserController.GET_MANAGE_REGISTRATION_REQUESTS_VIEW, result);
	}
	
	@Test
	public void processApproveRegistration_returns_URL_processRegistrationConfirmation() {
		// ARRANGE
		int mockId = 0;
		when(mockUserService.findById(mockId)).thenReturn(mockUser);
		// ACT
		String result = manageUserController.processApproveRegistration(mockId, mockModel);
		// ASSERT
		InOrder order = inOrder(mockUser, mockUserService, mockModel);
		order.verify(mockUser, times(1)).setRegistered(true);
		order.verify(mockUserService, times(1)).save(mockUser);
		order.verify(mockModel, times(1)).addAttribute(ManageUserController.MESSAGE_MODEL_ATTR, "You have approved the registration request");
		assertEquals(ManageUserController.GET_PROCESS_REGISTRATION_CONFIRMATION_VIEW, result);
	}
	
	@Test
	public void processDenyRegistration_returns_URL_processRegistrationConfirmation() {
		// ARRANGE
		int mockId = 0;
		when(mockUserService.findById(mockId)).thenReturn(mockUser);
		// ACT
		String result = manageUserController.processDenyRegistration(mockId, mockModel);
		// ASSERT
		InOrder order = inOrder(mockUserService, mockModel);
		order.verify(mockUserService, times(1)).deleteUser(mockUser);
		order.verify(mockModel, times(1)).addAttribute(ManageUserController.MESSAGE_MODEL_ATTR, "You have denied the registration request");
		assertEquals(ManageUserController.GET_PROCESS_REGISTRATION_CONFIRMATION_VIEW, result);
	}
	
	@Test
	public void getViewStudentSubmissionsPage_noLogin_returns_URL_accessError() {
		// ARRANGE
		when(mockSession.getAttribute(ManageUserController.SESSION_CURRENT_USER_ATTR)).thenReturn(null);
		// ACT
		String result = manageUserController.getViewStudentSubmissionsPage(mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute(ManageUserController.ERROR_MODEL_ATTR, ManageUserController.REQUIRE_LOGIN_MESSAGE);
		assertEquals(ManageUserController.GET_ACCESS_ERROR_VIEW, result);
	}
	
	@Test
	public void getViewStudentSubmissionsPage_userIsStudent_returns_URL_accessError() {
		// ARRANGE
		when(mockSession.getAttribute(ManageUserController.SESSION_CURRENT_USER_ATTR)).thenReturn(mockUser);
		when(mockUser.getRole()).thenReturn("Student");
		// ACT
		String result = manageUserController.getViewStudentSubmissionsPage(mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute(ManageUserController.ERROR_MODEL_ATTR, ManageUserController.NO_ACCESS_MESSAGE);
		assertEquals(ManageUserController.GET_ACCESS_ERROR_VIEW, result);
	}
	
	@Test
	public void getViewStudentSubmissionsPage_success_returns_URL_manageUsers() {
		// ARRANGE
		when(mockSession.getAttribute(ManageUserController.SESSION_CURRENT_USER_ATTR)).thenReturn(mockUser);
		when(mockUser.getRole()).thenReturn("Trainer");
		// ACT
		String result = manageUserController.getViewStudentSubmissionsPage(mockModel, mockSession);
		// ASSERT
		assertEquals(ManageUserController.GET_VIEW_STUDENT_SUBMISSIONS_VIEW, result);
	}
	
	@Test
	public void processStudentSubmissionSearch_noInput_returns_URL_manageUsers() {
		// ARRANGE
		String mockFname = "";
		String mockLname = "";
		List<User> mockStudents = new ArrayList<User>();
		when(mockUserService.findByRole("Student")).thenReturn(mockStudents);
		// ACT
		String result = manageUserController.processStudentSubmissionSearch(mockFname, mockLname, mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute("students", mockStudents);
		assertEquals(ManageUserController.GET_VIEW_STUDENT_SUBMISSIONS_VIEW, result);
	}
	
	@Test
	public void processStudentSubmissionSearch_fname_returns_URL_manageUsers() {
		// ARRANGE
		String mockFname = "mockFname";
		String mockLname = "";
		List<User> mockStudents = new ArrayList<User>();
		when(mockUserService.findByRoleAndFirstNameIgnoreCase("Student", mockFname) ).thenReturn(mockStudents);
		// ACT
		String result = manageUserController.processStudentSubmissionSearch(mockFname, mockLname, mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute("students", mockStudents);
		assertEquals(ManageUserController.GET_VIEW_STUDENT_SUBMISSIONS_VIEW, result);
	}
	
	@Test
	public void processStudentSubmissionSearch_lname_returns_URL_manageUsers() {
		// ARRANGE
		String mockFname = "";
		String mockLname = "mockLname";
		List<User> mockStudents = new ArrayList<User>();
		when(mockUserService.findByRoleAndLastNameIgnoreCase("Student", mockLname) ).thenReturn(mockStudents);
		// ACT
		String result = manageUserController.processStudentSubmissionSearch(mockFname, mockLname, mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute("students", mockStudents);
		assertEquals(ManageUserController.GET_VIEW_STUDENT_SUBMISSIONS_VIEW, result);
	}
	
	@Test
	public void processStudentSubmissionSearch_fnameAndLname_returns_URL_manageUsers() {
		// ARRANGE
		String mockFname = "mockFname";
		String mockLname = "mockLname";
		List<User> mockStudents = new ArrayList<User>();
		when(mockUserService.findByRoleAndFirstNameAndLastNameIgnoreCase("Student", mockFname, mockLname)).thenReturn(mockStudents);
		// ACT
		String result = manageUserController.processStudentSubmissionSearch(mockFname, mockLname, mockModel, mockSession);
		// ASSERT
		verify(mockModel, times(1)).addAttribute("students", mockStudents);
		assertEquals(ManageUserController.GET_VIEW_STUDENT_SUBMISSIONS_VIEW, result);
	}
}
