package com.ebook.dao;

import com.ebook.model.BookRental;

import java.sql.SQLException;
import java.util.List;

public interface BookOrderDAO extends GenericDao {
    List<BookRental> getOrderedBooksByUserId(int userId) throws SQLException;


}
