<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Browse Books - E-Book Rental</title>
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
            <li><a href="MyAccountServlet">My Account</a></li>
            <li><a href="LogOutServlet">Logout</a></li>
        </ul>
    </nav>
</header>

<main>
    <h2>Browse Books</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Year</th>

                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${books}" var="book">
                <tr>
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.yearOfPublication}</td>
                    <td>
                        <c:set var="rented" value="false"/>
                        <c:forEach items="${orderedBooks}" var="orderedBook">
                            <c:if test="${orderedBook.book.id eq book.id and orderedBook.returnDate eq null}">
                                <c:set var="rented" value="true"/>
                            </c:if>
                        </c:forEach>
                        <c:if test="${not rented}">
                            <form action="RentBookServlet" method="post">
                                <input type="hidden" name="bookId" value="${book.id}">
                                <button type="submit">Rent</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>

        </tbody>
    </table>
</main>

<footer>
    <p>&copy; 2024 E-Book Rental. All rights reserved.</p>
</footer>
</body>
</html>
