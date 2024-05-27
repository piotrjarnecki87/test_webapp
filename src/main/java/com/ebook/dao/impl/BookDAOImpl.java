package com.ebook.dao.impl;

import com.ebook.dao.AbstractJdbcDao;
import com.ebook.dao.BookDAO;
import com.ebook.exceptions.InvalidAuthorException;
import com.ebook.exceptions.InvalidPublicationYearException;
import com.ebook.exceptions.InvalidTitleException;
import com.ebook.model.Book;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl extends AbstractJdbcDao implements BookDAO {

    private Connection connection;

    public BookDAOImpl(Connection connection) {
        this.connection = connection;
    }
    static int currentYear = Year.now().getValue();
    static int minYear = 1450;
    static int maxYear = currentYear + 5;

    @Override
    public void addBook(Book book) throws SQLException {
        if (book.getYearOfPublication() < minYear || book.getYearOfPublication() > maxYear) {
            throw new InvalidPublicationYearException(minYear, maxYear);
        }

        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidTitleException();
        }

        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new InvalidAuthorException();
        }

        String query = "INSERT INTO books (title, author, yearOfPublication) VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getYearOfPublication());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        if (book.getYearOfPublication() < minYear || book.getYearOfPublication() > maxYear) {
            throw new InvalidPublicationYearException(minYear, maxYear);
        }

        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidTitleException();
        }

        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new InvalidAuthorException();
        }

        String query = "UPDATE books SET title=?, author=?, yearOfPublication=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getYearOfPublication());
            preparedStatement.setInt(4, book.getId());
            preparedStatement.executeUpdate();

            connection.commit(); // Zatwierdź transakcję po wykonaniu operacji
        } catch (SQLException e) {
            connection.rollback(); // Przywróć stan poprzedni w przypadku błędu
            throw e; // Rzuć wyjątek dalej
        } finally {
            connection.setAutoCommit(true); // Włącz ponownie automatyczne zatwierdzanie
        }

        }


    @Override
    public void deleteBook(int bookId) throws SQLException {
        String query = "DELETE FROM books WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Book getBookById(int bookId) throws SQLException {
        Book book = null;
        String query = "SELECT * FROM books WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    book = new Book(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getInt("yearOfPublication")

                    );
                }
            }
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("yearOfPublication")
                );
                books.add(book);

            }
        }
        return books;
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
}
