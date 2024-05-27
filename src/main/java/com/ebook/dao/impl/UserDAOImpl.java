package com.ebook.dao.impl;

import com.ebook.dao.AbstractJdbcDao;
import com.ebook.dao.UserDAO;

import com.ebook.model.User;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class UserDAOImpl extends AbstractJdbcDao implements UserDAO {
    private Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addUser(User user) throws SQLException {
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO clients (username, password, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getPhoneNumber());

            // Wykonanie zapytania
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(String username) {

    }


    @Override
    public User getUserByUsername(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            // Zapytanie do pobrania użytkownika na podstawie nazwy użytkownika
            String query = "SELECT * FROM clients WHERE username = ?";
            statement = this.connection.prepareStatement(query);
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            // Jeśli znaleziono użytkownika, przypisz go do obiektu User
            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getInt("phone")
                );

                // Dodaj więcej pól, jeśli istnieją
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Zamknięcie połączenia i innych zasobów JDBC
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }


    @Override
    public boolean isValidLogin(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Zapytanie do sprawdzenia poprawności danych logowania
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            statement = this.connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            resultSet = statement.executeQuery();

            return resultSet.next(); // Zwróć true, jeśli znaleziono użytkownika o podanych danych logowania
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void rentBook(int userId, int bookId, Date rentalDate) throws SQLException {

    }

    @Override
    public void deleteBookRental(int bookId) throws SQLException {

    }

    @Override
    public void updateReturnDate(int rentalsId, Date returnDate) throws SQLException {

    }

    @Override
    protected String getTableName() {
        return null;
    }

    @Override
    protected String getIdColumnName() {
        return null;
    }

    @Override
    protected Object mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public void add(Object entity) {

    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Object getById(int id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }
}