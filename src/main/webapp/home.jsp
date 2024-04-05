<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    response.sendRedirect(request.getContextPath() + "/HomeServlet");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Book Rental</title>
</head>
<body>
    <header>
        <h1>E-Book Rental</h1>
        <nav>
            <ul>
                <li><a href="LoginServlet">Login</a></li>
                <li><a href="RegisterServlet">Register</a></li>
            </ul>
        </nav>
    </header>

    <main>
        <h2>Welcome to E-Book Rental!</h2>
        <!-- You can place the main content of the home page here if needed -->
    </main>

    <footer>
        <p>&copy; 2024 E-Book Rental. All rights reserved.</p>
    </footer>
</body>
</html>
