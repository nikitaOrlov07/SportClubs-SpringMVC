package com.example.web;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseTest {
    @Test
    void checkDatabases_exists()
    {
        Connection connection;
        try{
            String url      = "jdbc:postgresql://localhost:5432/postgres";
            String username = "postgres";
            String password = "07022005";

            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(connection, "Failed to establish connection to the database");
    }

}

