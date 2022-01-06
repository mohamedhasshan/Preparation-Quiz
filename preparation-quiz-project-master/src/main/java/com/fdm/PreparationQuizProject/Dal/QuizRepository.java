package com.fdm.PreparationQuizProject.Dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.PreparationQuizProject.Model.Quiz;
import com.fdm.PreparationQuizProject.Model.User;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer>{
	List<Quiz> findByUser(User user);
	
	List<Quiz> findByCreaterRole(String createrRole);
}
