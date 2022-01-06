package com.fdm.PreparationQuizProject.Controller;



import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdm.PreparationQuizProject.Model.Question;
import com.fdm.PreparationQuizProject.Model.QuestionSubmission;
import com.fdm.PreparationQuizProject.Model.QuizSubmission;
import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.QuestionSubmissionService;
import com.fdm.PreparationQuizProject.Service.QuizSubmissionService;
import com.fdm.PreparationQuizProject.Service.UserService;

/***
 * 
 * @author Billy
 *
 */
@Controller
public class QuizSubmissionController {
	static final String VIEW_MY_SUBMISSIONS_VIEW = "viewMySubmissions";
	static final String SUBMISSIONS_TO_PRINT_ATTR = "submissionsToPrint";
	static final String CURRENT_USER_ATTR = "currentUser";
	static final String GET_MY_SUBMISSIONS_URL = "/getMySubmissions";
	static final String REVIEW_QUIZ_SUBMISSION_VIEW = "reviewQuizSubmission";
	static final String QUIZ_TO_REVIEW_ATTR = "quizToReview";
	static final String GET_REVIEW_QUIZ_URL = "/getReviewQuiz";
	static final String HOME_VIEW = "home";
	static final String PROCESS_MARK_QUIZ_URL = "/processMarkQuiz";
	static final String MARK_QUIZ_VIEW = "markQuiz";
	static final String QUIZ_MARKING_ATTR = "quizMarking";
	static final String GET_MARK_QUIZ_URL = "/getMarkQuiz";
	private QuizSubmissionService quizSubmissionService;
	private UserService userService;
	private QuestionSubmissionService questionSubmissionService;

	/**Autowired constructor
	 * @param quizSubmissionService service for QuizSubmission
	 * @param userService service for User
	 * @param questionSubmissionService service for QuestionSubmission
	 */
	@Autowired
	public QuizSubmissionController(QuizSubmissionService quizSubmissionService, UserService userService, QuestionSubmissionService questionSubmissionService
) {
		super();
		this.quizSubmissionService = quizSubmissionService;
		this.userService = userService;
		this.questionSubmissionService = questionSubmissionService;
	}
	
	/**Post Mapping,
	 * Getting a <QuizSubmission> id and retrive it from database,
	 * then pass it to markQuiz.jsp to show and enable marking
	 * @param id quizSubmission id for retrieving from database by findById
	 * @param model for adding quizSubmission got from database to quizMarking attribute
	 * @return "markQuiz"
	 */
	@PostMapping(GET_MARK_QUIZ_URL)
	public String getMarkQuizPage(@RequestParam int id, Model model){
		QuizSubmission quizToMark = quizSubmissionService.findById(id);
		quizToMark.update();
		model.addAttribute(QUIZ_MARKING_ATTR,quizToMark);

		return MARK_QUIZ_VIEW;
	}
	
	
	/**Post Mapping,
	 * Passing a Marked <QuizSubmission> from frontEnd,
	 * Auto Marking the MC answers,
	 * save it to the database afterward.
	 * @param quizMarked the quiz marked by trainer/sales passed back from front end
	 * @return "home"
	 */
	@PostMapping(PROCESS_MARK_QUIZ_URL)
	public String processMarkQuizPage(QuizSubmission quizMarked){
		List<QuestionSubmission> questionSubmissions= quizMarked.getQuestionSubmissions();
		int studentMark = quizMarked.getStudentMark();
		for(QuestionSubmission questionSubmission: questionSubmissions) {
			Question question = questionSubmission.getQuestion();
			String format = question.getFormat();
			if(format.equals("Short Answer")) {
					int markGot = questionSubmission.getStudentMark();
					studentMark += markGot;
			}
			questionSubmission = questionSubmissionService.saveQuestionSubmission(questionSubmission);
		}
		quizMarked.setStudentMark(studentMark);
		quizMarked.setMarked(true);
		quizMarked = quizSubmissionService.save(quizMarked);
		User userToUpdate = quizMarked.getStudent();
		userToUpdate.updateSubmittedQuiz(quizMarked);
		userService.save(userToUpdate);
		
		return HOME_VIEW;
	}
	
	/**Post Mapping,
	 * Passing a <QuizSubmission> id into the function,
	 * Retrieve the <QuizSubmission> from the database by the id,
	 * add it to the quizToReview attribute for displaying in reviewQuizSubmission.jsp
	 * @param id <QuizSubmission> id for retrieving the <QuizSubmission> from database by findById
	 * @param model quizToReview attribute for adding the retrieved <QuizSubmission> for displaying in reviewQuizSubmission.jsp
	 * @return "reviewQuizSubmission"
	 */
	@PostMapping(GET_REVIEW_QUIZ_URL)
	public String getReviewQuizPage(@RequestParam int id, Model model){
		QuizSubmission quizToReview = quizSubmissionService.findById(id);
		quizToReview.update();
		model.addAttribute(QUIZ_TO_REVIEW_ATTR,quizToReview);
		
		return REVIEW_QUIZ_SUBMISSION_VIEW;
	}
	
	/**Get Mapping,
	 * Getting current user attribute from session,
	 * get all the related <QuizSubmission> from the database by that
	 * add all the related <QuizSubmission> to submissionToPrint model to display in viewMySubmission.jsp
	 * @param session get the <user> by getAttribute from "currentUser"
	 * @param model add the related <QuizSubmission> of current user to the submissionToPrint attribute
	 * @return "viewMySubmission"
	 */
	@GetMapping(GET_MY_SUBMISSIONS_URL)
	public String getMySubmissionsPage(HttpSession session, Model model) {
		User user = (User) session.getAttribute(CURRENT_USER_ATTR);
		int id = user.getId();
		user = userService.findById(id);
		List<QuizSubmission> quizSubmissions = quizSubmissionService.findByStudent(user);
		Collections.reverse(quizSubmissions);
		model.addAttribute(SUBMISSIONS_TO_PRINT_ATTR, quizSubmissions);
		
		return VIEW_MY_SUBMISSIONS_VIEW;
	}
	
	/**PostMapping
	 * <User> id is passed in,
	 * Getting user from database by findById,
	 * get all the related <QuizSubmission> from the database by findByStudent(user)
	 * add all the related <QuizSubmission> to submissionToPrint model to display in viewMySubmission.jsp
	 * @param id <User> id for retrieving <User> from database by findById
	 * @param model add the related <QuizSubmission> of the user retrieved to the submissionToPrint attribute
	 * @return "viewMySubmission"
	 */
	@PostMapping("/getStudentSubmissions")
	public String getStudentSubmissionsPage(@RequestParam int id, Model model) {
		User user = userService.findById(id);
		List<QuizSubmission> quizSubmissions = quizSubmissionService.findByStudent(user);
		Collections.reverse(quizSubmissions);
		model.addAttribute(SUBMISSIONS_TO_PRINT_ATTR, quizSubmissions);

		return VIEW_MY_SUBMISSIONS_VIEW;
	}
	
	
	
}
