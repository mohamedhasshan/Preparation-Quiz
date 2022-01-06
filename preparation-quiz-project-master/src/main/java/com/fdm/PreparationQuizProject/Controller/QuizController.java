package com.fdm.PreparationQuizProject.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdm.PreparationQuizProject.Model.Question;
import com.fdm.PreparationQuizProject.Model.QuestionSubmission;
import com.fdm.PreparationQuizProject.Model.Quiz;
import com.fdm.PreparationQuizProject.Model.QuizSubmission;
import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.QuestionService;
import com.fdm.PreparationQuizProject.Service.QuestionSubmissionService;
import com.fdm.PreparationQuizProject.Service.QuizService;
import com.fdm.PreparationQuizProject.Service.QuizSubmissionService;
import com.fdm.PreparationQuizProject.Service.UserService;

/**
 * @author Nicholas, Billy
 *
 */
@Controller
public class QuizController {
	
	protected static final String VIEW_FINISHED_QUIZZES_URL = "/viewFinishedQuizzes";
	protected static final String VIEW_FINISHED_QUIZZES_VIEW = "viewFinishedQuizzes";
	protected static final String FILTER_FINISHED_QUIZZES_URL = "/filterFinishedQuizzes";
	protected static final String FINISHED_QUIZ_DETAILS_VIEW = "finishedQuizDetails";
	protected static final String FINISHED_QUIZ_MODEL_ATTR = "finishedQuiz";
	protected static final String GET_FINISHED_QUIZ_URL = "/getFinishedQuiz";
	protected static final String CANCEL_AND_RETURN_URL = "/cancelAndReturn";
	protected static final String PROCESS_SORT_VIEW_QUIZ_URL = "/processSortViewQuiz";
	protected static final String VIEW_QUIZZES_VIEW = "viewQuizzes";
	protected static final String VIEW_QUIZZES_URL = "/viewQuizzes";
	protected static final String REDIRECT_MANAGE_QUIZZES_URL = "redirect:/manageQuizzes";
	protected static final String DELETE_QUIZ_URL = "/deleteQuiz";
	protected static final String DELETE_QUIZ_CONFIRMATION_VIEW = "deleteQuizConfirmation";
	protected static final String QUIZ_TO_DELETE_SESSION_ATTR = "quizToDelete";
	protected static final String PROCESS_DELETE_QUIZ_URL = "/processDeleteQuiz";
	protected static final String PROCESS_EDIT_QUIZ_URL = "/processEditQuiz";
	protected static final String PROCESS_SORT_MANAGE_QUIZ_URL = "/processSortManageQuiz";
	protected static final String MANAGE_QUIZZES_VIEW = "manageQuizzes";
	protected static final String ALLOWED_LIST_SESSION_ATTR = "allowedList";
	protected static final String MANAGE_QUIZZES_URL = "/manageQuizzes";
	protected static final String PROCESS_REMOVE_QUESTION_FROM_QUIZ_URL = "/processRemoveQuestionFromQuiz";
	protected static final String PROCESS_ADD_QUESTION_TO_QUIZ_URL = "/processAddQuestionToQuiz";
	protected static final String REDIRECT_CREATE_QUIZ_URL = "redirect:/createQuiz";
	protected static final String PROCESS_SORT_CREATE_QUIZ_URL = "/processSortCreateQuiz";
	protected static final String QUIZ_SAVED_VIEW = "quizSaved";
	protected static final String QUIZ_LIST_SESSION_ATTR = "quizList";
	protected static final String QUIZ_TO_EDIT_SESSION_ATTR = "quizToEdit";
	protected static final String PROCESS_QUIZ_CREATION_URL = "/processQuizCreation";
	protected static final String CREATE_QUIZ_VIEW = "createQuiz";
	protected static final String ADDED_QUESTIONS_LIST_SESSION_ATTR = "addedQuestionsList";
	protected static final String SORTED_QUESTIONS_SESSION_ATTR = "sortedQuestions";
	protected static final String SORT_QUESTION_MODEL_ATTR = "sortQuestion";
	protected static final String CREATE_QUIZ_URL = "/createQuiz";
	protected static final String HOME_VIEW = "home";
	protected static final String MESSAGE_MODEL_ATTR = "message";
	protected static final String PROCESS_SUBMIT_QUIZ_URL = "/processSubmitQuiz";
	protected static final String TAKE_QUIZ_VIEW = "takeQuiz";
	protected static final String QUIZ_TAKING_MODEL_ATTR = "quizTaking";
	protected static final String CURRENT_USER_SESSION_ATTR = "currentUser";
	protected static final String GET_TAKE_QUIZ_URL = "/getTakeQuiz";
	private QuestionService questionService;
	private QuestionSubmissionService questionSubmissionService;
	private QuizService quizService;
	private QuizSubmissionService quizSubmissionService;
	private UserService userService;

