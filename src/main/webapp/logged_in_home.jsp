<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Book Rental - Home</title>
</head>
<body>
    <header>
        <h1>E-Book Rental</h1>
        <nav>
            <ul>

                <li><a href="BrowseBooksServlet">Browse Books</a></li>
                <li><a href="MyAccountServlet">My Account</a></li>
                <li><a href="LogOutServlet">Logout</a></li>
            </ul>
        </nav>
    </header>

    <main>
        <h2>Welcome to E-Book Rental, ${username}!</h2>
        <p>This is the home page for logged-in users.</p>
        <!-- You can place additional content here -->
    </main>

    <footer>
        <p>&copy; 2024 E-Book Rental. All rights reserved.</p>
    </footer>
</body>
</html>
