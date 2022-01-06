package com.fdm.PreparationQuizProject.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.UserService;

@Controller
public class RegisterController {

	static final String HOME = "home";
	static final String USERNAME_TAKEN_MSG = "Username is already used";
	static final String TRAINERSALES_REG_MSG = "Hi Trainer/Sales, registeration successful pending registeration review";
	static final String INVALID_USERNAME_PASSWORD_MSG = "Username and password should be at least 5 characters!";
	static final String INVALID_EMAIL_MSG = "Oops, looks like you entered an invalid email";
	static final String COMPLETED_REG_MSG = "Hi Student, You have completed your registeration!";
	static final String MESSAGE = "message";
	static final String STUDENT_ATTR = "Student";
	static final String LOGOUT_MESSAGE = "You need to logout to access this page";
	static final String PROCESS_REGISTER = "processRegister";
	static final String REGISTER = "register";
	static final String ERROR_MESSAGE = "errorMessage";
	static final String USER = "currentUser";

	private UserService userService;

	public RegisterController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping(REGISTER)
	public String fetchRegisterPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute(USER);
		if (user == null) {
			model.addAttribute(USER, new User());
			return REGISTER;
		} else {
			model.addAttribute(ERROR_MESSAGE, LOGOUT_MESSAGE);
			return HOME;
		}
	}

	@PostMapping(PROCESS_REGISTER)
	public String processRegister(User user, Model model, HttpSession session) {
		if (userService.checkUsernameAndPassword(user) == false) {
			model.addAttribute(ERROR_MESSAGE, INVALID_USERNAME_PASSWORD_MSG);
			model.addAttribute(USER, user);
			return REGISTER;
		}
		
		if (userService.checkEmail(user) == false) {
			model.addAttribute(ERROR_MESSAGE, INVALID_EMAIL_MSG);
			model.addAttribute(USER, user);
			return REGISTER;
		}
		if (user.getRole().equals(STUDENT_ATTR)) {
			user.setRegistered(true);
		} else {
			user.setRegistered(false);
		}	
		User outputUser = userService.registerUser(user);
        if (outputUser !=null && outputUser.isRegistered()==true) {
            model.addAttribute(USER, outputUser);
            model.addAttribute(MESSAGE, COMPLETED_REG_MSG);
            return HOME ;
        } else if (outputUser !=null && outputUser.isRegistered()==false) {
            model.addAttribute(USER, outputUser);
            model.addAttribute(MESSAGE, TRAINERSALES_REG_MSG);
            return HOME ;
        } else {
        	model.addAttribute(USER, user);
            model.addAttribute(ERROR_MESSAGE, USERNAME_TAKEN_MSG);
            return REGISTER;
        }

	}
}