	private Log log= LogFactory.getLog(QuizController.class);
	
	@Autowired
	public QuizController(QuestionService questionService, QuizService quizService, QuizSubmissionService quizSubmissionService, UserService userService, QuestionSubmissionService questionSubmissionService) {
		super();
		this.questionService = questionService;
		this.quizService = quizService;
		this.quizSubmissionService = quizSubmissionService;
		this.userService = userService;
		this.questionSubmissionService = questionSubmissionService;
	}
	
	/**Post Mapping,
	 * Passing in a <Quiz> id for retrieving the <Quiz> from database by findById
	 * Create a <QuizSubmission> and passing in the details get from the <Quiz>
	 * the whole <QuizSubmission> will then pass to the quizTaking attribute,
	 * the attribute will pass to takeQuiz.jsp for display
	 * @param quizId <Quiz> id for retrieving the <Quiz> from database which will be used to create the <QuizSubmission>
	 * @param model attribute quizTaking for adding the <QuizSubmission> generated to display in takeQuiz.jsp
	 * @param session attribute currentUser is used for getting the <User> user of current user
	 * @return "takeQuiz"
	 */
	@PostMapping(GET_TAKE_QUIZ_URL)
	public String getTakeQuizPage(@RequestParam int quizId, Model model, HttpSession session){
		Quiz quizToTake = quizService.findById(quizId);
		quizToTake.update();
		String name = quizToTake.getName();
		int numOfQuestions = quizToTake.getNumOfQuestions();
		int totalMark = quizToTake.getTotalMark();
		List<String> subjects = quizToTake.getSubjects();
		List<String> categories = quizToTake.getCategories();
		List<String> questionFormats = quizToTake.getQuestionFormats();
		List<Question> questions= quizToTake.getQuestions();
		log.info(questions);
		
		List<QuestionSubmission> questionSubmissions= new ArrayList<>();
		for(int i=0; i<questions.size();i++) {
			Question question = questions.get(i);
			QuestionSubmission questionSubmission = new QuestionSubmission();
			questionSubmission.setQuestion(question);
			questionSubmissions.add(questionSubmission);
		}
		User student = (User) session.getAttribute(CURRENT_USER_SESSION_ATTR);
		log.info(student);
		
		QuizSubmission quizSubmission = new QuizSubmission();
		quizSubmission.setQuestionSubmissions(questionSubmissions);
		quizSubmission.setStudent(student);
		quizSubmission.setName(name);
		quizSubmission.setNumOfQuestions(numOfQuestions);
		quizSubmission.setTotalMark(totalMark);
		quizSubmission.setSubjects(subjects);
		quizSubmission.setCategories(categories);
		quizSubmission.setQuestionFormats(questionFormats);
		model.addAttribute(QUIZ_TAKING_MODEL_ATTR,quizSubmission);
		
		return TAKE_QUIZ_VIEW;
	}

