package com.fdm.PreparationQuizProject.Setup;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fdm.PreparationQuizProject.Model.Question;
import com.fdm.PreparationQuizProject.Model.Quiz;
import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.QuestionService;
import com.fdm.PreparationQuizProject.Service.QuizService;
import com.fdm.PreparationQuizProject.Service.UserService;


@Component
public class DataLoader implements ApplicationRunner{
	
	private Log log = LogFactory.getLog(DataLoader.class);
	private UserService userService;
	private QuestionService questionService;
	private QuizService quizService;
	
	@Autowired
	public DataLoader(UserService userService, QuestionService questionService, QuizService quizService) {
		this.userService = userService;
		this.questionService = questionService;
		this.quizService = quizService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		dataInit();
		
		log.info("http://localhost:8088/QuizSystem/");
	}
	
	public void dataInit() {
		User user1 = new User("a", "a", "Kai", "Lim", "abc@abc.com", "Student", "Ongoing", true);
		User user2 = new User("b", "b", "Yunheng", "Zheng", "abc@abc.com", "Trainer", "Ongoing", false);
		User user3 = new User("c", "c", "Nicholas", "Siew", "abc@abc.com", "Trainer", "Ongoing", true);
		User user4 = new User("d", "d", "Mohamed", "Hasshan", "abc@abc.com", "Sales", "Ongoing", true);
		User user5 = new User("e", "e", "Billy", "Wong", "user@12312312.com", "Sales", "Ongoing", false);
		User user6 = new User("f", "f", "James", "Bond" , "123@email.com", "Student" ,"Absent", true);
		userService.save(user1);
		userService.save(user2);
		userService.save(user3);
		userService.save(user4);
		userService.save(user5);
		userService.save(user6);
		
		Question question1 = new Question("OOD", "Course Content", "Short Answer", "What does 1 == 1 return?", 5, "true", null, user1);
		String[] answer2 = {"html", "python", "exe", "jsp"};
		Question question2 = new Question("Java", "Course Content", "Multiple Choice", "What do you use with Spring Web Mvc", 5, "jsp", Arrays.asList(answer2), user1);
		
		Question question3 = new Question("Soft Skill", "Interview Preparation", "Short Answer", "Do you need to speak English", 1, "yes", null, user3);
		String[] answer4 = {"Structured Query Language", "Strong Quick Language", "Some Query Language", "Javascript"};
		Question question4 = new Question("SQL", "Course Content", "Multiple Choice", "What is SQL", 5, "Structured Query Language", Arrays.asList(answer4), user3);
		
		Question question5 = new Question("UNIX", "Course Content", "Short Answer", "How to change directory", 2, "cd", null, user4);
		String[] answer6 = {"cd", "ls", "ps", "mv"};
		Question question6 = new Question("UNIX", "Course Content", "Multiple Choice", "How do you list files", 5, "ls", Arrays.asList(answer6), user4);
		
		Question question7 = new Question("SQL", "Course Content", "Short Answer", "How do you set condition", 3, "WHERE", null, user4);
		String[] answer8 = {"0", "1", "2", "3"};
		Question question8 = new Question("OOD", "Course Content", "Multiple Choice", "What is the index of the first item in a list", 2, "0", Arrays.asList(answer8), user4);
		
		questionService.saveQuestion(question1);
		questionService.saveQuestion(question2);
		questionService.saveQuestion(question3);
		questionService.saveQuestion(question4);
		questionService.saveQuestion(question5);
		questionService.saveQuestion(question6);
		questionService.saveQuestion(question7);
		questionService.saveQuestion(question8);
		
		
		Quiz quiz1 = new Quiz("OOD Quiz 1", user1, new ArrayList<>());
		quiz1.addQuestion(question1);
		quiz1.addQuestion(question8);
		
		Quiz quiz2 = new Quiz("Course Content Quiz 1", user3, new ArrayList<>());
		quiz2.addQuestion(question1);
		quiz2.addQuestion(question2);
		quiz2.addQuestion(question4);
		quiz2.addQuestion(question5);
		quiz2.addQuestion(question6);
		quiz2.addQuestion(question7);
		quiz2.addQuestion(question8);
		
		Quiz quiz3 = new Quiz("UNIX Practice Test 1", user4, new ArrayList<>());
		quiz3.addQuestion(question5);
		quiz3.addQuestion(question6);
		
		quiz1.update();
		quiz2.update();
		quiz3.update();

		quizService.save(quiz1);
		quizService.save(quiz2);
		quizService.save(quiz3);
		
	}

}
