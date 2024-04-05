package com.ebook.dao.impl;

import com.ebook.dao.AbstractJdbcDao;
import com.ebook.dao.BookOrderDAO;
import com.ebook.model.Book;
import com.ebook.model.BookRental;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookOrderDAOImpl extends AbstractJdbcDao implements BookOrderDAO {

    private Connection connection;

    public BookOrderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<BookRental> getOrderedBooksByUserId(int userId) throws SQLException {
        List<BookRental> orderedBooks = new ArrayList<>();

        String sql = "SELECT r.id as rentalsId, b.id, b.title, b.author, b.year, r.rental_date, r.return_date FROM rentals r JOIN books b ON r.book_id = b.id WHERE r.client_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int rentalsId = resultSet.getInt("rentalsId");
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("year")
                );
                Date rentalDate = resultSet.getDate("rental_date");
                Date returnDate = resultSet.getDate("return_date");

                BookRental bookRental = new BookRental(rentalsId, book, rentalDate, returnDate);
                orderedBooks.add(bookRental);
            }
        }

        return orderedBooks;
    }
    @Override
    public void rentBook(int userId, int bookId, Date rentalDate) throws SQLException {
        String sql = "INSERT INTO rentals (client_id, book_id, rental_date) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, bookId);
            statement.setDate(3, new java.sql.Date(rentalDate.getTime()));
            statement.executeUpdate();
        }
    }
    @Override
    public void deleteBookRental(int bookId) throws SQLException {
        String sql = "DELETE FROM rentals WHERE book_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            statement.executeUpdate();
        }
    }



    @Override
    public void updateReturnDate(int rentalsId, Date returnDate) throws SQLException {
        String sql = "UPDATE rentals SET return_date = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(returnDate.getTime()));
            statement.setInt(2, rentalsId);

            statement.executeUpdate();
        }
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
}