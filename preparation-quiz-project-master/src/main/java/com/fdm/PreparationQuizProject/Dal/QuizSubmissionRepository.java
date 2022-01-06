package com.fdm.PreparationQuizProject.Dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.PreparationQuizProject.Model.QuizSubmission;
import com.fdm.PreparationQuizProject.Model.User;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Integer>{

	List<QuizSubmission> findByStudent(User student);

}
