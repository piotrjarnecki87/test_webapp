package com.ebook.controller;

import com.ebook.dao.impl.UserDAOImpl;
import com.ebook.database.ConnectionPool;
import com.ebook.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {

        private UserDAOImpl userDao; //

        public void init() throws ServletException {
                super.init();
                try {
                        // Load the MySQL JDBC driver
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        // Initialize BookDAOImpl with a connection
                        Connection connection = ConnectionPool.getInstance().getConnection();
                        userDao = new UserDAOImpl(connection);


                } catch (ClassNotFoundException e) {
                        throw new ServletException("Failed to initialize DAO", e);
                }
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String firstName = request.getParameter("first_name");
                String lastName = request.getParameter("last_name");
                String email = request.getParameter("email");
                int phoneNumber = request.getIntHeader("phone");


                User user = new User();

                try {
                        userDao.addUser(user);

                        // Przekieruj użytkownika na stronę logowania po zarejestrowaniu
                        response.sendRedirect(request.getContextPath() + "/login.jsp");
                } catch (SQLException e) {
                        e.printStackTrace();
                        // Obsłuż błąd, jeśli nie udało się zapisać użytkownika
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while registering user.");
                }
        }
}
