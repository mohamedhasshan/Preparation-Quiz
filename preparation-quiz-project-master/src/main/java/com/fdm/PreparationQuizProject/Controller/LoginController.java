package com.fdm.PreparationQuizProject.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.UserService;

@Controller
public class LoginController {

	static final String LOGIN = "login";
	static final String USER = "currentUser";
	static final String PROCESS_LOGIN = "processLogin";
	static final String ERROR_MESSAGE = "message";
	static final String LOGOUT = "logout";

	private UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(LOGIN)
	public String fetchLoginPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute(USER);
		if (user == null) {
			model.addAttribute(USER, new User());
			return LOGIN;
		} else {
			model.addAttribute("error", "You must be logged out first");
			return "accessError";
		}
	}

	@PostMapping(PROCESS_LOGIN)
	public String processLogin(User user, Model model, HttpSession session) {
		User outputUser = userService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
		if (outputUser != null) {
			if(outputUser.isRegistered() && !outputUser.getStatus().equals("Absent")) {
				session.setAttribute(USER, outputUser);
				return "home";
			} else {
				model.addAttribute(ERROR_MESSAGE, "Please wait for an admin to approve your account");
				model.addAttribute(USER, new User());
				return LOGIN;
			}
		} else {
			model.addAttribute(ERROR_MESSAGE, "Invalid Username or Password");
			model.addAttribute(USER, new User());
			return LOGIN;
		}

	}
	
	@GetMapping(LOGOUT)
    public String processLogout(Model model, HttpSession session) {
        session.invalidate();
        return "home";
    }

}
