package com.ebook.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionPoolTest {

    public static void main(String[] args) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try {
            // Pobierz połączenie z puli
            Connection connection = connectionPool.getConnection();

            // Sprawdź, czy połączenie jest otwarte
            if (connection != null && !connection.isClosed()) {
                System.out.println("Połączenie z bazą danych zostało nawiązane.");
            } else {
                System.out.println("Nie udało się nawiązać połączenia z bazą danych.");
            }

            // Zwalnianie połączenia do puli
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Zamknięcie wszystkich połączeń przy zakończeniu testu
            connectionPool.closeConnections();
        }
    }
}