	/**Post Mapping,
	 * <QuizSubmission> submittedQuiz is pass back with input,
	 * Mutiple choice questions will be auto marked and calculated with marks here.
	 * The <QuizSubmission> submittedQuiz with updated marking of MC questions is then save to the database
	 * User will also be updated in local and database with the new <QuizSubmission> submittedQuiz
	 * @param submittedQuiz the <QuizSubmission> pass back from the takeQuiz.jsp with input from student
	 * @param model attribute message for adding "Quiz successfully submitted!" message to display in "home"
	 * @param session attribute currentUser for updating the user
	 * @return "home"
	 */
	@PostMapping(PROCESS_SUBMIT_QUIZ_URL)
	public String processSubmitQuizPage(QuizSubmission submittedQuiz, Model model,HttpSession session){
		log.info("entering processSubmitQuiz");
		List<QuestionSubmission> questionSubmissions= submittedQuiz.getQuestionSubmissions();
		
		int studentMark = 0;
		for(QuestionSubmission questionSubmission: questionSubmissions) {
			Question question = questionSubmission.getQuestion();
			String format = question.getFormat();
			log.info(format);
			if(format.equals("Multiple Choice")) {
				String studentAnswer = questionSubmission.getStudentAnswer();
				String correctAnswer = question.getAnswer();
				if(studentAnswer!=null)
				if(studentAnswer.equals(correctAnswer)) {
					int markGot = question.getMark();
					questionSubmission.setStudentMark(markGot);
					studentMark += markGot;
				}
			}
			questionSubmission = questionSubmissionService.saveQuestionSubmission(questionSubmission);
		}
		
		log.info("UpdateMarksLoop");
		
		submittedQuiz.setQuestionSubmissions(questionSubmissions);
		submittedQuiz.setStudentMark(studentMark);
		submittedQuiz.setMarked(false);
		User userToUpdate = submittedQuiz.getStudent();
		submittedQuiz = quizSubmissionService.save(submittedQuiz);
		int id = userToUpdate.getId();
		userToUpdate = userService.findById(id);
		userToUpdate.addSubmittedQuiz(submittedQuiz);
		userToUpdate = userService.updateUser(userToUpdate);
		session.setAttribute(CURRENT_USER_SESSION_ATTR, userToUpdate);
		model.addAttribute(MESSAGE_MODEL_ATTR, "Quiz successfully submited!");
		return HOME_VIEW;
	}
	
	
	/**
	 * returns a spring form, showing sortQuestions, addedQuestions and input text box for name
	 * sortQuestions are the available questions for adding to quiz, under filter conditions.
	 * Ensures that for editing quizzes, available questions do have a duplicate of existing questions in the quiz
	 * 
	 * used for both creating a new quiz as well as updating old quizzes
	 * 
	 * @param model
	 * @param session
	 * @return 
	 * createQuiz view is returned, where questions can be added or removed from the quiz.
	 */
	@GetMapping(CREATE_QUIZ_URL)
	public String createQuiz(Model model, HttpSession session) {
		Question question= new Question();
		model.addAttribute(SORT_QUESTION_MODEL_ATTR, question);
		
		if (session.getAttribute(SORTED_QUESTIONS_SESSION_ATTR)==null) {
			List<Question> allQuestions= questionService.findAllQuestion();
			session.setAttribute(SORTED_QUESTIONS_SESSION_ATTR, allQuestions);
		}
		
		if (!(session.getAttribute(ADDED_QUESTIONS_LIST_SESSION_ATTR)==null)) {
			List<Question> addedQuestions=(List<Question>) session.getAttribute(ADDED_QUESTIONS_LIST_SESSION_ATTR);
			List<Question> sortedQuestions=(List<Question>) session.getAttribute(SORTED_QUESTIONS_SESSION_ATTR);			
			List<Integer> listOfQuestionId= new ArrayList<Integer>();
			for (Question q: addedQuestions) {
				int id= q.getId();
				listOfQuestionId.add(id);
			}
			
			List<Question> overlappingQuestions = new ArrayList<Question>();
			for (Question q: sortedQuestions) {
				int id= q.getId();
				if (listOfQuestionId.contains(id)) {
					overlappingQuestions.add(q);
				}
			}
			
			for(Question q: overlappingQuestions) {
				sortedQuestions.remove(q);
			}
			session.setAttribute(SORTED_QUESTIONS_SESSION_ATTR, sortedQuestions);
			
		}
		
		return CREATE_QUIZ_VIEW;
	}
	
