package com.fdm.PreparationQuizProject.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

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

class QuizControllerTest {

	QuizController quizController;
	User user;
	Question question;
	Question question2;
	List<Question> sortedQuestion;
	List<Question> addedQuestion;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		quizController= new QuizController(mockQuestionService, mockQuizService, mockQuizSubmissionService, mockUserService, mockQuestionSubmissionService);
		
		user = new User();
		question = new Question("SQL","Course Content","Multiple Choice","Q1",4,"a",Arrays.asList("a","b","c","d"),user);
		question2 = new Question("OOD","Course Content","Short Answer","Q1",6,"a",null,user);
		
		sortedQuestion=new ArrayList<>(Arrays.asList(question,question2));
		addedQuestion=new ArrayList<>(Arrays.asList(question));
		
	}
	@Mock
	private QuestionService mockQuestionService;
	
	@Mock
	private QuizService mockQuizService;
	
	@Mock
	private QuizSubmissionService mockQuizSubmissionService;
	
	@Mock
	private UserService mockUserService;
	
	@Mock
	private QuestionSubmissionService mockQuestionSubmissionService;
	
	@Mock
	private Question mockQuestion;
	
	@Mock
	private List<Question> mockQuestionList;
	
	@Mock
	private Model mockModel;
	
	@Mock
	private HttpSession mockSession;
	
	@Mock
	private User mockUser;
	
	@Mock
	private Quiz mockQuiz;
	
	
	
	@Test
	void getTakeQuizPage_returns_takeQuizURL() {
		//arrange
		InOrder inOrder = inOrder(mockQuizService,mockSession,mockModel);
		user.setRole("Trainer");
		List<Question> questions = new ArrayList<>(Arrays.asList(question,question2));
		Quiz quiz = new Quiz("quizName", user, questions);
		when(mockQuizService.findById(1)).thenReturn(quiz);
		when(mockSession.getAttribute(QuizController.CURRENT_USER_SESSION_ATTR)).thenReturn(user);
		//act
		String result=quizController.getTakeQuizPage(1, mockModel, mockSession);
		//assert
		inOrder.verify(mockQuizService, times(1)).findById(1);
		inOrder.verify(mockSession, times(1)).getAttribute(QuizController.CURRENT_USER_SESSION_ATTR);
		inOrder.verify(mockModel, times(1)).addAttribute(eq(QuizController.QUIZ_TAKING_MODEL_ATTR),isA(QuizSubmission.class));

		assertEquals(QuizController.TAKE_QUIZ_VIEW, result);
	}
	
	@Test
	void processSubmitQuizPage_returns_homeURL_studentAnswerCorrectlyInMC() {
		//arrange
		InOrder inOrder = inOrder(mockQuestionSubmissionService,mockSession,mockModel);
		
		user.setId(1);
		user.setSubmittedQuizzes(new ArrayList<QuizSubmission>());
		QuestionSubmission questionSubmission = new QuestionSubmission("a", null, 0, question);
		QuestionSubmission questionSubmission2 = new QuestionSubmission("a", null, 0, question2);
		List<QuestionSubmission> questionSubmissions = new ArrayList<>(Arrays.asList(questionSubmission,questionSubmission2));
		QuizSubmission quizSubmission = new QuizSubmission("quiz", false, 0, user,questionSubmissions,Arrays.asList("OOD","SQL"),Arrays.asList("Course Content"),Arrays.asList("Multiple Choice","Short Answer"),2,10);		

		when(mockQuestionSubmissionService.saveQuestionSubmission(questionSubmission)).thenReturn(questionSubmission);
		when(mockQuestionSubmissionService.saveQuestionSubmission(questionSubmission2)).thenReturn(questionSubmission2);
		when(mockQuizSubmissionService.save(quizSubmission)).thenReturn(quizSubmission);
		when(mockUserService.findById(1)).thenReturn(user);
		when(mockUserService.updateUser(user)).thenReturn(user);
		//act
		String result=quizController.processSubmitQuizPage(quizSubmission, mockModel, mockSession);
		//assert
		inOrder.verify(mockQuestionSubmissionService, times(1)).saveQuestionSubmission(questionSubmission);
		inOrder.verify(mockQuestionSubmissionService, times(1)).saveQuestionSubmission(questionSubmission2);
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.CURRENT_USER_SESSION_ATTR, user);
		inOrder.verify(mockModel, times(1)).addAttribute(QuizController.MESSAGE_MODEL_ATTR, "Quiz successfully submited!");

		assertEquals(QuizController.HOME_VIEW, result);
	}
	
	@Test
	void processSubmitQuizPage_returns_homeURL_studentAnswerIncorrectlyInMC() {
		//arrange
		InOrder inOrder = inOrder(mockQuestionSubmissionService,mockSession,mockModel);
		
		user.setId(1);
		user.setSubmittedQuizzes(new ArrayList<QuizSubmission>());
		QuestionSubmission questionSubmission = new QuestionSubmission("c", null, 0, question);
		QuestionSubmission questionSubmission2 = new QuestionSubmission("c", null, 0, question2);
		List<QuestionSubmission> questionSubmissions = new ArrayList<>(Arrays.asList(questionSubmission,questionSubmission2));
		QuizSubmission quizSubmission = new QuizSubmission("quiz", false, 0, user,questionSubmissions,Arrays.asList("OOD","SQL"),Arrays.asList("Course Content"),Arrays.asList("Multiple Choice","Short Answer"),2,10);		

		when(mockQuestionSubmissionService.saveQuestionSubmission(questionSubmission)).thenReturn(questionSubmission);
		when(mockQuestionSubmissionService.saveQuestionSubmission(questionSubmission2)).thenReturn(questionSubmission2);
		when(mockQuizSubmissionService.save(quizSubmission)).thenReturn(quizSubmission);
		when(mockUserService.findById(1)).thenReturn(user);
		when(mockUserService.updateUser(user)).thenReturn(user);
		//act
		String result=quizController.processSubmitQuizPage(quizSubmission, mockModel, mockSession);
		//assert
		inOrder.verify(mockQuestionSubmissionService, times(1)).saveQuestionSubmission(questionSubmission);
		inOrder.verify(mockQuestionSubmissionService, times(1)).saveQuestionSubmission(questionSubmission2);
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.CURRENT_USER_SESSION_ATTR, user);
		inOrder.verify(mockModel, times(1)).addAttribute(QuizController.MESSAGE_MODEL_ATTR, "Quiz successfully submited!");

		assertEquals(QuizController.HOME_VIEW, result);
	}
	
	@Test
	void processSubmitQuizPage_returns_homeURL_studentAnswerNothing() {
		//arrange
		InOrder inOrder = inOrder(mockQuestionSubmissionService,mockSession,mockModel);
		
		user.setId(1);
		user.setSubmittedQuizzes(new ArrayList<QuizSubmission>());
		QuestionSubmission questionSubmission = new QuestionSubmission(null, null, 0, question);
		QuestionSubmission questionSubmission2 = new QuestionSubmission(null, null, 0, question2);
		List<QuestionSubmission> questionSubmissions = new ArrayList<>(Arrays.asList(questionSubmission,questionSubmission2));
		QuizSubmission quizSubmission = new QuizSubmission("quiz", false, 0, user,questionSubmissions,Arrays.asList("OOD","SQL"),Arrays.asList("Course Content"),Arrays.asList("Multiple Choice","Short Answer"),2,10);		

		when(mockQuestionSubmissionService.saveQuestionSubmission(questionSubmission)).thenReturn(questionSubmission);
		when(mockQuestionSubmissionService.saveQuestionSubmission(questionSubmission2)).thenReturn(questionSubmission2);
		when(mockQuizSubmissionService.save(quizSubmission)).thenReturn(quizSubmission);
		when(mockUserService.findById(1)).thenReturn(user);
		when(mockUserService.updateUser(user)).thenReturn(user);
		//act
		String result=quizController.processSubmitQuizPage(quizSubmission, mockModel, mockSession);
		//assert
		inOrder.verify(mockQuestionSubmissionService, times(1)).saveQuestionSubmission(questionSubmission);
		inOrder.verify(mockQuestionSubmissionService, times(1)).saveQuestionSubmission(questionSubmission2);
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.CURRENT_USER_SESSION_ATTR, user);
		inOrder.verify(mockModel, times(1)).addAttribute(QuizController.MESSAGE_MODEL_ATTR, "Quiz successfully submited!");

		assertEquals(QuizController.HOME_VIEW, result);
	}
	
	@Test
	void createQuiz_returns_createQuizView_newQuiz_missing_sortedQuestion_and_addedQuestionsList() {
		//arrange
		when(mockSession.getAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR)).thenReturn(null);
		when(mockSession.getAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR)).thenReturn(null);
		when(mockQuestionService.findAllQuestion()).thenReturn(mockQuestionList);
		//act
		String result=quizController.createQuiz(mockModel, mockSession);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR, mockQuestionList);
		assertEquals(QuizController.CREATE_QUIZ_VIEW, result);
	}
	
	@Test
	void createQuiz_returns_createQuizView_editOldQuiz_have_sortedQuestion_but_no_addedQuestionsList() {
		//arrange
		when(mockSession.getAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR)).thenReturn(sortedQuestion);
		when(mockSession.getAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR)).thenReturn(null);
		when(mockQuestionService.findAllQuestion()).thenReturn(mockQuestionList);
		//act
		String result=quizController.createQuiz(mockModel, mockSession);
		//assert
		assertEquals(QuizController.CREATE_QUIZ_VIEW, result);
	}
	
	@Test
	void createQuiz_returns_createQuizView_editOldQuiz_have_sortedQuestion_and_addedQuestionsList() {
		//arrange
		when(mockSession.getAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR)).thenReturn(sortedQuestion);
		when(mockSession.getAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR)).thenReturn(addedQuestion);
		when(mockQuestionService.findAllQuestion()).thenReturn(mockQuestionList);
		//act
		String result=quizController.createQuiz(mockModel, mockSession);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR, sortedQuestion);
		assertEquals(QuizController.CREATE_QUIZ_VIEW, result);
	}
	
	@Test
	void processQuizCreation_returns_QuizSavedView_newQuiz_no_QuizToEdit_sessionAttr() {
		//arrange
		InOrder inOrder= inOrder(mockSession);
		when(mockSession.getAttribute(QuizController.CURRENT_USER_SESSION_ATTR)).thenReturn(user);
		when(mockSession.getAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR)).thenReturn(addedQuestion);
		when(mockSession.getAttribute(QuizController.QUIZ_TO_EDIT_SESSION_ATTR)).thenReturn(null);
		//act
		String result=quizController.processQuizCreation("name", mockSession);
		//assert
		inOrder.verify(mockSession, times(1)).removeAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR);
		inOrder.verify(mockSession, times(1)).removeAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR);
		inOrder.verify(mockSession, times(1)).removeAttribute(QuizController.QUIZ_TO_EDIT_SESSION_ATTR);
		inOrder.verify(mockSession, times(1)).removeAttribute(QuizController.QUIZ_LIST_SESSION_ATTR);
		assertEquals(QuizController.QUIZ_SAVED_VIEW, result);
	}
	
	@Test
	void processQuizCreation_returns_QuizSavedView_oldQuiz_QuizToEdit() {
		//arrange
		InOrder inOrder= inOrder(mockQuizService, mockSession);
		when(mockSession.getAttribute(QuizController.CURRENT_USER_SESSION_ATTR)).thenReturn(user);
		String quizName= "name";
		when(mockSession.getAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR)).thenReturn(addedQuestion);
		Quiz dummyQuiz= new Quiz();
		when(mockSession.getAttribute(QuizController.QUIZ_TO_EDIT_SESSION_ATTR)).thenReturn(dummyQuiz);
		Quiz sessionQuiz=dummyQuiz;
		sessionQuiz.setName(quizName);
		sessionQuiz.setQuestions(addedQuestion);
		sessionQuiz.update();
		//act
		String result=quizController.processQuizCreation(quizName, mockSession);
		//assert
		inOrder.verify(mockQuizService, times(1)).save(sessionQuiz);
		inOrder.verify(mockSession, times(1)).removeAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR);
		inOrder.verify(mockSession, times(1)).removeAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR);
		inOrder.verify(mockSession, times(1)).removeAttribute(QuizController.QUIZ_TO_EDIT_SESSION_ATTR);
		inOrder.verify(mockSession, times(1)).removeAttribute(QuizController.QUIZ_LIST_SESSION_ATTR);
		assertEquals(QuizController.QUIZ_SAVED_VIEW, result);
	}
	
	@Test
	void filterQuestionsInCreateQuiz_oneSubjectOnly_noAddedQuestions() {
		//arrange
		Question dummyQuestion= new Question("OOD","ALL","ALL",null,0,null,null,null);
		List<Question> subjectAndCategoryList=new ArrayList<Question>(Arrays.asList(question, question2));
		List<Question> subjectAndFormatList=new ArrayList<Question>(Arrays.asList(question, question));
		List<Question> categoryAndFormatList=new ArrayList<Question>(Arrays.asList(question2, question));
		List<Question> subjectList=new ArrayList<Question>(Arrays.asList(question));
		List<Question> categoryList=new ArrayList<Question>(Arrays.asList(question2));
		List<Question> formatList=new ArrayList<Question>(Arrays.asList(question2, question2));
		List<Question> subjectAndCategoryAndFormatList=new ArrayList<Question>(Arrays.asList(question,question2, question));

		when(mockQuestionService.findBySubjectAndCategory(dummyQuestion.getSubject(), dummyQuestion.getCategory())).thenReturn(subjectAndCategoryList);
		when(mockQuestionService.findBySubjectAndFormat(dummyQuestion.getSubject(), dummyQuestion.getFormat())).thenReturn(subjectAndFormatList);
		when(mockQuestionService.findByCategoryAndFormat(dummyQuestion.getCategory(), dummyQuestion.getFormat())).thenReturn(categoryAndFormatList);
		when(mockQuestionService.findBySubject(dummyQuestion.getSubject())).thenReturn(subjectList);
		when(mockQuestionService.findByCategory(dummyQuestion.getCategory())).thenReturn(categoryList);
		when(mockQuestionService.findByFormat(dummyQuestion.getFormat())).thenReturn(formatList);
		when(mockQuestionService.findBySubjectAndCategoryAndFormat(dummyQuestion.getSubject(), dummyQuestion.getCategory(), dummyQuestion.getFormat())).thenReturn(subjectAndCategoryAndFormatList);
		
		when(mockSession.getAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR)).thenReturn(null);
		//act
		String result=quizController.filterQuestionsInCreateQuiz(dummyQuestion, mockSession);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR,subjectList);
		assertEquals(QuizController.REDIRECT_CREATE_QUIZ_URL, result);
	}
	@Test
	void filterQuestionsInCreateQuiz_Subject_Category_And_Format_noAddedQuestions() {
		//arrange
		Question dummyQuestion= new Question("OOD","Course Content","Multiple Choice",null,0,null,null,null);
		List<Question> subjectAndCategoryList=new ArrayList<Question>(Arrays.asList(question, question2));
		List<Question> subjectAndFormatList=new ArrayList<Question>(Arrays.asList(question, question));
		List<Question> categoryAndFormatList=new ArrayList<Question>(Arrays.asList(question2, question));
		List<Question> subjectList=new ArrayList<Question>(Arrays.asList(question));
		List<Question> categoryList=new ArrayList<Question>(Arrays.asList(question2));
		List<Question> formatList=new ArrayList<Question>(Arrays.asList(question2, question2));
		List<Question> subjectAndCategoryAndFormatList=new ArrayList<Question>(Arrays.asList(question,question2, question));

		when(mockQuestionService.findBySubjectAndCategory(dummyQuestion.getSubject(), dummyQuestion.getCategory())).thenReturn(subjectAndCategoryList);
		when(mockQuestionService.findBySubjectAndFormat(dummyQuestion.getSubject(), dummyQuestion.getFormat())).thenReturn(subjectAndFormatList);
		when(mockQuestionService.findByCategoryAndFormat(dummyQuestion.getCategory(), dummyQuestion.getFormat())).thenReturn(categoryAndFormatList);
		when(mockQuestionService.findBySubject(dummyQuestion.getSubject())).thenReturn(subjectList);
		when(mockQuestionService.findByCategory(dummyQuestion.getCategory())).thenReturn(categoryList);
		when(mockQuestionService.findByFormat(dummyQuestion.getFormat())).thenReturn(formatList);
		when(mockQuestionService.findBySubjectAndCategoryAndFormat(dummyQuestion.getSubject(), dummyQuestion.getCategory(), dummyQuestion.getFormat())).thenReturn(subjectAndCategoryAndFormatList);
		
		when(mockSession.getAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR)).thenReturn(null);
		//act
		String result=quizController.filterQuestionsInCreateQuiz(dummyQuestion, mockSession);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR,subjectAndCategoryAndFormatList);
		assertEquals(QuizController.REDIRECT_CREATE_QUIZ_URL, result);
	}
	
	@Test
	void filterQuestionsInCreateQuiz_Subject_And_Category_With_AddedQuestions() {
		//arrange
		Question dummyQuestion= new Question("OOD","Course Content","ALL",null,0,null,null,null);
		List<Question> subjectAndCategoryList=new ArrayList<Question>(Arrays.asList(question, question2));
		List<Question> subjectAndFormatList=new ArrayList<Question>(Arrays.asList(question, question));
		List<Question> categoryAndFormatList=new ArrayList<Question>(Arrays.asList(question2, question));
		List<Question> subjectList=new ArrayList<Question>(Arrays.asList(question));
		List<Question> categoryList=new ArrayList<Question>(Arrays.asList(question2));
		List<Question> formatList=new ArrayList<Question>(Arrays.asList(question2, question2));
		List<Question> subjectAndCategoryAndFormatList=new ArrayList<Question>(Arrays.asList(question,question2, question));

		when(mockQuestionService.findBySubjectAndCategory(dummyQuestion.getSubject(), dummyQuestion.getCategory())).thenReturn(subjectAndCategoryList);
		when(mockQuestionService.findBySubjectAndFormat(dummyQuestion.getSubject(), dummyQuestion.getFormat())).thenReturn(subjectAndFormatList);
		when(mockQuestionService.findByCategoryAndFormat(dummyQuestion.getCategory(), dummyQuestion.getFormat())).thenReturn(categoryAndFormatList);
		when(mockQuestionService.findBySubject(dummyQuestion.getSubject())).thenReturn(subjectList);
		when(mockQuestionService.findByCategory(dummyQuestion.getCategory())).thenReturn(categoryList);
		when(mockQuestionService.findByFormat(dummyQuestion.getFormat())).thenReturn(formatList);
		when(mockQuestionService.findBySubjectAndCategoryAndFormat(dummyQuestion.getSubject(), dummyQuestion.getCategory(), dummyQuestion.getFormat())).thenReturn(subjectAndCategoryAndFormatList);
		
		when(mockSession.getAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR)).thenReturn(addedQuestion);
		//act
		String result=quizController.filterQuestionsInCreateQuiz(dummyQuestion, mockSession);
		subjectAndCategoryList.remove(question);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR, subjectAndCategoryList);
		assertEquals(QuizController.REDIRECT_CREATE_QUIZ_URL, result);
	}
	
	@Test
	void addQuestionToQuiz_RedirectsTo_CreateQuizView() {
		//arrange
		InOrder inOrder= inOrder(mockSession);
		Optional<Question> questionsToAdd= Optional.of(question);
		when(mockQuestionService.findById(4)).thenReturn(questionsToAdd);
		when(mockSession.getAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR)).thenReturn(addedQuestion);
		when(mockSession.getAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR)).thenReturn(sortedQuestion);
		addedQuestion.add(question);
		sortedQuestion.remove(question);
		//act
		String result=quizController.addQuestionToQuiz(4, mockSession);
		//assert
		inOrder.verify(mockSession,times(1)).setAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR, addedQuestion);
		inOrder.verify(mockSession,times(1)).setAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR,sortedQuestion);
		assertEquals(QuizController.REDIRECT_CREATE_QUIZ_URL, result);
	}
	
	@Test
	void addQuestionToQuiz_RedirectsTo_CreateQuizView_no_initial_addedQuestions() {
		//arrange
		InOrder inOrder= inOrder(mockSession);
		Optional<Question> questionsToAdd= Optional.of(question);
		when(mockQuestionService.findById(4)).thenReturn(questionsToAdd);
		when(mockSession.getAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR)).thenReturn(null);
		when(mockSession.getAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR)).thenReturn(sortedQuestion);
		List<Question> newList= new ArrayList<Question>();
		newList.add(question);
		sortedQuestion.remove(question);
		//act
		String result=quizController.addQuestionToQuiz(4, mockSession);
		//assert
		inOrder.verify(mockSession,times(1)).setAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR, newList);
		inOrder.verify(mockSession,times(1)).setAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR,sortedQuestion);
		assertEquals(QuizController.REDIRECT_CREATE_QUIZ_URL, result);
	}
	
	@Test
	void removeQuestionFromQuiz_RedirectsTo_CreateQuizView() {
		//arrange
		InOrder inOrder= inOrder(mockSession);
		Optional<Question> questionsToAdd= Optional.of(question);
		when(mockQuestionService.findById(4)).thenReturn(questionsToAdd);
		when(mockSession.getAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR)).thenReturn(addedQuestion);
		when(mockSession.getAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR)).thenReturn(sortedQuestion);
		addedQuestion.remove(question);
		sortedQuestion.add(question);
		//act
		String result=quizController.removeQuestionFromQuiz(4, mockSession);
		//assert
		inOrder.verify(mockSession,times(1)).setAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR, addedQuestion);
		inOrder.verify(mockSession,times(1)).setAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR,sortedQuestion);
		assertEquals(QuizController.REDIRECT_CREATE_QUIZ_URL, result);
	}
	
	@Test
	void manageQuizzes_Returns_manageQuizzesView_Student() {
		//arrange
		InOrder inOrder= inOrder(mockSession);
		user.setRole("Student");
		user.setStatus("Beach");
		Quiz quiz1= new Quiz("name", user, addedQuestion);
		Quiz quiz2= new Quiz("name", user, addedQuestion);
		quiz2.setCreaterRole("Sales");
		List<Quiz> dummyQuizList1= new ArrayList<Quiz>(Arrays.asList(quiz1));
		List<Quiz> dummyQuizList2= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2));
		when(mockSession.getAttribute(QuizController.CURRENT_USER_SESSION_ATTR)).thenReturn(user);
		
		when(mockQuizService.findByUser(user)).thenReturn(dummyQuizList1);
		when(mockQuizService.findAllQuizzes()).thenReturn(dummyQuizList2);
		//act
		String result=quizController.manageQuizzes(mockSession);
		//assert
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.ALLOWED_LIST_SESSION_ATTR, dummyQuizList1);
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, dummyQuizList1);
		assertEquals(QuizController.MANAGE_QUIZZES_VIEW,result);
	}
	
	@Test
	void manageQuizzes_Returns_manageQuizzesView_AbsentStudent() {
		//arrange
		InOrder inOrder= inOrder(mockSession);
		user.setRole("Student");
		user.setStatus("Absent");
		Quiz quiz1= new Quiz("name", user, addedQuestion);
		Quiz quiz2= new Quiz("name", user, addedQuestion);
		List<Quiz> dummyQuizList1= new ArrayList<Quiz>(Arrays.asList(quiz1));
		List<Quiz> dummyQuizList2= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2));
		List<Quiz> emptyList= new ArrayList<Quiz>();
		when(mockSession.getAttribute(QuizController.CURRENT_USER_SESSION_ATTR)).thenReturn(user);
		
		when(mockQuizService.findByUser(user)).thenReturn(dummyQuizList1);
		when(mockQuizService.findAllQuizzes()).thenReturn(dummyQuizList2);
		//act
		String result=quizController.manageQuizzes(mockSession);
		//assert
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.ALLOWED_LIST_SESSION_ATTR, emptyList);
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, emptyList);
		assertEquals(QuizController.MANAGE_QUIZZES_VIEW,result);
	}
	
	@Test
	void manageQuizzes_Returns_manageQuizzesView_Trainer() {
		//arrange
		InOrder inOrder= inOrder(mockSession);
		user.setRole("Trainer");
		Quiz quiz1= new Quiz("name", user, addedQuestion);
		Quiz quiz2= new Quiz("name", user, addedQuestion);
		List<Quiz> dummyQuizList1= new ArrayList<Quiz>(Arrays.asList(quiz1));
		List<Quiz> dummyQuizList2= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2));
		when(mockSession.getAttribute(QuizController.CURRENT_USER_SESSION_ATTR)).thenReturn(user);
		
		when(mockQuizService.findByUser(user)).thenReturn(dummyQuizList1);
		when(mockQuizService.findAllQuizzes()).thenReturn(dummyQuizList2);
		//act
		String result=quizController.manageQuizzes(mockSession);
		//assert
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.ALLOWED_LIST_SESSION_ATTR, dummyQuizList2);
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, dummyQuizList2);
		assertEquals(QuizController.MANAGE_QUIZZES_VIEW,result);
	}
	
	@Test
	void manageQuizzes_Returns_manageQuizzesView_Sales() {
		//arrange
		InOrder inOrder= inOrder(mockSession);
		Quiz quiz1= new Quiz("name", user, addedQuestion);
		Quiz quiz2= new Quiz("name", user, addedQuestion);
		quiz1.setCreaterRole("Sales");
		quiz2.setCreaterRole("Sales");
		user.setRole("Sales");
		List<Quiz> dummyQuizList1= new ArrayList<Quiz>(Arrays.asList(quiz1));
		List<Quiz> dummyQuizList2= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2));
		when(mockSession.getAttribute(QuizController.CURRENT_USER_SESSION_ATTR)).thenReturn(user);
		
		when(mockQuizService.findByUser(user)).thenReturn(dummyQuizList1);
		when(mockQuizService.findAllQuizzes()).thenReturn(dummyQuizList2);
		//act
		String result=quizController.manageQuizzes(mockSession);
		//assert
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.ALLOWED_LIST_SESSION_ATTR, dummyQuizList2);
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, dummyQuizList2);
		assertEquals(QuizController.MANAGE_QUIZZES_VIEW,result);
	}
	
	@Test
	void manageQuizzes_Returns_manageQuizzesView_Sales_quizzes_created_by_trainer() {
		//arrange
		InOrder inOrder= inOrder(mockSession);
		Quiz quiz1= new Quiz("name", user, addedQuestion);
		Quiz quiz2= new Quiz("name", user, addedQuestion);
		quiz1.setCreaterRole("Sales");
		quiz2.setCreaterRole("Trainer");
		user.setRole("Sales");
		List<Quiz> dummyQuizList1= new ArrayList<Quiz>(Arrays.asList(quiz1));
		List<Quiz> dummyQuizList2= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2));
		when(mockSession.getAttribute(QuizController.CURRENT_USER_SESSION_ATTR)).thenReturn(user);
		
		when(mockQuizService.findByUser(user)).thenReturn(dummyQuizList1);
		when(mockQuizService.findAllQuizzes()).thenReturn(dummyQuizList2);
		dummyQuizList2.remove(quiz2);
		//act
		String result=quizController.manageQuizzes(mockSession);
		//assert
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.ALLOWED_LIST_SESSION_ATTR, dummyQuizList2);
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, dummyQuizList2);
		assertEquals(QuizController.MANAGE_QUIZZES_VIEW,result);
	}

	@Test
	void sortQuizzesForManaging_nothingChecked() {
		//arrange
		String categoryCheckbox="check";
		String subjectCheckbox="check";
		String formatCheckbox="check";
		
		Quiz quiz1=new Quiz();
		quiz1.setCategories(Arrays.asList("Course Content"));
		quiz1.setSubjects(Arrays.asList("Java", "OOD", "Unix"));
		quiz1.setQuestionFormats(Arrays.asList("Multiple Choice"));
		
		Quiz quiz2=new Quiz();
		quiz2.setCategories(Arrays.asList("Course Content"));
		quiz2.setSubjects(Arrays.asList("Java"));
		quiz2.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
		
		Quiz quiz3=new Quiz();
		quiz3.setCategories(Arrays.asList("Course Content", "Interview"));
		quiz3.setSubjects(Arrays.asList("Unix", "SQL"));
		quiz3.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
		
		List<Quiz> quizzes= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2, quiz3));
		when(mockSession.getAttribute(QuizController.ALLOWED_LIST_SESSION_ATTR)).thenReturn(quizzes);
		//act
		String result=quizController.sortQuizzesForManaging(mockSession, categoryCheckbox, subjectCheckbox, formatCheckbox);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, quizzes);
		assertEquals(QuizController.MANAGE_QUIZZES_VIEW, result);
	}
	
	@Test
	void sortQuizzesForManaging_OnlyCategoryChecked() {
		//arrange
		String categoryCheckbox="check,Interview";
		String subjectCheckbox="check";
		String formatCheckbox="check";
		
		Quiz quiz1=new Quiz();
		quiz1.setCategories(Arrays.asList("Course Content"));
		quiz1.setSubjects(Arrays.asList("Java", "OOD", "Unix"));
		quiz1.setQuestionFormats(Arrays.asList("Multiple Choice"));
		
		Quiz quiz2=new Quiz();
		quiz2.setCategories(Arrays.asList("Course Content"));
		quiz2.setSubjects(Arrays.asList("Java"));
		quiz2.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
		
		Quiz quiz3=new Quiz();
		quiz3.setCategories(Arrays.asList("Course Content", "Interview"));
		quiz3.setSubjects(Arrays.asList("Unix", "SQL"));
		quiz3.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
		
		List<Quiz> quizzes= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2, quiz3));
		when(mockSession.getAttribute(QuizController.ALLOWED_LIST_SESSION_ATTR)).thenReturn(quizzes);
		//act
		quizzes.remove(quiz1);
		quizzes.remove(quiz2);
		String result=quizController.sortQuizzesForManaging(mockSession, categoryCheckbox, subjectCheckbox, formatCheckbox);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, quizzes);
		assertEquals(QuizController.MANAGE_QUIZZES_VIEW, result);
	}
	
	@Test
	void sortQuizzesForManaging_Category_And_Subject_Checked() {
		//arrange
		String categoryCheckbox="check,Course Content";
		String subjectCheckbox="check,Java";
		String formatCheckbox="check";
		
		Quiz quiz1=new Quiz();
		quiz1.setCategories(Arrays.asList("Course Content"));
		quiz1.setSubjects(Arrays.asList("Java", "OOD", "Unix"));
		quiz1.setQuestionFormats(Arrays.asList("Multiple Choice"));
		
		Quiz quiz2=new Quiz();
		quiz2.setCategories(Arrays.asList("Course Content"));
		quiz2.setSubjects(Arrays.asList("Java"));
		quiz2.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
		
		Quiz quiz3=new Quiz();
		quiz3.setCategories(Arrays.asList("Course Content", "Interview"));
		quiz3.setSubjects(Arrays.asList("Unix", "SQL"));
		quiz3.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
		
		List<Quiz> quizzes= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2, quiz3));
		when(mockSession.getAttribute(QuizController.ALLOWED_LIST_SESSION_ATTR)).thenReturn(quizzes);
		//act
		quizzes.remove(quiz3);
		String result=quizController.sortQuizzesForManaging(mockSession, categoryCheckbox, subjectCheckbox, formatCheckbox);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, quizzes);
		assertEquals(QuizController.MANAGE_QUIZZES_VIEW, result);
	}
	
	@Test
	void sortQuizzesForManaging_AllChecked() {
		//arrange
		String categoryCheckbox="check,Course Content";
		String subjectCheckbox="check,Java";
		String formatCheckbox="check,Multiple Choice";
		
		Quiz quiz1=new Quiz();
		quiz1.setCategories(Arrays.asList("Course Content"));
		quiz1.setSubjects(Arrays.asList("Java", "OOD", "Unix"));
		quiz1.setQuestionFormats(Arrays.asList("Multiple Choice"));
		
		Quiz quiz2=new Quiz();
		quiz2.setCategories(Arrays.asList("Course Content"));
		quiz2.setSubjects(Arrays.asList("Java"));
		quiz2.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
		
		Quiz quiz3=new Quiz();
		quiz3.setCategories(Arrays.asList("Course Content", "Interview"));
		quiz3.setSubjects(Arrays.asList("Unix", "SQL"));
		quiz3.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
		
		List<Quiz> quizzes= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2, quiz3));
		when(mockSession.getAttribute(QuizController.ALLOWED_LIST_SESSION_ATTR)).thenReturn(quizzes);
		//act
		quizzes.remove(quiz2);
		quizzes.remove(quiz3);
		String result=quizController.sortQuizzesForManaging(mockSession, categoryCheckbox, subjectCheckbox, formatCheckbox);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, quizzes);
		assertEquals(QuizController.MANAGE_QUIZZES_VIEW, result);
	}
	
	@Test
	void sortQuizzesForManaging_double_format() {
		//arrange
		String categoryCheckbox="check";
		String subjectCheckbox="check";
		String formatCheckbox="check,Multiple Choice,Short Answer";
		
		Quiz quiz1=new Quiz();
		quiz1.setCategories(Arrays.asList("Course Content"));
		quiz1.setSubjects(Arrays.asList("Java", "OOD", "Unix"));
		quiz1.setQuestionFormats(Arrays.asList("Multiple Choice"));
		
		Quiz quiz2=new Quiz();
		quiz2.setCategories(Arrays.asList("Course Content"));
		quiz2.setSubjects(Arrays.asList("Java"));
		quiz2.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
		
		Quiz quiz3=new Quiz();
		quiz3.setCategories(Arrays.asList("Course Content", "Interview"));
		quiz3.setSubjects(Arrays.asList("Unix", "SQL"));
		quiz3.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
		
		List<Quiz> quizzes= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2, quiz3));
		when(mockSession.getAttribute(QuizController.ALLOWED_LIST_SESSION_ATTR)).thenReturn(quizzes);
		//act
		String result=quizController.sortQuizzesForManaging(mockSession, categoryCheckbox, subjectCheckbox, formatCheckbox);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, quizzes);
		assertEquals(QuizController.MANAGE_QUIZZES_VIEW, result);
	}
	
	@Test
	void sortQuizzesForManaging_triple_subject_one_format() {
		//arrange
		String categoryCheckbox="check";
		String subjectCheckbox="check,Java,OOD,Unix";
		String formatCheckbox="check,Multiple Choice";
		
		Quiz quiz1=new Quiz();
		quiz1.setCategories(Arrays.asList("Course Content"));
		quiz1.setSubjects(Arrays.asList("Java", "OOD", "Unix"));
		quiz1.setQuestionFormats(Arrays.asList("Multiple Choice"));
		
		Quiz quiz2=new Quiz();
		quiz2.setCategories(Arrays.asList("Course Content"));
		quiz2.setSubjects(Arrays.asList("Java"));
		quiz2.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
		
		Quiz quiz3=new Quiz();
		quiz3.setCategories(Arrays.asList("Course Content", "Interview"));
		quiz3.setSubjects(Arrays.asList("Unix", "SQL"));
		quiz3.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
		
		List<Quiz> quizzes= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2, quiz3));
		when(mockSession.getAttribute(QuizController.ALLOWED_LIST_SESSION_ATTR)).thenReturn(quizzes);
		//act
		quizzes.remove(quiz3);
		String result=quizController.sortQuizzesForManaging(mockSession, categoryCheckbox, subjectCheckbox, formatCheckbox);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, quizzes);
		assertEquals(QuizController.MANAGE_QUIZZES_VIEW, result);
	}
	
	@Test
	void editQuiz_redirects_To_CreateQuizUrl() {
		//arrange
		InOrder inOrder= inOrder(mockSession);
		when(mockQuizService.findById(0)).thenReturn(mockQuiz);
		//act
		String result=quizController.editQuiz(0, mockSession);
		//assert
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_TO_EDIT_SESSION_ATTR, mockQuiz);
		inOrder.verify(mockSession, times(1)).setAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR, mockQuiz.getQuestions());
		assertEquals(QuizController.REDIRECT_CREATE_QUIZ_URL, result);
	}
	
	@Test
	void processDeleteQuiz_returns_deleteConfirmationView() {
		//arrange
		when(mockQuizService.findById(0)).thenReturn(mockQuiz);
		//act
		String result=quizController.processDeleteQuiz(0, mockSession);
		//assert
		verify(mockSession,times(1)).setAttribute(QuizController.QUIZ_TO_DELETE_SESSION_ATTR, mockQuiz);
		assertEquals(QuizController.DELETE_QUIZ_CONFIRMATION_VIEW, result);
	}
	
	@Test
	void deleteQuiz_redirectsTo_ManageQuizzesURL_deletes_quiz_From_DB() {
		//arrange
		InOrder inOrder= inOrder(mockQuizService, mockSession);
		when(mockSession.getAttribute(QuizController.QUIZ_TO_DELETE_SESSION_ATTR)).thenReturn(mockQuiz);
		when(mockQuizService.findById(0)).thenReturn(mockQuiz);
		//act
		String result=quizController.deleteQuiz(mockSession);
		//assert
		inOrder.verify(mockQuizService, times(1)).deleteQuiz(mockQuiz);
		inOrder.verify(mockSession, times(1)).removeAttribute(QuizController.QUIZ_TO_DELETE_SESSION_ATTR);
		assertEquals(QuizController.REDIRECT_MANAGE_QUIZZES_URL, result);
	}
	
	@Test
	void viewQuizzes_returns_viewQuizzesView() {
		//arrange
		List<Quiz> dummyQuizList= new ArrayList<Quiz>();
		when(mockQuizService.findAllQuizzes()).thenReturn(dummyQuizList);
		//act
		String result=quizController.viewQuizzes(mockSession);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, dummyQuizList);
		assertEquals(QuizController.VIEW_QUIZZES_VIEW, result);
	}
	
	@Test
	void sortQuizzezForViewQuiz_NothingChecked() {
		//arrange
		String categoryCheckbox="check";
		String subjectCheckbox="check";
		String formatCheckbox="check";
				
		Quiz quiz1=new Quiz();
		quiz1.setCategories(Arrays.asList("Course Content"));
		quiz1.setSubjects(Arrays.asList("Java", "OOD", "Unix"));
		quiz1.setQuestionFormats(Arrays.asList("Multiple Choice"));
				
		Quiz quiz2=new Quiz();
		quiz2.setCategories(Arrays.asList("Course Content"));
		quiz2.setSubjects(Arrays.asList("Java"));
		quiz2.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
				
		Quiz quiz3=new Quiz();
		quiz3.setCategories(Arrays.asList("Course Content", "Interview"));
		quiz3.setSubjects(Arrays.asList("Unix", "SQL"));
		quiz3.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
				
		List<Quiz> quizzes= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2, quiz3));
		when(mockQuizService.findAllQuizzes()).thenReturn(quizzes);
		//act
		String result=quizController.sortQuizzesForViewQuiz(mockSession, categoryCheckbox, subjectCheckbox, formatCheckbox);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, quizzes);
		assertEquals(QuizController.VIEW_QUIZZES_VIEW, result);
	}
	
	@Test
	void sortQuizzezForViewQuiz_Only_SubjectChecked() {
		//arrange
		String categoryCheckbox="check";
		String subjectCheckbox="check,Unix";
		String formatCheckbox="check";
				
		Quiz quiz1=new Quiz();
		quiz1.setCategories(Arrays.asList("Course Content"));
		quiz1.setSubjects(Arrays.asList("Java", "OOD", "Unix"));
		quiz1.setQuestionFormats(Arrays.asList("Multiple Choice"));
				
		Quiz quiz2=new Quiz();
		quiz2.setCategories(Arrays.asList("Course Content"));
		quiz2.setSubjects(Arrays.asList("Java"));
		quiz2.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
				
		Quiz quiz3=new Quiz();
		quiz3.setCategories(Arrays.asList("Course Content", "Interview"));
		quiz3.setSubjects(Arrays.asList("Unix", "SQL"));
		quiz3.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
				
		List<Quiz> quizzes= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2, quiz3));
		when(mockQuizService.findAllQuizzes()).thenReturn(quizzes);
		//act
		quizzes.remove(quiz2);
		String result=quizController.sortQuizzesForViewQuiz(mockSession, categoryCheckbox, subjectCheckbox, formatCheckbox);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, quizzes);
		assertEquals(QuizController.VIEW_QUIZZES_VIEW, result);
	}
	
	@Test
	void sortQuizzezForViewQuiz_Only_Subject_And_Format_Checked() {
		//arrange
		String categoryCheckbox="check";
		String subjectCheckbox="check,Unix";
		String formatCheckbox="check,Short Answer";
				
		Quiz quiz1=new Quiz();
		quiz1.setCategories(Arrays.asList("Course Content"));
		quiz1.setSubjects(Arrays.asList("Java", "OOD", "Unix"));
		quiz1.setQuestionFormats(Arrays.asList("Multiple Choice"));
				
		Quiz quiz2=new Quiz();
		quiz2.setCategories(Arrays.asList("Course Content"));
		quiz2.setSubjects(Arrays.asList("Java"));
		quiz2.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
				
		Quiz quiz3=new Quiz();
		quiz3.setCategories(Arrays.asList("Course Content", "Interview"));
		quiz3.setSubjects(Arrays.asList("Unix", "SQL"));
		quiz3.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
				
		List<Quiz> quizzes= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2, quiz3));
		when(mockQuizService.findAllQuizzes()).thenReturn(quizzes);
		//act
		quizzes.remove(quiz2);
		quizzes.remove(quiz1);
		String result=quizController.sortQuizzesForViewQuiz(mockSession, categoryCheckbox, subjectCheckbox, formatCheckbox);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, quizzes);
		assertEquals(QuizController.VIEW_QUIZZES_VIEW, result);
	}
	
	@Test
	void sortQuizzezForViewQuiz_All_Checked() {
		//arrange
		String categoryCheckbox="check,Interview";
		String subjectCheckbox="check,Unix";
		String formatCheckbox="check,Short Answer";
				
		Quiz quiz1=new Quiz();
		quiz1.setCategories(Arrays.asList("Course Content"));
		quiz1.setSubjects(Arrays.asList("Java", "OOD", "Unix"));
		quiz1.setQuestionFormats(Arrays.asList("Multiple Choice"));
				
		Quiz quiz2=new Quiz();
		quiz2.setCategories(Arrays.asList("Course Content"));
		quiz2.setSubjects(Arrays.asList("Java"));
		quiz2.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
				
		Quiz quiz3=new Quiz();
		quiz3.setCategories(Arrays.asList("Course Content", "Interview"));
		quiz3.setSubjects(Arrays.asList("Unix", "SQL"));
		quiz3.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
				
		List<Quiz> quizzes= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2, quiz3));
		when(mockQuizService.findAllQuizzes()).thenReturn(quizzes);
		//act
		quizzes.remove(quiz2);
		quizzes.remove(quiz1);
		String result=quizController.sortQuizzesForViewQuiz(mockSession, categoryCheckbox, subjectCheckbox, formatCheckbox);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, quizzes);
		assertEquals(QuizController.VIEW_QUIZZES_VIEW, result);
	}
	
	@Test
	void sortQuizzezForViewQuiz_double_category() {
		//arrange
		String categoryCheckbox="check,Interview,Course Content";
		String subjectCheckbox="check";
		String formatCheckbox="check";
				
		Quiz quiz1=new Quiz();
		quiz1.setCategories(Arrays.asList("Course Content"));
		quiz1.setSubjects(Arrays.asList("Java", "OOD", "Unix"));
		quiz1.setQuestionFormats(Arrays.asList("Multiple Choice"));
				
		Quiz quiz2=new Quiz();
		quiz2.setCategories(Arrays.asList("Course Content"));
		quiz2.setSubjects(Arrays.asList("Java"));
		quiz2.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
				
		Quiz quiz3=new Quiz();
		quiz3.setCategories(Arrays.asList("Course Content", "Interview"));
		quiz3.setSubjects(Arrays.asList("Unix", "SQL"));
		quiz3.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
				
		List<Quiz> quizzes= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2, quiz3));
		when(mockQuizService.findAllQuizzes()).thenReturn(quizzes);
		//act
		String result=quizController.sortQuizzesForViewQuiz(mockSession, categoryCheckbox, subjectCheckbox, formatCheckbox);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, quizzes);
		assertEquals(QuizController.VIEW_QUIZZES_VIEW, result);
	}
	
	@Test
	void sortQuizzezForViewQuiz_one_category_triple_subject() {
		//arrange
		String categoryCheckbox="check,Course Content";
		String subjectCheckbox="check,OOD,Unix,SQL";
		String formatCheckbox="check";
				
		Quiz quiz1=new Quiz();
		quiz1.setCategories(Arrays.asList("Course Content"));
		quiz1.setSubjects(Arrays.asList("Java", "OOD", "Unix"));
		quiz1.setQuestionFormats(Arrays.asList("Multiple Choice"));
				
		Quiz quiz2=new Quiz();
		quiz2.setCategories(Arrays.asList("Course Content"));
		quiz2.setSubjects(Arrays.asList("Java"));
		quiz2.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
				
		Quiz quiz3=new Quiz();
		quiz3.setCategories(Arrays.asList("Course Content", "Interview"));
		quiz3.setSubjects(Arrays.asList("Unix", "SQL"));
		quiz3.setQuestionFormats(Arrays.asList("Multiple Choice", "Short Answer"));
				
		List<Quiz> quizzes= new ArrayList<Quiz>(Arrays.asList(quiz1, quiz2, quiz3));
		when(mockQuizService.findAllQuizzes()).thenReturn(quizzes);
		//act
		quizzes.remove(quiz2);
		String result=quizController.sortQuizzesForViewQuiz(mockSession, categoryCheckbox, subjectCheckbox, formatCheckbox);
		//assert
		verify(mockSession, times(1)).setAttribute(QuizController.QUIZ_LIST_SESSION_ATTR, quizzes);
		assertEquals(QuizController.VIEW_QUIZZES_VIEW, result);
	}
	
	@Test
	void cancelAndReturnhome_removes_attributes_and_returns_home() {
		//arrange
		InOrder inOrder= inOrder(mockSession);
		//act
		String result= quizController.cancelAndReturnHome(mockSession);
		//assert
		inOrder.verify(mockSession, times(1)).removeAttribute(QuizController.QUIZ_LIST_SESSION_ATTR);
		inOrder.verify(mockSession, times(1)).removeAttribute(QuizController.ADDED_QUESTIONS_LIST_SESSION_ATTR);
		inOrder.verify(mockSession, times(1)).removeAttribute(QuizController.SORTED_QUESTIONS_SESSION_ATTR);
		inOrder.verify(mockSession, times(1)).removeAttribute(QuizController.QUIZ_TO_EDIT_SESSION_ATTR);
		assertEquals(QuizController.HOME_VIEW, result);
	}
}
