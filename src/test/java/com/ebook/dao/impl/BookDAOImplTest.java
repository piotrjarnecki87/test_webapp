package com.ebook.dao.impl;

import com.ebook.exceptions.InvalidAuthorException;
import com.ebook.exceptions.InvalidPublicationYearException;
import com.ebook.exceptions.InvalidTitleException;
import com.ebook.h2database.H2DatabaseConfig;
import com.ebook.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

class BookDAOImplTest {
    private BookDAOImpl bookDAO;
    private DataSource dataSource;
    @BeforeEach
    public void setUp() throws SQLException {
        H2DatabaseConfig h2DatabaseConfig = new H2DatabaseConfig();
        dataSource = h2DatabaseConfig.getDataSource();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS books (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), author VARCHAR(255), yearOfPublication INT);");
        }

        bookDAO = new BookDAOImpl(dataSource.getConnection());
    }
    @AfterEach
    private void resetData() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS books");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addInitialBooks() throws SQLException {

        Book book1 = new Book(1, "Old Title 1", "Old Author 1", 2000);
        Book book2 = new Book(2, "Old Title 2", "Old Author 2", 2001);
        Book book3 = new Book(3, "Old Title 3", "Old Author 3", 2002);

        bookDAO.addBook(book1);
        bookDAO.addBook(book2);
        bookDAO.addBook(book3);
    }



    // Tests addBook
    @ParameterizedTest
    @CsvSource({
            "1, Old Book, Historical Author, 1451",
            "2, Future Book, Future Author, 2025"
    })
    public void testAddBookWithCorrectData(int id, String title, String author, int yearOfPublication) throws SQLException {
        Book book = new Book(id, title, author, yearOfPublication);
        bookDAO.addBook(book);

        Book retrievedBook = bookDAO.getBookById(id);
        assertNotNull(retrievedBook);
        assertEquals(yearOfPublication, retrievedBook.getYearOfPublication());
    }




    @ParameterizedTest
    @CsvSource({
            "1, Old Book, Historical Author, 1449",
            "2, Future Book, Future Author, 2030",
            "3 , Old Book, Historical Author, "
    })
    public void testAddBookWithInvalidYear(int id, String title, String author, Integer yearOfPublication) {
        Book book = new Book(id, title, author, yearOfPublication);


        InvalidPublicationYearException expectedInvalidYearException = new InvalidPublicationYearException(BookDAOImpl.minYear,BookDAOImpl.maxYear);
        Exception actualException = assertThrows(Exception.class, () -> {
            bookDAO.addBook(book);
        });

        if (yearOfPublication == null) {
            Assertions.assertTrue(actualException instanceof NullPointerException);

        } else if (yearOfPublication < BookDAOImpl.minYear || yearOfPublication > BookDAOImpl.maxYear) {
            Assertions.assertEquals(expectedInvalidYearException.getClass(), actualException.getClass());
            Assertions.assertEquals(expectedInvalidYearException.getMessage(), actualException.getMessage());
        } else {
            fail("Unexpected exception thrown: " + actualException.getClass());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1, Future Title,'' , 1450",
            "2, '', Future Author, 2029 ",
            "3, Future Title, , 2029 "
    })
    public void testAddBookWithInvalidTitleAndAuthorFields(int id, String title, String author, int yearOfPublication) {
        Book book = new Book(id, title, author, yearOfPublication);

        InvalidTitleException expectedTitleException  = new InvalidTitleException();
        InvalidAuthorException expectedAuthorException = new InvalidAuthorException();

        Exception actualException = assertThrows(Exception.class, () -> {
            bookDAO.addBook(book);
        });

        if (actualException instanceof InvalidTitleException) {
            assertEquals(expectedTitleException.getClass(), actualException.getClass());
            assertEquals(expectedTitleException.getMessage(), actualException.getMessage());
        } else if (actualException instanceof InvalidAuthorException) {
            assertEquals(expectedAuthorException.getClass(), actualException.getClass());
            assertEquals(expectedAuthorException.getMessage(), actualException.getMessage());
        } else {
            fail("Unexpected exception thrown");
        }
    }



