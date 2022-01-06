package com.fdm.PreparationQuizProject.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.UserService;

/**
 * A controller that handles requests related to the Manage User functionalities
 * 
 * @author Yunheng Zheng
 *
 */
@Controller
public class ManageUserController {

	static final String GET_VIEW_STUDENT_SUBMISSIONS_VIEW = "viewStudentSubmissions";
	static final String GET_REGISTRATION_REQUESTS_VIEW = "registrationRequests";
	static final String GET_MANAGE_USERS_VIEW = "manageUsers";
	static final String GET_UPDATE_STUDENT_VIEW = "updateStudent";
	static final String GET_MANAGE_REGISTRATION_REQUESTS_VIEW = "manageRegistrationRequests";
	static final String GET_PROCESS_REGISTRATION_CONFIRMATION_VIEW = "processRegistrationConfirmation";
	static final String GET_ACCESS_ERROR_VIEW = "accessError";
	static final String SESSION_CURRENT_USER_ATTR = "currentUser";
	static final String MESSAGE_MODEL_ATTR = "message";
	static final String ERROR_MODEL_ATTR = "error";
	static final String REQUIRE_LOGIN_MESSAGE = "You must be logged in first.";
	static final String NO_ACCESS_MESSAGE = "You do not have access to this page";
	static final String[] SALES_STATUS_OPTIONS_STRINGS = { "Pond", "Beached", "Absent" };
	static final String[] ALL_STATUS_OPTIONS_STRINGS = { "Ongoing", "Pond", "Beached", "Absent" };

	private UserService userService;

	@Autowired
	public ManageUserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	/**
	 * Gets the manageUsers page, returns the accessError.jsp if the user is not
	 * logged in or is a student
	 * 
	 * @param model   used to represent the requestScope
	 * @param session used to represent the sessionScope
	 * @return returns to the manageUsers.jsp to display the manage user page
	 */
	@GetMapping("/manageUsers")
	public String getManageUsersPage(Model model, HttpSession session) {
		if (session.getAttribute(SESSION_CURRENT_USER_ATTR) == null) {
			model.addAttribute(ERROR_MODEL_ATTR, REQUIRE_LOGIN_MESSAGE);
			return GET_ACCESS_ERROR_VIEW;
		}

		User currentUser = (User) session.getAttribute(SESSION_CURRENT_USER_ATTR);

		if (currentUser.getRole().equals("Student")) {
			model.addAttribute(ERROR_MODEL_ATTR, NO_ACCESS_MESSAGE);
			return GET_ACCESS_ERROR_VIEW;
		}

		List<User> allStudents = userService.findByRole("Student");
		model.addAttribute("students", allStudents);
		return GET_MANAGE_USERS_VIEW;
	}

	/**
	 * Process the search student functionality, returns the students that match the search
	 * 
	 * @param firstName	retrieved from user input
	 * @param lastName	retrieved from user input
	 * @param model   used to represent the requestScope
	 * @param session used to represent the sessionScope
	 * @return returns to the manageUsers.jsp to display the manage user page
	 */
	@PostMapping("/processManageStudentSearch")
	public String processManageStudentSearch(@RequestParam String firstName, @RequestParam String lastName, Model model,
			HttpSession session) {

		List<User> foundStudents = new ArrayList<User>();

		if (firstName.equals("") && lastName.equals("")) {
			foundStudents = userService.findByRole("Student");
		} else if (firstName.equals("")) {
			foundStudents = userService.findByRoleAndLastNameIgnoreCase("Student", lastName);
		} else if (lastName.equals("")) {
			foundStudents = userService.findByRoleAndFirstNameIgnoreCase("Student", firstName);
		} else {
			foundStudents = userService.findByRoleAndFirstNameAndLastNameIgnoreCase("Student", firstName, lastName);
		}

		model.addAttribute("students", foundStudents);
		return GET_MANAGE_USERS_VIEW;

	}

