<!-- register.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
</head>
<body>
    <h2>Registration Page</h2>
    <form action="RegisterServlet" method="post">
            Username: <input type="text" name="username"><br>
            Password: <input type="password" name="password"><br>
            First Name: <input type="text" name="first_name"><br>
            Last Name: <input type="text" name="last_name"><br>
            Email: <input type="text" name="email"><br>
            Phone Number: <input type="text" name="phone"><br>
            <input type="submit" value="Register">
        </form>
</body>
</html>
