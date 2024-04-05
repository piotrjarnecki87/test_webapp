package com.ebook.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogOutServlet", urlPatterns = "/LogOutServlet")
public class LogOutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Pobieramy sesję
        HttpSession session = request.getSession();

        // Usuwamy atrybut sesji zawierający informacje o zalogowanym użytkowniku
        session.removeAttribute("username");

        // Przekierowujemy użytkownika na stronę logowania po wylogowaniu
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Wywołujemy metodę doGet, aby obsłużyć wylogowanie
        doGet(request, response);
    }
}
