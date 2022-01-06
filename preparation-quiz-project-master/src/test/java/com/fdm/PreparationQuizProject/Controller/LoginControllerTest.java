package com.fdm.PreparationQuizProject.Controller;

import com.fdm.PreparationQuizProject.Model.User;
import com.fdm.PreparationQuizProject.Service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

import static com.fdm.PreparationQuizProject.Controller.LoginController.ERROR_MESSAGE;
import static org.mockito.ArgumentMatchers.any;

class LoginControllerTest {

    @Mock
    private UserService userService;

    @Mock
    Model mockModel;

    @Mock
    HttpSession mockSession;
    private LoginController loginController;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
        loginController = new LoginController(userService);
    }

    @Test
    public void test_processLogin_withValidUser() {
        //ARRANGE
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRegistered(true);
        user.setStatus("ACTIVE");
        Mockito.when(userService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);

        //ACT
        String url = loginController.processLogin(user, mockModel, mockSession);

        //ASSERT
        Mockito.verify(userService).findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        Mockito.verify(mockSession).setAttribute(LoginController.USER, user);

        Assertions.assertEquals("home", url);
    }

    @Test
    public void test_processLogin_invalidUser() {
        //ARRANGE
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRegistered(true);
        user.setStatus("ACTIVE");
        Mockito.when(userService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(null);

        //ACT
        String url = loginController.processLogin(user, mockModel, mockSession);

        //ASSERT
        Mockito.verify(userService).findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        Mockito.verify(mockModel).addAttribute(ERROR_MESSAGE, "Invalid Username or Password");

        Assertions.assertEquals("login", url);
    }

    @Test
    public void test_processLogin_absentUser() {
        //ARRANGE
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRegistered(true);
        user.setStatus("Absent");
        Mockito.when(userService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);

        //ACT
        String url = loginController.processLogin(user, mockModel, mockSession);

        //ASSERT
        Mockito.verify(userService).findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        Mockito.verify(mockModel).addAttribute(ERROR_MESSAGE, "Please wait for an admin to approve your account");

        Assertions.assertEquals("login", url);
    }

    @Test
    public void test_fetchLoginPage() {
        //ARRANGE
        //ACT
        String result = loginController.fetchLoginPage(mockModel, mockSession);

        //ASSERT
        Mockito.verify(mockModel).addAttribute(any(String.class), any(User.class));
        Assertions.assertEquals(LoginController.LOGIN, result);
    }

    @Test
    public void test_fetchLoginPage_WhenUserIsInSession() {
        //ARRANGE
        User user = new User();
        user.setUsername("username");

        Mockito.when(mockSession.getAttribute(LoginController.USER)).thenReturn(user);
        //ACT
        String result = loginController.fetchLoginPage(mockModel, mockSession);

        //ASSERT
        Assertions.assertEquals("accessError", result);
    }

    @Test
    public void test_processLogout() {
        //ARRANGE
        //ACT
        String result = loginController.processLogout(mockModel, mockSession);

        //ASSERT
        Mockito.verify(mockSession).invalidate();
        Assertions.assertEquals("home", result);
    }
}