	/**
	 * Checks if it is creating a new quiz or editing an existing quiz
	 * 
	 * Saves a quiz with name and added questions. Quiz must have a name for it to save
	 * 
	 * @param name
	 * @param session
	 * @return
	 */
	@PostMapping(PROCESS_QUIZ_CREATION_URL)
	public String processQuizCreation(@RequestParam String name, HttpSession session) {
		User sessionUser= (User) session.getAttribute(CURRENT_USER_SESSION_ATTR);
		String quizName= name;
		List<Question> addedQuestions= (List<Question>) session.getAttribute(ADDED_QUESTIONS_LIST_SESSION_ATTR);
		Quiz sessionQuiz;
		if (session.getAttribute(QUIZ_TO_EDIT_SESSION_ATTR)==null) {
			sessionQuiz= new Quiz(quizName, sessionUser, addedQuestions);
			}
		else {
			sessionQuiz=(Quiz) session.getAttribute(QUIZ_TO_EDIT_SESSION_ATTR);
			sessionQuiz.setName(name);
			sessionQuiz.setQuestions(addedQuestions);
		}
		sessionQuiz.update();
		quizService.save(sessionQuiz);
		log.info(sessionQuiz +"has been saved");
		session.removeAttribute(SORTED_QUESTIONS_SESSION_ATTR);
		session.removeAttribute(ADDED_QUESTIONS_LIST_SESSION_ATTR);
		session.removeAttribute(QUIZ_TO_EDIT_SESSION_ATTR);
		session.removeAttribute(QUIZ_LIST_SESSION_ATTR);
		return QUIZ_SAVED_VIEW;
	}
	
	/**
	 * Filters the available questions based on selected filter_checkboxes. Also ensures that questions already added are not shown.
	 * 
	 * @param returnedQuestion
	 * @param session
	 * @return redirect to /createQuiz
	 */
	@PostMapping(PROCESS_SORT_CREATE_QUIZ_URL)
	public String filterQuestionsInCreateQuiz(Question returnedQuestion, HttpSession session) {
		
		String subject=returnedQuestion.getSubject();
		String category= returnedQuestion.getCategory();
		String format= returnedQuestion.getFormat();
		List<Question> sortedQuestions;
		
		if(!returnedQuestion.getSubject().equals("ALL")
			&& !returnedQuestion.getCategory().equals("ALL")
			&& returnedQuestion.getFormat().equals("ALL"))
		{
			sortedQuestions= questionService.findBySubjectAndCategory(subject, category);
		}
		
		else if(!returnedQuestion.getSubject().equals("ALL")
				&& returnedQuestion.getCategory().equals("ALL")
				&& !returnedQuestion.getFormat().equals("ALL"))
		{
			sortedQuestions= questionService.findBySubjectAndFormat(subject, format);
		}
		
		else if(returnedQuestion.getSubject().equals("ALL")
				&& !returnedQuestion.getCategory().equals("ALL")
				&& !returnedQuestion.getFormat().equals("ALL"))
		{
			sortedQuestions= questionService.findByCategoryAndFormat(category, format);
		}
		
		else if(!returnedQuestion.getSubject().equals("ALL")
				&& returnedQuestion.getCategory().equals("ALL")
				&& returnedQuestion.getFormat().equals("ALL"))
		{
			sortedQuestions= questionService.findBySubject(subject);
		}
		
		else if(returnedQuestion.getSubject().equals("ALL")
				&& !returnedQuestion.getCategory().equals("ALL")
				&& returnedQuestion.getFormat().equals("ALL"))
		{
			sortedQuestions= questionService.findByCategory(category);
		}
		
		else if(returnedQuestion.getSubject().equals("ALL")
				&& returnedQuestion.getCategory().equals("ALL")
				&& !returnedQuestion.getFormat().equals("ALL"))
		{
			sortedQuestions= questionService.findByFormat(format);
		}
		
		else if(!returnedQuestion.getSubject().equals("ALL")
				&& !returnedQuestion.getCategory().equals("ALL")
				&& !returnedQuestion.getFormat().equals("ALL")) 
		{
			sortedQuestions= questionService.findBySubjectAndCategoryAndFormat(subject, category, format);
		}
		
		else
		{
			sortedQuestions= questionService.findAllQuestion();
		}
		
		if (!(session.getAttribute(ADDED_QUESTIONS_LIST_SESSION_ATTR)==null)) {
			List<Question> addedQuestions=(List<Question>) session.getAttribute(ADDED_QUESTIONS_LIST_SESSION_ATTR);
			
			List<Integer> listOfQuestionId= new ArrayList<Integer>();
			for (Question question: addedQuestions) {
				int id= question.getId();
				listOfQuestionId.add(id);
			}
			
			List<Question> overlappingQuestions = new ArrayList<Question>();
			for (Question question: sortedQuestions) {
				int id= question.getId();
				if (listOfQuestionId.contains(id)) {
					overlappingQuestions.add(question);
				}
			}
			
			for(Question question: overlappingQuestions) {
				sortedQuestions.remove(question);
			}
			
		}
		session.setAttribute(SORTED_QUESTIONS_SESSION_ATTR, sortedQuestions);
		
		return REDIRECT_CREATE_QUIZ_URL;
	}
	
