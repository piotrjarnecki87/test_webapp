package com.ebook.dao;

import com.ebook.model.BookRental;

import java.util.List;

public interface CopyDAO {
    void addCopy(BookRental copy);
    void updateCopy(BookRental copy);
    void deleteCopy(int copyId);
    BookRental getCopyById(int copyId);
    List<BookRental> getAllCopiesOfBook(int bookId);
}
