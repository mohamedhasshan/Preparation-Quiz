package com.fdm.PreparationQuizProject.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.UserService;

@Controller
public class UserController {

	static final String INVALID_INPUT_MSG = "Invalid Input";
	static final String DETAILS_UPDATED_MSG = "Your details have been updated";
	static final String MESSAGE = "message";
	static final String USERNAME_TAKEN_MSG = " is already taken. Please choose another username";
	static final String ERROR_MESSAGE = "errorMessage";
	static final String USER_ATTR = "currentUser";
	static final String UPDATE_USER_ATTR = "bindUpdateUser";
	static final String UPDATE_USER_VIEW = "updateUser";
	static final String HOME = "home";
	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String getHomePage() {
		return HOME;
	}

	@GetMapping("/getHome")
	public String getHome() {
		return HOME;
	}

	/**
	 * when user click on Profile show Profile Page with username password and email
	 * 
	 * @param model
	 * @return updateUser.jsp
	 */
	@GetMapping("/userUpdate")
	public String getUserUpdate(Model model) {
		User updatedUser = new User();
		model.addAttribute(UPDATE_USER_ATTR, updatedUser);
		return UPDATE_USER_VIEW;
	}

	/**
	 * returns a spring form object. check if username password email is valid
	 * before processing
	 * 
	 * @param updateUserInfo
	 * @param session        updates activeUser
	 * @param model
	 * @return saved user detail and return to home.jsp
	 */
	@PostMapping("/updateUserInfo")
	public String processUserInfoUpdate(User updateUserInfo, HttpSession session, Model model) {
		User currentUser = (User) session.getAttribute(USER_ATTR);
		User userToBeUpdated = new User();
		if (userService.checkUsername(updateUserInfo)==true &&
				userService.checkPassword(updateUserInfo)==true &&
				userService.checkEmail(updateUserInfo)==true) {
			currentUser.setUsername(updateUserInfo.getUsername());
			currentUser.setPassword(updateUserInfo.getPassword());
			currentUser.setEmail(updateUserInfo.getEmail());
			userService.checkAndSave(currentUser);
			if (currentUser.equals(null)) {
				model.addAttribute(UPDATE_USER_ATTR, userToBeUpdated);
				model.addAttribute(ERROR_MESSAGE,
						updateUserInfo.getUsername() + USERNAME_TAKEN_MSG);
				return UPDATE_USER_VIEW;
			} else {
				session.setAttribute(USER_ATTR, currentUser);
				model.addAttribute(MESSAGE, DETAILS_UPDATED_MSG);
				return HOME;
			}
		}
		model.addAttribute(UPDATE_USER_ATTR, userToBeUpdated);
		model.addAttribute(ERROR_MESSAGE, INVALID_INPUT_MSG);
		return UPDATE_USER_VIEW;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