	/**
	 * Adds question to quiz. removes question from available questions list and adds it to added questions list
	 * 
	 * @param id
	 * @param session
	 * @return redirect to /createQuiz
	 */
	@PostMapping(PROCESS_ADD_QUESTION_TO_QUIZ_URL)
	public String addQuestionToQuiz(@RequestParam int id, HttpSession session) {
		Optional<Question> questionsToAdd= questionService.findById(id);
		Question questionToAdd=questionsToAdd.get();
		
		List<Question> addedQuestions;
		if (session.getAttribute(ADDED_QUESTIONS_LIST_SESSION_ATTR)==null) {
			addedQuestions= new ArrayList<Question>();
		}
		else {
			addedQuestions= (List<Question>) session.getAttribute(ADDED_QUESTIONS_LIST_SESSION_ATTR);
		}
		List<Question> availableQuestions=(List<Question>) session.getAttribute(SORTED_QUESTIONS_SESSION_ATTR);
		
		addedQuestions.add(questionToAdd);
		for(Question question: availableQuestions){
			if (question.getId()==questionToAdd.getId()) {
				questionToAdd=question;
			}
		}
		availableQuestions.remove(questionToAdd);
		
		session.setAttribute(ADDED_QUESTIONS_LIST_SESSION_ATTR, addedQuestions);
		session.setAttribute(SORTED_QUESTIONS_SESSION_ATTR, availableQuestions);
		
		return REDIRECT_CREATE_QUIZ_URL;
	}
	
	/**
	 * Removes question from quiz. removes question from added questions list and adds it to available questions list
	 * 
	 * @param id
	 * @param session
	 * @return redirect to /createQuiz
	 */
	@PostMapping(PROCESS_REMOVE_QUESTION_FROM_QUIZ_URL)
	public String removeQuestionFromQuiz(@RequestParam int id, HttpSession session) {
		Optional<Question> questionsToRemove= questionService.findById(id);
		Question questionToRemove=questionsToRemove.get();
		
		List<Question> addedQuestions= (List<Question>) session.getAttribute(ADDED_QUESTIONS_LIST_SESSION_ATTR);
		List<Question> availableQuestions=(List<Question>) session.getAttribute(SORTED_QUESTIONS_SESSION_ATTR);
		
		availableQuestions.add(questionToRemove);
		
		for(Question question: addedQuestions){
			if (question.getId()==questionToRemove.getId()) {
				questionToRemove=question;
			}
		}
		
		addedQuestions.remove(questionToRemove);
		
		
		session.setAttribute(ADDED_QUESTIONS_LIST_SESSION_ATTR, addedQuestions);
		session.setAttribute(SORTED_QUESTIONS_SESSION_ATTR, availableQuestions);
		return REDIRECT_CREATE_QUIZ_URL;
	}
	
	/**
	 * Displays the list of quizzes, sorted by user's role. Trainers can access all, sales access sales and student, and students can only access their own created quizzes/
	 * Has edit quiz and delete quiz buttons
	 * 
	 * @param session
	 * @return manageQuizzes view
	 */
	@GetMapping(MANAGE_QUIZZES_URL)
	public String manageQuizzes(HttpSession session) {
		
		User sessionUser= (User) session.getAttribute(CURRENT_USER_SESSION_ATTR);
		List<Quiz> quizzesByRole= new ArrayList<Quiz>();
		
		if (sessionUser.getRole().equals("Student")
			&& !(sessionUser.getStatus().equals("Absent"))) {
			quizzesByRole= quizService.findByUser(sessionUser);
		}
		
		else if(sessionUser.getRole().equals("Trainer")) {
			quizzesByRole=quizService.findAllQuizzes();
		}
		
		else if(sessionUser.getRole().equals("Sales")) {
			List<Quiz> allQuizzes= quizService.findAllQuizzes();
			for (Quiz quiz:allQuizzes) {
				if (quiz.getCreaterRole().equals("Sales")
						||quiz.getCreaterRole().equals("Student")) {
					quizzesByRole.add(quiz);
				}
			}
			
		}
		session.setAttribute(ALLOWED_LIST_SESSION_ATTR, quizzesByRole);
		session.setAttribute(QUIZ_LIST_SESSION_ATTR, quizzesByRole);
		return MANAGE_QUIZZES_VIEW;
	}
	
