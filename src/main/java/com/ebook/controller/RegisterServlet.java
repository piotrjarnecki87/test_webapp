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

        private UserDAOImpl userDao;

        public void init() throws ServletException {
                super.init();
                // Inicjalizacja userDao przy użyciu połączenia z bazą danych
                Connection connection = ConnectionPool.getInstance().getConnection();
                userDao = new UserDAOImpl(connection);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                // Pobierz dane z formularza rejestracji
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String firstName = request.getParameter("first_name");
                String lastName = request.getParameter("last_name");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");

                try {
                        // Utwórz nowy obiekt użytkownika na podstawie danych z formularza
                        User user = new User(0, username, password, firstName, lastName, email, Integer.parseInt(phone));

                        // Dodaj użytkownika do bazy danych
                        userDao.addUser(user);

                        // Przekieruj użytkownika na stronę logowania po zarejestrowaniu
                        response.sendRedirect(request.getContextPath() + "/login.jsp");
                } catch (NumberFormatException e) {
                        e.printStackTrace();
                        // Obsłuż błąd, jeśli nie udało się przekształcić ciągu znaków na liczbę
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid phone number format.");
                } catch (SQLException e) {
                        e.printStackTrace();
                        // Obsłuż błąd, jeśli nie udało się zapisać użytkownika
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while registering user.");
                }
        }

}