	/**
	 * Gets the updateStudent page, returns the accessError.jsp if the user is not
	 * logged in or is a student
	 * 
	 * @param id	represents the id of the student to be edited
	 * @param model   used to represent the requestScope
	 * @param session used to represent the sessionScope
	 * @return returns to the updateStudent.jsp to edit the student details
	 */
	@PostMapping("/editStudent")
	public String getEditStudentPage(@RequestParam int id, Model model, HttpSession session) {
		if (session.getAttribute(SESSION_CURRENT_USER_ATTR) == null) {
			model.addAttribute(ERROR_MODEL_ATTR, REQUIRE_LOGIN_MESSAGE);
			return GET_ACCESS_ERROR_VIEW;
		}

		User currentUser = (User) session.getAttribute(SESSION_CURRENT_USER_ATTR);

		if (currentUser.getRole().equals("Student")) {
			model.addAttribute(ERROR_MODEL_ATTR, NO_ACCESS_MESSAGE);
			return GET_ACCESS_ERROR_VIEW;
		}

		User targetStudent = userService.findById(id);

		model.addAttribute("student", targetStudent);
		model.addAttribute("allStatus", ALL_STATUS_OPTIONS_STRINGS);
		model.addAttribute("salesStatusOptions", SALES_STATUS_OPTIONS_STRINGS);
		return GET_UPDATE_STUDENT_VIEW;

	}

	/**
	 * Process the update of student details, returns to the same page with the
	 * updated details or an error
	 * 
	 * @param id	represents the id of the student to be edited
	 * @param firstName	retrieved from user input
	 * @param lastName	retrieved from user input
	 * @param username	retrieved from user input
	 * @param password	retrieved from user input
	 * @param email		retrieved from user input
	 * @param status	retrieved from user input
	 * @param model   used to represent the requestScope
	 * @param session used to represent the sessionScope
	 * @return returns to the updateStudent.jsp to show the error or the updated details
	 */
	@PostMapping("/processUserUpdate")
	public String processUserUpdate(@RequestParam int id, @RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String username, @RequestParam String password, @RequestParam String email,
			@RequestParam String status, Model model, HttpSession session) {

		User targetStudent = userService.findById(id);

		if (!username.equals(targetStudent.getUsername()) && userService.findByUsername(username) != null) {
			model.addAttribute(ERROR_MODEL_ATTR, "The username already exists");
		} else if (username.length() < 5 || password.length() < 5) {
			model.addAttribute(ERROR_MODEL_ATTR, "Username and password should be at least 5 characters");
		} else if (!email.contains("@") || !email.contains(".com")) {
			model.addAttribute(ERROR_MODEL_ATTR, "The email you entered was invalid");
		} else {

			targetStudent.setFirstName(firstName);
			targetStudent.setLastName(lastName);
			targetStudent.setUsername(username);
			targetStudent.setPassword(password);
			targetStudent.setStatus(status);
			targetStudent.setEmail(email);

			targetStudent = userService.save(targetStudent);
			model.addAttribute(MESSAGE_MODEL_ATTR, "You have successfully updated the student");
		}

		model.addAttribute("student", targetStudent);
		model.addAttribute("allStatus", ALL_STATUS_OPTIONS_STRINGS);
		model.addAttribute("salesStatusOptions", SALES_STATUS_OPTIONS_STRINGS);
		return GET_UPDATE_STUDENT_VIEW;

	}

	/**
	 * Gets the manageRegistrationRequests page, returns the accessError.jsp if the user is not
	 * logged in or is a student
	 * 
	 * @param model   used to represent the requestScope
	 * @param session used to represent the sessionScope
	 * @return returns to the manageRegistrationRequests.jsp to show the registration requests
	 */
	@GetMapping("/manageRegistrationRequests")
	public String getManageRegistrationRequests(Model model, HttpSession session) {
		if (session.getAttribute(SESSION_CURRENT_USER_ATTR) == null) {
			model.addAttribute(ERROR_MODEL_ATTR, REQUIRE_LOGIN_MESSAGE);
			return GET_ACCESS_ERROR_VIEW;
		}

		User currentUser = (User) session.getAttribute(SESSION_CURRENT_USER_ATTR);

		if (currentUser.getRole().equals("Student")) {
			model.addAttribute(ERROR_MODEL_ATTR, NO_ACCESS_MESSAGE);
			return GET_ACCESS_ERROR_VIEW;
		} else if (currentUser.getRole().equals("Trainer")) {
			List<User> registrationRequests = userService.findByRoleAndIsRegistered("Trainer", false);
			model.addAttribute(GET_REGISTRATION_REQUESTS_VIEW, registrationRequests);
			return GET_MANAGE_REGISTRATION_REQUESTS_VIEW;
		} else {
			List<User> registrationRequests = userService.findByRoleAndIsRegistered("Sales", false);
			model.addAttribute(GET_REGISTRATION_REQUESTS_VIEW, registrationRequests);
			return GET_MANAGE_REGISTRATION_REQUESTS_VIEW;
		}
	}

	
	/**
	 * Process the approval of an registration request, returns to processRegistrationConfirmation
	 * 
	 * @param id	represents the id of the user to be registered
	 * @param model   used to represent the requestScope
	 * @return returns to processRegistrationConfirmation.jsp to confirm the process
	 */
	@PostMapping("/processApproveRegistration")
	public String processApproveRegistration(@RequestParam int id, Model model) {
		
		User targetUser = userService.findById(id);
		
		targetUser.setRegistered(true);
		userService.save(targetUser);
		
		model.addAttribute(MESSAGE_MODEL_ATTR, "You have approved the registration request");
		return GET_PROCESS_REGISTRATION_CONFIRMATION_VIEW;
	}
	
