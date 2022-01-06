package com.fdm.PreparationQuizProject.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fdm.PreparationQuizProject.Dal.UserRepository;
import com.fdm.PreparationQuizProject.Model.User;

class UserServiceTest {
	private UserService userService;
	private User user;
	private User user2;
	private List<User> users;
	
	
	@Mock
	UserRepository mockUserRepo;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		userService = new UserService(mockUserRepo);
		user = new User();
		user2 = new User();
		users = new ArrayList<>(Arrays.asList(user,user2));
	}
	
	@Test
	void saveUser_return_user() {
		// ARRANGE
		when(mockUserRepo.save(user)).thenReturn(user);
		// ACT
		User result = userService.save(user);
		// ASSERT
		verify(mockUserRepo,times(1)).save(user);
		
		assertEquals(user,result);
	}
	
	
	@Test
	void findAllUsers_return_userList() {
		// ARRANGE
		when(mockUserRepo.findAll()).thenReturn(users);
		// ACT
		List<User> result = userService.findAllUsers();
		// ASSERT
		verify(mockUserRepo,times(1)).findAll();
		
		assertEquals(users,result);
	}
	
	@Test
	void updateUser_return_user() {
		// ARRANGE
		when(mockUserRepo.save(user)).thenReturn(user);
		// ACT
		User result = userService.updateUser(user);
		// ASSERT
		verify(mockUserRepo,times(1)).save(user);
		
		assertEquals(user,result);
	}
	
	@Test
	void deleteUser() {
		// ARRANGE
		// ACT
		userService.deleteUser(user);
		// ASSERT
		verify(mockUserRepo,times(1)).delete(user);
	}
	
	@Test
	void findById_return_null_foundUserIsEmpty() {
		// ARRANGE
		Optional <User> foundUser = Optional.ofNullable(null);
		when(mockUserRepo.findById(1)).thenReturn(foundUser);
		// ACT
		User result = userService.findById(1);
		// ASSERT
		verify(mockUserRepo,times(1)).findById(1);
		
		assertEquals(null,result);
	}
	
	@Test
	void findById_return_user_foundUserIsNotEmpty() {
		// ARRANGE
		Optional <User> foundUser = Optional.of(user);
		when(mockUserRepo.findById(1)).thenReturn(foundUser);
		// ACT
		User result = userService.findById(1);
		// ASSERT
		verify(mockUserRepo,times(1)).findById(1);
		
		assertEquals(user,result);
	}
	
	@Test
	void checkUsername_return_false_username_lengthLessThanFive() {
		// ARRANGE
		user.setUsername("aaaa");
		// ACT
		boolean result = userService.checkUsername(user);
		// ASSERT		
		assertEquals(false,result);
	}
	
	@Test
	void checkUsername_return_true_username_lengthEqualsFive() {
		// ARRANGE
		user.setUsername("aaaaa");
		// ACT
		boolean result = userService.checkUsername(user);
		// ASSERT		
		assertEquals(true,result);
	}
	
	@Test
	void checkUsername_return_true_username_lengthLargerThanFive() {
		// ARRANGE
		user.setUsername("aaaaaa");
		// ACT
		boolean result = userService.checkUsername(user);
		// ASSERT		
		assertEquals(true,result);
	}
	
	@Test
	void checkPassword_return_false_username_lengthLessThanFive() {
		// ARRANGE
		user.setPassword("aaaa");
		// ACT
		boolean result = userService.checkPassword(user);
		// ASSERT		
		assertEquals(false,result);
	}
	
	@Test
	void checkPassword_return_true_username_lengthEqualsFive() {
		// ARRANGE
		user.setPassword("aaaaa");
		// ACT
		boolean result = userService.checkPassword(user);
		// ASSERT		
		assertEquals(true,result);
	}
	
	@Test
	void checkPassword_return_true_username_lengthLargerThanFive() {
		// ARRANGE
		user.setPassword("aaaaaa");
		// ACT
		boolean result = userService.checkPassword(user);
		// ASSERT		
		assertEquals(true,result);
	}
	
	@Test
	void checkUsernameAndPassword_return_false_username_bothLengthLessThanFive() {
		// ARRANGE
		user.setUsername("aaaa");
		user.setPassword("aaaa");
		// ACT
		boolean result = userService.checkUsernameAndPassword(user);
		// ASSERT		
		assertEquals(false,result);
	}
	
	@Test
	void checkUsernameAndPassword_return_false_username_onlyUsernameLengthLessThanFive() {
		// ARRANGE
		user.setUsername("aaaa");
		user.setPassword("aaaaa");
		// ACT
		boolean result = userService.checkUsernameAndPassword(user);
		// ASSERT		
		assertEquals(false,result);
	}
	
	@Test
	void checkUsernameAndPassword_return_false_username_onlyPasswordLengthLessThanFive() {
		// ARRANGE
		user.setUsername("aaaaa");
		user.setPassword("aaaa");
		// ACT
		boolean result = userService.checkUsernameAndPassword(user);
		// ASSERT		
		assertEquals(false,result);
	}
	
	@Test
	void checkUsernameAndPassword_return_true_username_bothLengthEqualsFive() {
		// ARRANGE
		user.setUsername("aaaaa");
		user.setPassword("aaaaa");
		// ACT
		boolean result = userService.checkUsernameAndPassword(user);
		// ASSERT		
		assertEquals(true,result);
	}
	
	@Test
	void checkUsernameAndPassword_return_true_username_bothLengthLongerThanFive() {
		// ARRANGE
		user.setUsername("aaaaaa");
		user.setPassword("aaaaaa");
		// ACT
		boolean result = userService.checkUsernameAndPassword(user);
		// ASSERT		
		assertEquals(true,result);
	}
	
	@Test
	void checkEmail_return_false_failingBothCondition() {
		// ARRANGE
		user.setEmail("aaaaa");
		// ACT
		boolean result = userService.checkEmail(user);
		// ASSERT		
		assertEquals(false,result);
	}
	
	@Test
	void checkEmail_return_false_failingTheFirstCondition() {
		// ARRANGE
		user.setEmail("aaaaa.com");
		// ACT
		boolean result = userService.checkEmail(user);
		// ASSERT		
		assertEquals(false,result);
	}
	
	@Test
	void checkEmail_return_false_failingTheSecondCondition() {
		// ARRANGE
		user.setEmail("aa@aaa");
		// ACT
		boolean result = userService.checkEmail(user);
		// ASSERT		
		assertEquals(false,result);
	}
	
	@Test
	void checkEmail_return_true_PassingBothCondition() {
		// ARRANGE
		user.setEmail("aaaaa@a.com");
		// ACT
		boolean result = userService.checkEmail(user);
		// ASSERT		
		assertEquals(true,result);
	}
	
	@Test
	void findUserByUsernameAndPassword_return_null_foundUserIsEmpty() {
		// ARRANGE
		Optional <User> foundUser = Optional.ofNullable(null);
		when(mockUserRepo.findByUsernameAndPassword("a","b")).thenReturn(foundUser);
		// ACT
		User result = userService.findUserByUsernameAndPassword("a","b");
		// ASSERT
		verify(mockUserRepo,times(1)).findByUsernameAndPassword("a","b");
		
		assertEquals(null,result);
	}
	
	@Test
	void findUserByUsernameAndPassword_return_user_foundUserIsNotEmpty() {
		// ARRANGE
		Optional <User> foundUser = Optional.of(user);
		when(mockUserRepo.findByUsernameAndPassword("a","b")).thenReturn(foundUser);
		// ACT
		User result = userService.findUserByUsernameAndPassword("a","b");
		// ASSERT
		verify(mockUserRepo,times(1)).findByUsernameAndPassword("a","b");
		
		assertEquals(user,result);
	}
	
	@Test
	void registerUser_return_null_findByUsernameGotUser() {
		// ARRANGE
		Optional <User> foundUser = Optional.of(user2);
		user.setUsername("a");
		when(mockUserRepo.findByUsername("a")).thenReturn(foundUser);
		// ACT
		User result = userService.registerUser(user);
		// ASSERT
		verify(mockUserRepo,times(1)).findByUsername("a");
		
		assertEquals(null,result);
	}
	
	@Test
	void registerUser_return_null_findByUsernameGotNotUser() {
		// ARRANGE
		Optional <User> foundUser = Optional.ofNullable(null);
		user.setUsername("a");
		when(mockUserRepo.findByUsername("a")).thenReturn(foundUser);
		// ACT
		User result = userService.registerUser(user);
		// ASSERT
		verify(mockUserRepo,times(1)).findByUsername("a");
		
		assertEquals(user,result);
	}
	
	@Test
	void checkAndSave_return_user_countByUsernameIsZero() {
		// ARRANGE
		user.setUsername("a");
		when(mockUserRepo.countByUsername("a")).thenReturn(0);
		when(mockUserRepo.save(user)).thenReturn(user);
		// ACT
		User result = userService.checkAndSave(user);
		// ASSERT
		verify(mockUserRepo,times(1)).countByUsername("a");
		verify(mockUserRepo,times(1)).save(user);

		assertEquals(user,result);
	}
	
	@Test
	void checkAndSave_return_null_countByUsernameIsNotZero() {
		// ARRANGE
		user.setUsername("a");
		when(mockUserRepo.countByUsername("a")).thenReturn(1);
		// ACT
		User result = userService.checkAndSave(user);
		// ASSERT
		verify(mockUserRepo,times(1)).countByUsername("a");
		
		assertEquals(null,result);
	}
	
	@Test
	void findByRole_return_users() {
		// ARRANGE
		when(mockUserRepo.findByRole("Trainer")).thenReturn(users);
		// ACT
		List<User> result = userService.findByRole("Trainer");
		// ASSERT
		verify(mockUserRepo,times(1)).findByRole("Trainer");
		
		assertEquals(users,result);
	}
	
	@Test
	void findByRoleAndLastNameIgnoreCase_return_users() {
		// ARRANGE
		when(mockUserRepo.findByRoleAndLastNameIgnoreCase("Trainer","lName")).thenReturn(users);
		// ACT
		List<User> result = userService.findByRoleAndLastNameIgnoreCase("Trainer","lName");
		// ASSERT
		verify(mockUserRepo,times(1)).findByRoleAndLastNameIgnoreCase("Trainer","lName");
		
		assertEquals(users,result);
	}
	
	@Test
	void findByRoleAndFirstNameIgnoreCase_return_users() {
		// ARRANGE
		when(mockUserRepo.findByRoleAndFirstNameIgnoreCase("Trainer","fName")).thenReturn(users);
		// ACT
		List<User> result = userService.findByRoleAndFirstNameIgnoreCase("Trainer","fName");
		// ASSERT
		verify(mockUserRepo,times(1)).findByRoleAndFirstNameIgnoreCase("Trainer","fName");
		
		assertEquals(users,result);
	}
	
	@Test
	void findByRoleAndFirstNameAndLastNameIgnoreCase_return_users() {
		// ARRANGE
		when(mockUserRepo.findByRoleAndFirstNameAndLastNameIgnoreCase("Trainer","fName","lName")).thenReturn(users);
		// ACT
		List<User> result = userService.findByRoleAndFirstNameAndLastNameIgnoreCase("Trainer","fName","lName");
		// ASSERT
		verify(mockUserRepo,times(1)).findByRoleAndFirstNameAndLastNameIgnoreCase("Trainer","fName","lName");
		
		assertEquals(users,result);
	}
	
	@Test
	void findByRoleAndIsRegistered_return_users() {
		// ARRANGE
		when(mockUserRepo.findByRoleAndIsRegistered("Trainer",true)).thenReturn(users);
		// ACT
		List<User> result = userService.findByRoleAndIsRegistered("Trainer",true);
		// ASSERT
		verify(mockUserRepo,times(1)).findByRoleAndIsRegistered("Trainer",true);
		
		assertEquals(users,result);
	}
	
	@Test
	void findByUsername_return_null_foundUserIsEmpty() {
		// ARRANGE
		Optional <User> foundUser = Optional.ofNullable(null);
		when(mockUserRepo.findByUsername("username")).thenReturn(foundUser);
		// ACT
		User result = userService.findByUsername("username");
		// ASSERT
		verify(mockUserRepo,times(1)).findByUsername("username");
		
		assertEquals(null,result);
	}
	
	@Test
	void findByUsername_return_user_foundUserIsNotEmpty() {
		// ARRANGE
		Optional <User> foundUser = Optional.of(user);
		when(mockUserRepo.findByUsername("username")).thenReturn(foundUser);
		// ACT
		User result = userService.findByUsername("username");
		// ASSERT
		verify(mockUserRepo,times(1)).findByUsername("username");
		
		assertEquals(user,result);
	}
	
}
