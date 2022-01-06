package com.fdm.PreparationQuizProject.Controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdm.PreparationQuizProject.Model.Question;
import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.QuestionService;
import com.fdm.PreparationQuizProject.Service.UserService;

/**
 * A controller that handles requests related to question creation functionalities
 * 
 * @author Yunheng Zheng
 *
 */
@Controller
public class QuestionController {
	
	static final String GET_CREATE_QUESTION_CONFIRMATION_VIEW = "createQuestionConfirmation";
	static final String GET_CREATE_QUESTION_VIEW = "createQuestion";
	static final String GET_ACCESS_ERROR_VIEW = "accessError";
	static final String SESSION_CURRENT_USER_ATTR = "currentUser";
	static final String MESSAGE_MODEL_ATTR = "message";
	static final String ERROR_MODEL_ATTR = "error";
	static final String REQUIRE_LOGIN_MESSAGE = "You must be logged in first.";
	static final String NO_ACCESS_MESSAGE = "You do not have access to this page";
	
	private UserService userService;
	private QuestionService questionService;

	@Autowired
	public QuestionController(UserService userService, QuestionService questionService) {
		super();
		this.userService = userService;
		this.questionService = questionService;
	}

	/**
	 * Gets the createQuestion page, returns the accessError.jsp if the user is not logged in
	 * 
	 * @param model   used to represent the requestScope
	 * @param session used to represent the sessionScope
	 * @return returns to the createQuestion.jsp to display the manage user page
	 */
	@GetMapping("/createQuestion")        
	public String getCreateQuestionPage(Model model, HttpSession session) {
		if (session.getAttribute(SESSION_CURRENT_USER_ATTR) == null) {
			model.addAttribute(ERROR_MODEL_ATTR, REQUIRE_LOGIN_MESSAGE);
			return GET_ACCESS_ERROR_VIEW;
		}
		
		User currentUser = (User) session.getAttribute(SESSION_CURRENT_USER_ATTR);
		
		if (currentUser.getRole().equals("Student") && currentUser.getStatus().equals("Absent")) {
			model.addAttribute(ERROR_MODEL_ATTR, NO_ACCESS_MESSAGE);
			return GET_ACCESS_ERROR_VIEW;
		}
		
		return GET_CREATE_QUESTION_VIEW;
	}


	/**
	 * Process the creation of short answer question, returns to createQuestionConfirmation after submission
	 * 
	 * @param subject	retrieved from user input
	 * @param category	retrieved from user input
	 * @param format	retrieved from user input
	 * @param name		retrieved from user input
	 * @param mark		retrieved from user input
	 * @param answer	retrieved from user input
	 * @param model  	used to represent the requestScope
	 * @param session 	used to represent the sessionScope
	 * @return returns to createQuestionConfirmation after the question is created
	 */
	@PostMapping("/processCreateShortQuestion")
	public String processCreateShortQuestion(@RequestParam String subject, @RequestParam String category,
			@RequestParam String format, @RequestParam String name, @RequestParam int mark, @RequestParam String answer,
			Model model, HttpSession session) {
		
		User currentUser = (User) session.getAttribute(SESSION_CURRENT_USER_ATTR);
		
		Question question = new Question(subject, category, format, name, mark, answer, null, currentUser);
		
		question = questionService.saveQuestion(question);
		currentUser.addCreatedQuestion(question);
		currentUser = userService.save(currentUser);
		
		session.setAttribute(SESSION_CURRENT_USER_ATTR, currentUser);
		return GET_CREATE_QUESTION_CONFIRMATION_VIEW;
	}

	/**
	 * Process the creation of multiple choice question, returns to createQuestionConfirmation after submission
	 * 
	 * @param subject	retrieved from user input
	 * @param category	retrieved from user input
	 * @param format	retrieved from user input
	 * @param name		retrieved from user input
	 * @param mark		retrieved from user input
	 * @param answerIndex	retrieved from user input
	 * @param choices	retrieved from user input
	 * @param model  	used to represent the requestScope
	 * @param session 	used to represent the sessionScope
	 * @return returns to createQuestionConfirmation after the question is created
	 */
	@PostMapping("/processCreateMCQuestion")
	public String processCreateMCQuestion(@RequestParam String subject, @RequestParam String category,
			@RequestParam String format, @RequestParam String name, @RequestParam int mark, @RequestParam int answerIndex,
			@RequestParam String[] choices, Model model, HttpSession session) {
		
		User currentUser = (User) session.getAttribute(SESSION_CURRENT_USER_ATTR);
		
		String answer = choices[answerIndex];
		List<String> choicesList = Arrays.asList(choices);
		
		Question question = new Question(subject, category, format, name, mark, answer, choicesList, currentUser);
		
		question = questionService.saveQuestion(question);
		currentUser.addCreatedQuestion(question);
		currentUser = userService.save(currentUser);
		
		session.setAttribute(SESSION_CURRENT_USER_ATTR, currentUser);
		return GET_CREATE_QUESTION_CONFIRMATION_VIEW;
	}

}