	/**
	 * Process the denial of an registration request, returns to processRegistrationConfirmation
	 * 
	 * @param id	represents the id of the user to be registered
	 * @param model   used to represent the requestScope
	 * @return returns to processRegistrationConfirmation.jsp to confirm the process
	 */
	@PostMapping("/processDenyRegistration")
	public String processDenyRegistration(@RequestParam int id, Model model) {
		
		User targetUser = userService.findById(id);
		
		userService.deleteUser(targetUser);
		
		model.addAttribute(MESSAGE_MODEL_ATTR, "You have denied the registration request");
		return GET_PROCESS_REGISTRATION_CONFIRMATION_VIEW;
	}
	
	/**
	 * Gets the viewStudentSubmissions page, returns the accessError.jsp if the user is not
	 * logged in or is a student
	 * 
	 * @param model   used to represent the requestScope
	 * @param session used to represent the sessionScope
	 * @return returns to the viewStudentSubmissions.jsp to display the students and view their submissions
	 */
	@GetMapping("/viewStudentSubmissions")
	public String getViewStudentSubmissionsPage(Model model, HttpSession session) {
		if (session.getAttribute(SESSION_CURRENT_USER_ATTR) == null) {
			model.addAttribute(ERROR_MODEL_ATTR, REQUIRE_LOGIN_MESSAGE);
			return GET_ACCESS_ERROR_VIEW;
		}

		User currentUser = (User) session.getAttribute(SESSION_CURRENT_USER_ATTR);

		if (currentUser.getRole().equals("Student")) {
			model.addAttribute(ERROR_MODEL_ATTR, NO_ACCESS_MESSAGE);
			return GET_ACCESS_ERROR_VIEW;
		}

		List<User> allStudents = userService.findByRole("Student");
		model.addAttribute("students", allStudents);
		return GET_VIEW_STUDENT_SUBMISSIONS_VIEW;
	}
	
	/**
	 * Process the search student functionality, returns the students that match the search
	 * 
	 * @param firstName	retrieved from user input
	 * @param lastName	retrieved from user input
	 * @param model   used to represent the requestScope
	 * @param session used to represent the sessionScope
	 * @return returns to the viewStudentSubmissions.jsp with the students that match the search 
	 */
	@PostMapping("/processStudentSubmissionSearch")
	public String processStudentSubmissionSearch(@RequestParam String firstName, @RequestParam String lastName, Model model,
			HttpSession session) {

		List<User> foundStudents = new ArrayList<User>();

		if (firstName.equals("") && lastName.equals("")) {
			foundStudents = userService.findByRole("Student");
		} else if (firstName.equals("")) {
			foundStudents = userService.findByRoleAndLastNameIgnoreCase("Student", lastName);
		} else if (lastName.equals("")) {
			foundStudents = userService.findByRoleAndFirstNameIgnoreCase("Student", firstName);
		} else {
			foundStudents = userService.findByRoleAndFirstNameAndLastNameIgnoreCase("Student", firstName, lastName);
		}

		model.addAttribute("students", foundStudents);
		return GET_VIEW_STUDENT_SUBMISSIONS_VIEW;

	}
	
}
