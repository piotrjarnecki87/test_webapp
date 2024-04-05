<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Account - E-Book Rental</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
    </style>
</head>
<body>
<header>
    <h1>E-Book Rental</h1>
    <nav>
        <ul>
            <li><a href="HomeServlet">Dashboard</a></li>
            <li><a href="BrowseBooksServlet">Browse Books</a></li>
            <li><a href="LogOutServlet">Logout</a></li>
        </ul>
    </nav>
</header>

<main>
    <h2>My Account - Ordered Books</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Author</th>
                <th>Title</th>
                <th>Year</th>
                <th>Rental Date</th>
                <th>Return Date</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <!-- Iterating through the list of ordered books -->
          <c:forEach items="${orderedBooks}" var="bookRental" varStatus="status">
              <tr>
                  <td>${status.index + 1}</td>
                  <td>${bookRental.book.title}</td>
                  <td>${bookRental.book.author}</td>
                  <td>${bookRental.book.yearOfPublication}</td>
                  <td><fmt:formatDate value="${bookRental.rentalDate}" pattern="yyyy-MM-dd"/></td>
                  <td><fmt:formatDate value="${bookRental.returnDate}" pattern="yyyy-MM-dd"/></td>
                  <td>
                      <c:if test="${empty bookRental.returnDate}">
                          <form action="ReturnBookServlet" method="post">
                              <input type="hidden" name="bookRental_id" value="${bookRental.rentalsId}">
                              <button type="submit">Return Book</button>
                          </form>
                      </c:if>
                  </td>
              </tr>
          </c:forEach>



        </tbody>
    </table>
</main>

<footer>
    <p>&copy; 2024 E-Book Rental. All rights reserved. Current date: <%= new java.util.Date() %></p>
</footer>
</body>
</html>
