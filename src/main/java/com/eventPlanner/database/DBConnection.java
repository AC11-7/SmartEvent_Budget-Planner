package com.eventPlanner.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/event_budget_planner";
    private static final String UserName = "root";
    private static final String Password = System.getenv("DB_PASSWORD");

    public static Connection getConnection()
            throws SQLException {
        return DriverManager.getConnection(URL, UserName, Password);
    }
}

