package com.ebook.controller;

import com.ebook.dao.impl.BookOrderDAOImpl;
import com.ebook.database.ConnectionPool;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "ReturnBookServlet", urlPatterns = "/ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BookOrderDAOImpl rentalDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        Connection connection = ConnectionPool.getInstance().getConnection();
        rentalDAO = new BookOrderDAOImpl(connection);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paramName = request.getParameterNames().nextElement(); // Pobierz pierwszy parametr z żądania
        int rentalsId = Integer.parseInt(request.getParameter(paramName));

        try {
            // Usunięcie książki z listy wypożyczonych
            rentalDAO.updateReturnDate(rentalsId, new Date(System.currentTimeMillis()));

            // Przekierowanie użytkownika na stronę przeglądania książek po usunięciu
            response.sendRedirect(request.getContextPath() + "/MyAccountServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while returning the book.");
        }
    }
}