	/**
	 * Sorts quizzes based on checked boxes.
	 * For each set of check boxes, it will find quizzes with with the selected options
	 * 
	 * if multiple sets are selected at a time, quizzes need to fulfill having at least one of the checked options within in each set.
	 * if nothing selected with return all quizzes
	 * @param session
	 * @param categoryCheckbox
	 * @param subjectCheckbox
	 * @param formatCheckbox
	 * @return manageQuizzes view
	 */
	@PostMapping(PROCESS_SORT_MANAGE_QUIZ_URL)
	public String sortQuizzesForManaging(HttpSession session, @RequestParam String categoryCheckbox, @RequestParam String subjectCheckbox, @RequestParam String formatCheckbox) {
		List<Quiz> quizzes=(List<Quiz>) session.getAttribute(ALLOWED_LIST_SESSION_ATTR);
		String[] categories= categoryCheckbox.split(",");
		String[] subjects= subjectCheckbox.split(",");
		String[] formats= formatCheckbox.split(",");
		
		List<Quiz> quizzesFilter1= new ArrayList<Quiz>();
		List<Quiz> quizzesFilter2= new ArrayList<Quiz>();
		List<Quiz> quizzesFilterFinal= new ArrayList<Quiz>();
		
		if (categories.length>1
			&&subjects.length==1
			&&formats.length==1) {
			
			for (Quiz quiz: quizzes) {
				for (String category: categories) {
					if (quiz.getCategories().contains(category)
						&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
			quizzesFilterFinal=quizzesFilter1;
		}
		
		else if(categories.length==1
			&&subjects.length>1
			&&formats.length==1) {
			
			for (Quiz quiz: quizzes) {
				for (String subject: subjects) {
					if (quiz.getSubjects().contains(subject)
						&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
			quizzesFilterFinal=quizzesFilter1;
		}
		
		else if(categories.length==1
				&&subjects.length==1
				&&formats.length>1) {
				
			for (Quiz quiz: quizzes) {
				for (String format: formats) {
					if (quiz.getQuestionFormats().contains(format)
						&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
				quizzesFilterFinal=quizzesFilter1;
		}
		
		else if (categories.length>1
				&&subjects.length>1
				&&formats.length==1) {
				
			for (Quiz quiz: quizzes) {
				for (String category: categories) {
					if (quiz.getCategories().contains(category)
						&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
				
			for (Quiz quiz2: quizzesFilter1) {
				for (String subject: subjects) {
					if (quiz2.getSubjects().contains(subject)
						&&!quizzesFilter2.contains(quiz2)) {
						quizzesFilter2.add(quiz2);
					}
				}
			}
				quizzesFilterFinal=quizzesFilter2;
		}
		
		else if (categories.length>1
				&&subjects.length==1
				&&formats.length>1) {
				
			for (Quiz quiz: quizzes) {
				for (String category: categories) {
					if (quiz.getCategories().contains(category)
						&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
				
			for (Quiz quiz2: quizzesFilter1) {
				for (String format: formats) {
					if (quiz2.getQuestionFormats().contains(format)
						&&!quizzesFilter2.contains(quiz2)) {
						quizzesFilter2.add(quiz2);
					}
				}
			}
				quizzesFilterFinal=quizzesFilter2;
		}
		
		else if (categories.length==1
				&&subjects.length>1
				&&formats.length>1) {
				
			for (Quiz quiz: quizzes) {
				for (String subject: subjects) {
					if (quiz.getSubjects().contains(subject)
						&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
				
			for (Quiz quiz2: quizzesFilter1) {
				for (String format: formats) {
					if (quiz2.getQuestionFormats().contains(format)
						&&!quizzesFilter2.contains(quiz2)) {
						quizzesFilter2.add(quiz2);
					}
				}
			}
				quizzesFilterFinal=quizzesFilter2;
		}
		
		else if(categories.length==1
				&&subjects.length==1
				&&formats.length==1) {
			quizzesFilterFinal=quizzes;
		}
		
		else {
			for (Quiz quiz: quizzes) {
				for (String category: categories) {
					if (quiz.getCategories().contains(category)
							&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
			
			
			for (Quiz quiz2: quizzesFilter1) {
				for (String subject: subjects) {
					if (quiz2.getSubjects().contains(subject)
							&&!quizzesFilter2.contains(quiz2)) {
						quizzesFilter2.add(quiz2);
					}
				}
			}
			
			
			for (Quiz quiz3: quizzesFilter2) {
				for (String format: formats) {
					if (quiz3.getQuestionFormats().contains(format)
							&&!quizzesFilterFinal.contains(quiz3)) {
						quizzesFilterFinal.add(quiz3);
					}
				}
			}
		}
		
		
		session.setAttribute(QUIZ_LIST_SESSION_ATTR, quizzesFilterFinal);
		return MANAGE_QUIZZES_VIEW;
	}
	
	/**
	 * stores the quiz selected by user, bringing them back to the create quiz method to edit quiz details.
	 * 
	 * @param id
	 * @param session
	 * @return redirect to create quiz method
	 */
	@PostMapping(PROCESS_EDIT_QUIZ_URL)
	public String editQuiz(@RequestParam int id, HttpSession session) {
		Quiz quizToEdit=quizService.findById(id);
		quizToEdit.update();
		session.setAttribute(QUIZ_TO_EDIT_SESSION_ATTR, quizToEdit);
		session.setAttribute(ADDED_QUESTIONS_LIST_SESSION_ATTR, quizToEdit.getQuestions());
		return REDIRECT_CREATE_QUIZ_URL;
	}
	
	/**
	 * finds the quiz selected by user for deletion, and brings them to a confirmation page
	 * 
	 * @param id
	 * @param session
	 * @return delete confirmation view
	 */
	@PostMapping(PROCESS_DELETE_QUIZ_URL)
	public String processDeleteQuiz(@RequestParam int id, HttpSession session) {
		Quiz quizToDelete=quizService.findById(id);
		session.setAttribute(QUIZ_TO_DELETE_SESSION_ATTR, quizToDelete);
	return DELETE_QUIZ_CONFIRMATION_VIEW;
	}
	
	/**
	 * allows user to confirm their deletion of quiz
	 * deletes quiz from database
	 * 
	 * @param session
	 * @return redirects to manage quizzes method
	 */
	@GetMapping(DELETE_QUIZ_URL)
	public String deleteQuiz(HttpSession session) {
		Quiz quizToDelete=(Quiz) session.getAttribute(QUIZ_TO_DELETE_SESSION_ATTR);
		int id=quizToDelete.getId();
		quizToDelete=quizService.findById(id);
		quizToDelete.update();
		quizService.deleteQuiz(quizToDelete);
		log.info(quizToDelete+" deleted");
		session.removeAttribute(QUIZ_TO_DELETE_SESSION_ATTR);
	return REDIRECT_MANAGE_QUIZZES_URL;
	}
	
	/**
	 * brings user to view which displays all the database quizzes
	 * 
	 * @param session
	 * @return view quizzes view
	 */
	@GetMapping(VIEW_QUIZZES_URL)
	public String viewQuizzes(HttpSession session) {
		List<Quiz> allQuizzes = quizService.findAllQuizzes();
		session.setAttribute(QUIZ_LIST_SESSION_ATTR, allQuizzes);
		return VIEW_QUIZZES_VIEW;
	}
	
	/**
	 * similar to sortManageQuizzes, filters quizzes based on check boxes.
	 * It however starts with a different list, displaying ALL quizzes in the database regardless of user roles
	 * 
	 * @param session
	 * @param categoryCheckbox
	 * @param subjectCheckbox
	 * @param formatCheckbox
	 * @return view quizzes view
	 */
	@PostMapping(PROCESS_SORT_VIEW_QUIZ_URL)
	public String sortQuizzesForViewQuiz(HttpSession session, @RequestParam String categoryCheckbox, @RequestParam String subjectCheckbox, @RequestParam String formatCheckbox) {
		List<Quiz> quizzes=quizService.findAllQuizzes();
		String[] categories= categoryCheckbox.split(",");
		String[] subjects= subjectCheckbox.split(",");
		String[] formats= formatCheckbox.split(",");
		
		List<Quiz> quizzesFilter1= new ArrayList<Quiz>();
		List<Quiz> quizzesFilter2= new ArrayList<Quiz>();
		List<Quiz> quizzesFilterFinal= new ArrayList<Quiz>();
		
		if (categories.length>1
			&&subjects.length==1
			&&formats.length==1) {
			
			for (Quiz quiz: quizzes) {
				for (String category: categories) {
					if (quiz.getCategories().contains(category)
						&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
			quizzesFilterFinal=quizzesFilter1;
		}
		
		else if(categories.length==1
			&&subjects.length>1
			&&formats.length==1) {
			
			for (Quiz quiz: quizzes) {
				for (String subject: subjects) {
					if (quiz.getSubjects().contains(subject)
						&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
			quizzesFilterFinal=quizzesFilter1;
		}
		
		else if(categories.length==1
				&&subjects.length==1
				&&formats.length>1) {
				
			for (Quiz quiz: quizzes) {
				for (String format: formats) {
					if (quiz.getQuestionFormats().contains(format)
						&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
				quizzesFilterFinal=quizzesFilter1;
		}
		
		else if (categories.length>1
				&&subjects.length>1
				&&formats.length==1) {
				
			for (Quiz quiz: quizzes) {
				for (String category: categories) {
					if (quiz.getCategories().contains(category)
						&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
				
			for (Quiz quiz2: quizzesFilter1) {
				for (String subject: subjects) {
					if (quiz2.getSubjects().contains(subject)
						&&!quizzesFilter2.contains(quiz2)) {
						quizzesFilter2.add(quiz2);
					}
				}
			}
				quizzesFilterFinal=quizzesFilter2;
		}
		
		else if (categories.length>1
				&&subjects.length==1
				&&formats.length>1) {
				
			for (Quiz quiz: quizzes) {
				for (String category: categories) {
					if (quiz.getCategories().contains(category)
						&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
				
			for (Quiz quiz2: quizzesFilter1) {
				for (String format: formats) {
					if (quiz2.getQuestionFormats().contains(format)
						&&!quizzesFilter2.contains(quiz2)) {
						quizzesFilter2.add(quiz2);
					}
				}
			}
				quizzesFilterFinal=quizzesFilter2;
		}
		
		else if (categories.length==1
				&&subjects.length>1
				&&formats.length>1) {
				
			for (Quiz quiz: quizzes) {
				for (String subject: subjects) {
					if (quiz.getSubjects().contains(subject)
						&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
				
			for (Quiz quiz2: quizzesFilter1) {
				for (String format: formats) {
					if (quiz2.getQuestionFormats().contains(format)
						&&!quizzesFilter2.contains(quiz2)) {
						quizzesFilter2.add(quiz2);
					}
				}
			}
				quizzesFilterFinal=quizzesFilter2;
		}
		
		else if(categories.length==1
				&&subjects.length==1
				&&formats.length==1) {
			quizzesFilterFinal=quizzes;
		}
		
		else {
			for (Quiz quiz: quizzes) {
				for (String category: categories) {
					if (quiz.getCategories().contains(category)
							&&!quizzesFilter1.contains(quiz)) {
						quizzesFilter1.add(quiz);
					}
				}
			}
			
			
			for (Quiz quiz2: quizzesFilter1) {
				for (String subject: subjects) {
					if (quiz2.getSubjects().contains(subject)
							&&!quizzesFilter2.contains(quiz2)) {
						quizzesFilter2.add(quiz2);
					}
				}
			}
			
			
			for (Quiz quiz3: quizzesFilter2) {
				for (String format: formats) {
					if (quiz3.getQuestionFormats().contains(format)
							&&!quizzesFilterFinal.contains(quiz3)) {
						quizzesFilterFinal.add(quiz3);
					}
				}
			}
		}
		
		
		session.setAttribute(QUIZ_LIST_SESSION_ATTR, quizzesFilterFinal);
		return VIEW_QUIZZES_VIEW;
	}
	
	/**
	 * Deletes relevant session attributes should a process be cancelled
	 * 
	 * @param session
	 * @return home view
	 */
	@GetMapping(CANCEL_AND_RETURN_URL)
	public String cancelAndReturnHome(HttpSession session) {
		session.removeAttribute(QUIZ_LIST_SESSION_ATTR);
		session.removeAttribute(ADDED_QUESTIONS_LIST_SESSION_ATTR);
		session.removeAttribute(SORTED_QUESTIONS_SESSION_ATTR);
		session.removeAttribute(QUIZ_TO_EDIT_SESSION_ATTR);
		return HOME_VIEW;
	}

	
	
	
}
