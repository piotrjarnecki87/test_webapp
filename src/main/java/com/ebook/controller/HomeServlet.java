package com.ebook.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet(name = "HomeServlet", urlPatterns = "/HomeServlet")
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("id");

        if (userId != null) {
            request.getRequestDispatcher("/logged_in_home.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/normal_home.jsp").forward(request, response);
        }
    }
}
