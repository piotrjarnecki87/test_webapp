package com.ebook.controller;

import com.ebook.dao.impl.BookDAOImpl;
import com.ebook.dao.impl.BookOrderDAOImpl;
import com.ebook.database.ConnectionPool;
import com.ebook.model.Book;

import com.ebook.model.BookRental;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BrowseBooksServlet", urlPatterns = "/BrowseBooksServlet")
public class BrowseBooksServlet extends HttpServlet {

    private static BookDAOImpl bookDAO;
    private static BookOrderDAOImpl bookOrderDAO;
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Initialize BookDAOImpl with a connection
            Connection connection = ConnectionPool.getInstance().getConnection();
            bookDAO = new BookDAOImpl(connection);
            bookOrderDAO = new BookOrderDAOImpl(connection);

        } catch (ClassNotFoundException e) {
            throw new ServletException("Failed to initialize DAO", e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userId = 1;
            List<Book> books = bookDAO.getAllBooks(); // Pobranie listy książek z DAO
            List<BookRental> orderedBooks = bookOrderDAO.getOrderedBooksByUserId(userId);

            request.setAttribute("books", books); // Przekazanie listy książek do JSP
            request.setAttribute("orderedBooks", orderedBooks);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/browse_books.jsp");
            dispatcher.forward(request, response); // Przekierowanie do strony JSP
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving books.");
        }
    }
}

