package com.ebook.dao.impl;

import com.ebook.database.ConnectionPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class BookDAOImplTest {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static BookDAOImpl bookDAO;

    @BeforeClass
    public static void setUp() throws SQLException, InterruptedException {
        Connection connection = connectionPool.getConnection();
        bookDAO = new BookDAOImpl(connection);
        connectionPool.releaseConnection(connection);
    }

    @AfterClass
    public static void tearDown() throws SQLException, InterruptedException {
        connectionPool.closeConnections();
    }

    @Test
    public void testGetAll() throws SQLException {
        // Wywołanie metody getAll
        int numberOfBooks = bookDAO.getAllBooks().size();

        // Sprawdzenie, czy zwrócono poprawną liczbę książek
        assertEquals(10, numberOfBooks);
    }
}
