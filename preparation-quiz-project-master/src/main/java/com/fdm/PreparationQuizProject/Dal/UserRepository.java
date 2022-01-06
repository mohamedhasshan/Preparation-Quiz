package com.fdm.PreparationQuizProject.Dal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.PreparationQuizProject.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsernameAndPassword(String username, String password);

	int countByUsername(String username);

	Optional<User> findById(int id);

	Optional<User> findByUsername(String userName);

	List<User> findByRole(String role);

	List<User> findByRoleAndLastNameIgnoreCase(String role, String lName);

	List<User> findByRoleAndFirstNameIgnoreCase(String role, String fName);

	List<User> findByRoleAndFirstNameAndLastNameIgnoreCase(String role, String fName, String lName);

	List<User> findByRoleAndIsRegistered(String role, boolean isRegistered);
}
