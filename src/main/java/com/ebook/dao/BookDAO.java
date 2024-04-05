package com.ebook.dao;

import com.ebook.model.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO extends GenericDao {
    void addBook(Book book) throws SQLException;
    void updateBook(Book book) throws SQLException;
    void deleteBook(int bookId) throws SQLException;
    Book getBookById(int bookId) throws SQLException;
    List<Book> getAllBooks() throws SQLException;
}

