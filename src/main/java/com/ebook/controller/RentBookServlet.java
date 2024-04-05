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

@WebServlet(name = "RentBookServlet", urlPatterns = "/RentBookServlet")
public class RentBookServlet extends HttpServlet {

    private static BookOrderDAOImpl rentalDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        // Inicjalizacja RentalDAOImpl z połączeniem
        Connection connection = ConnectionPool.getInstance().getConnection();
        rentalDAO = new BookOrderDAOImpl(connection);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Pobranie identyfikatora książki z żądania
        String paramName = request.getParameterNames().nextElement(); // Pobierz pierwszy parametr z żądania
        int bookId = Integer.parseInt(request.getParameter(paramName));

        // Pobranie identyfikatora użytkownika (może być pobierane z sesji lub z innych danych uwierzytelniających)
        int userId = 1; // Załóżmy, że identyfikator użytkownika to 1

        try {
            // Dodanie wypożyczenia do bazy danych
            rentalDAO.rentBook(userId, bookId, new Date(System.currentTimeMillis())); // Poprawka: Przekazujemy aktualną datę jako datę wypożyczenia

            // Przekierowanie użytkownika na stronę przeglądania książek po udanym wypożyczeniu
            response.sendRedirect(request.getContextPath() + "/BrowseBooksServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while renting the book.");
        }
    }

}