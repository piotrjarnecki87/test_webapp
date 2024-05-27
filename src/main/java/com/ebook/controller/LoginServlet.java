package com.ebook.controller;

import com.ebook.dao.UserDAO;
import com.ebook.dao.impl.UserDAOImpl;
import com.ebook.model.User;
import com.ebook.database.DatabaseConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static jakarta.servlet.http.HttpServletResponse.*;

@WebServlet(name = "LoginServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DatabaseConnectionFactory.getConnection();
            userDAO = new UserDAOImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Nie udało się zainicjować UserDAO", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User(0, username, password, null, null, null, 0);

        try {
            if (isValidLogin(user, request)) {
                response.setStatus(SC_OK);
                response.sendRedirect(request.getContextPath() + "/HomeServlet");
            } else {
                response.setStatus(SC_UNAUTHORIZED);
                response.sendRedirect(request.getContextPath() + "/login.jsp?error=invalid_credentials");
            }
        } catch (SQLException e) {
            throw new ServletException("Wystąpił błąd podczas weryfikacji logowania", e);
        }
    }

    private boolean isValidLogin(User user, HttpServletRequest request) throws SQLException {
        User fetchedUser = userDAO.getUserByUsername(user.getUsername());
        if (fetchedUser != null && fetchedUser.getPassword().equals(user.getPassword())) {
            int userId = fetchedUser.getId();
            HttpSession session = request.getSession();
            session.setAttribute("id", userId);
            return true;
        } else {
            return false;
        }
    }
}
