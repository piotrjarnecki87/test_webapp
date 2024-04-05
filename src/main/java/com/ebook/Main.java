package com.ebook;

import com.ebook.dao.impl.BookDAOImpl;
import com.ebook.dao.impl.BookOrderDAOImpl;
import com.ebook.database.ConnectionPool;
import com.ebook.model.Book;
import com.ebook.model.BookRental;

import java.sql.SQLException;
import java.util.List;

public class Main {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static BookOrderDAOImpl bookOrderDAO; // Inicjalizacja obiektu bookDAO

    static {
        bookOrderDAO = new BookOrderDAOImpl(connectionPool.getConnection());
    }


    public static void main(String[] args) {
        try {
            List<BookRental> orderedBooks = bookOrderDAO.getOrderedBooksByUserId(1);
//            List<Book> books = bookDAO.getAllBooks(); // Pobranie listy książek
            System.out.println("Number of books: " + orderedBooks.size()); // Wyświetlenie liczby książek

            // Wyświetlenie szczegółów każdej książki
            for (BookRental bookRental : orderedBooks) {
                System.out.println("Title: " + bookRental.getRentalDate() + ", Author: " + bookRental.getReturnDate());
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Obsługa błędu SQLException
        }
    }
}
