package com.fdm.PreparationQuizProject.Dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.PreparationQuizProject.Model.QuestionSubmission;

@Repository
public interface QuestionSubmissionRepository extends JpaRepository<QuestionSubmission, Integer>{

}
