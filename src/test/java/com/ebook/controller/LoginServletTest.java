//package com.ebook.controller;
//import com.ebook.dao.UserDAO;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import static org.mockito.Mockito.*;
//
//public class LoginServletTest {
//
//    @Mock
//    UserDAO userDAO;
//
//    @Mock
//    HttpServletRequest request;
//
//    @Mock
//    HttpServletResponse response;
//
//    @Mock
//    HttpSession session;
//
//    @InjectMocks
//    LoginServlet loginServlet;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testDoPostWithValidCredentials() throws Exception {
//        // Arrange
//        when(request.getParameter("username")).thenReturn("testuser");
//        when(request.getParameter("password")).thenReturn("password");
//        when(request.getSession()).thenReturn(session);
//        when(userDAO.isValidLogin("testuser", "password")).thenReturn(true);
//
//        // Act
//        loginServlet.doPost(request, response);
//
//        // Assert
//        verify(session).setAttribute("username", "testuser");
//        verify(response).sendRedirect("/home");
//    }
//
//    @Test
//    public void testDoPostWithInvalidCredentials() throws Exception {
//        // Arrange
//        when(request.getParameter("username")).thenReturn("testuser");
//        when(request.getParameter("password")).thenReturn("invalidpassword");
//        when(userDAO.isValidLogin("testuser", "invalidpassword")).thenReturn(false);
//
//        // Act
//        loginServlet.doPost(request, response);
//
//        // Assert
//        verify(response).sendRedirect("/login?error=invalid_credentials");
//    }
//}
