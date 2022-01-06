package com.fdm.PreparationQuizProject.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.PreparationQuizProject.Dal.UserRepository;
import com.fdm.PreparationQuizProject.Model.User;

@Service
public class UserService {
	private UserRepository userRepo;

	@Autowired
	public UserService(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	public User save(User toBeSaved) {
		return userRepo.save(toBeSaved);
	}

	public List<User> findAllUsers() {
		return userRepo.findAll();
	}

	public User updateUser(User currentUser) {
		return userRepo.save(currentUser);
	}

	public void deleteUser(User user) {
		userRepo.delete(user);
	}

	public User findById(int id) {
		Optional<User> foundUser = userRepo.findById(id);
		if (foundUser.isEmpty()) {
			return null;
		}
		return foundUser.get();
	}

	public boolean checkUsername(User user) {
		if (user.getUsername().length() < 5) {
			return false;
		}
		return true;
	}

	public boolean checkPassword(User user) {
		if (user.getPassword().length() < 5) {
			return false;
		}
		return true;
	}

	public boolean checkUsernameAndPassword(User user) {
		if (user.getUsername().length() >= 5 && user.getPassword().length() >= 5) {
			return true;
		}
		return false;
	}

	public boolean checkEmail(User user) {
		if (user.getEmail().contains("@") && (user.getEmail().contains(".com"))) {
			return true;
		} else {
			return false;
		}
	}

	public User findUserByUsernameAndPassword(String username, String password) {
		Optional<User> user = userRepo.findByUsernameAndPassword(username, password);
		return user.orElse(null);
	}

	public User registerUser(User inputUser) {
		Optional<User> user = userRepo.findByUsername(inputUser.getUsername());
		if (user.isPresent()) {
			return null;
		} else {
			inputUser.setStatus("Ongoing");
			userRepo.save(inputUser);
			return inputUser;
		}
	}

	public User checkAndSave(User toBeProcessed) {
		if (userRepo.countByUsername(toBeProcessed.getUsername()) == 0) {
			return userRepo.save(toBeProcessed);
		} else {
			return null;
		}
	}

	public List<User> findByRole(String role) {
		return userRepo.findByRole(role);
	}

	public List<User> findByRoleAndLastNameIgnoreCase(String role, String lName) {
		return userRepo.findByRoleAndLastNameIgnoreCase(role, lName);
	}

	public List<User> findByRoleAndFirstNameIgnoreCase(String role, String fName) {
		return userRepo.findByRoleAndFirstNameIgnoreCase(role, fName);
	}

	public List<User> findByRoleAndFirstNameAndLastNameIgnoreCase(String role, String fName, String lName) {
		return userRepo.findByRoleAndFirstNameAndLastNameIgnoreCase(role, fName, lName);
	}

	public List<User> findByRoleAndIsRegistered(String role, boolean isRegistered) {
		return userRepo.findByRoleAndIsRegistered(role, isRegistered);
	}

	public User findByUsername(String username) {
		Optional<User> user = userRepo.findByUsername(username);
		return user.orElse(null);
	}

}
