package com.ebook.controller;

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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "MyAccountServlet", urlPatterns = "/MyAccountServlet")
public class MyAccountServlet extends HttpServlet {

    private static BookOrderDAOImpl bookOrderDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = ConnectionPool.getInstance().getConnection();
            bookOrderDAO = new BookOrderDAOImpl(connection);
        } catch (ClassNotFoundException e) {
            throw new ServletException("Failed to initialize DAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            int userId = getUserIdFromSessionOrDatabase(request);

            List<BookRental> orderedBooks = bookOrderDAO.getOrderedBooksByUserId(userId);

            request.setAttribute("orderedBooks", orderedBooks);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/my_account.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving ordered books.");
        }
    }


    private int getUserIdFromSessionOrDatabase(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Integer userId = (Integer) session.getAttribute("id");
            if (userId != null) {
                return userId;
            }
        }
        return -1;
    }
}