//tests updateBook
@ParameterizedTest
@CsvSource({
        "1, Clean Code, Robert C. Martin, 2008"
})
void testUpdateBook(int id, String title, String author, int yearOfPublication) throws SQLException {
    addInitialBooks();
    Book book = new Book(id, title, author, yearOfPublication);
    bookDAO.updateBook(book);

    Book updatedBook = bookDAO.getBookById(id);
    assertNotNull(updatedBook);
    assertEquals(title, updatedBook.getTitle());
    assertEquals(author, updatedBook.getAuthor());
    assertEquals(yearOfPublication, updatedBook.getYearOfPublication());
}


    @ParameterizedTest
    @CsvSource({
            "1, Future Title,'' , 1450",
            "2, '', Future Author, 2029 ",
            "3, Future Title, , 2029 "
    })
    public void testUpdateBookInvalidTitleAndAuthor(int id, String title, String author, int yearOfPublication) throws SQLException {
        addInitialBooks();
        Book book = new Book(id, title, author, yearOfPublication);

        InvalidTitleException expectedTitleException  = new InvalidTitleException();
        InvalidAuthorException expectedAuthorException = new InvalidAuthorException();

        Exception actualException = assertThrows(Exception.class, () -> {
            bookDAO.updateBook(book);
        });

        if (actualException instanceof InvalidTitleException) {
            assertEquals(expectedTitleException.getClass(), actualException.getClass());
            assertEquals(expectedTitleException.getMessage(), actualException.getMessage());
        } else if (actualException instanceof InvalidAuthorException) {
            assertEquals(expectedAuthorException.getClass(), actualException.getClass());
            assertEquals(expectedAuthorException.getMessage(), actualException.getMessage());
        } else {
            fail("Unexpected exception thrown");
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1, Old Book, Historical Author, 1449",
            "2, Future Book, Future Author, 2030",
            "3 , Old Book, Historical Author, "
    })
    public void testUpdateWithInvalidYear(int id, String title, String author, Integer yearOfPublication) throws SQLException {
        addInitialBooks();
        Book book = new Book(id, title, author, yearOfPublication);


        InvalidPublicationYearException expectedInvalidYearException = new InvalidPublicationYearException(BookDAOImpl.minYear,BookDAOImpl.maxYear);
        Exception actualException = assertThrows(Exception.class, () -> {
            bookDAO.updateBook(book);
        });

        if (yearOfPublication == null) {
            Assertions.assertTrue(actualException instanceof NullPointerException);

        } else if (yearOfPublication < BookDAOImpl.minYear || yearOfPublication > BookDAOImpl.maxYear) {
            Assertions.assertEquals(expectedInvalidYearException.getClass(), actualException.getClass());
            Assertions.assertEquals(expectedInvalidYearException.getMessage(), actualException.getMessage());
        } else {
            fail("Unexpected exception thrown: " + actualException.getClass());
        }
    }


    @ParameterizedTest
    @CsvSource({
            "1, Old Title 1, Old Author 1, 2000",
            "2, Old Title 2, Old Author 2, 2001",
            "3, Old Title 3, Old Author 3, 2002"
    })
    public void testGetBookById(int id, String expectedTitle, String expectedAuthor, int expectedYear) throws SQLException {
        addInitialBooks();
        Book retrievedBook = bookDAO.getBookById(id);

        assertEquals(expectedTitle, retrievedBook.getTitle());
        assertEquals(expectedAuthor, retrievedBook.getAuthor());
        assertEquals(expectedYear, retrievedBook.getYearOfPublication());
    }


//delete book
    @Test
    public void testDeleteBook() throws SQLException {
        addInitialBooks();

        int bookIdToDelete = 2;


        Book bookBeforeDeletion = bookDAO.getBookById(bookIdToDelete);
        assertNotNull(bookBeforeDeletion);
        bookDAO.deleteBook(bookIdToDelete);

        Book bookAfterDeletion = bookDAO.getBookById(bookIdToDelete);
        assertNull(bookAfterDeletion);
    }

    @Test
    public void testGetBookById() throws SQLException {
        addInitialBooks();

        int bookIdToRetrieve = 2;
        Book retrievedBook = bookDAO.getBookById(bookIdToRetrieve);

        assertEquals(2, retrievedBook.getId());
        assertEquals("Old Title 2", retrievedBook.getTitle());
        assertEquals("Old Author 2", retrievedBook.getAuthor());
        assertEquals(2001, retrievedBook.getYearOfPublication());
    }

    @Test
    public void testGetBookByInvalidId() throws SQLException {
        addInitialBooks();

        int nonExistentBookId = -999;
        Book nonExistentBook = bookDAO.getBookById(nonExistentBookId);

        assertNull(nonExistentBook);
    }

    @Test
    public void testGetAllBooks() throws SQLException {
        addInitialBooks();
        List<Book> allBooks = bookDAO.getAllBooks();

        // Validate the results
        assertNotNull(allBooks);
        assertEquals(3, allBooks.size());

        // Verify the details of each book
        Book book = allBooks.get(0);
        assertEquals(1, book.getId());
        assertEquals("Old Title 1", book.getTitle());
        assertEquals("Old Author 1", book.getAuthor());
        assertEquals(2000, book.getYearOfPublication());

        book = allBooks.get(1);
        assertEquals(2, book.getId());
        assertEquals("Old Title 2", book.getTitle());
        assertEquals("Old Author 2", book.getAuthor());
        assertEquals(2001, book.getYearOfPublication());

        book = allBooks.get(2);
        assertEquals(3, book.getId());
        assertEquals("Old Title 3", book.getTitle());
        assertEquals("Old Author 3", book.getAuthor());
        assertEquals(2002, book.getYearOfPublication());
    }












































        @Test
    void getBookById() {
    }

    @Test
    void getAllBooks() {
    }
}