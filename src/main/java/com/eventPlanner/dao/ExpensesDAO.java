package com.eventPlanner.dao;
import com.eventPlanner.database.DBConnection;
import com.eventPlanner.model.expenses;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ExpensesDAO {
    public void addExpenses(expenses expense)
            throws SQLException {
        String query = """
                INSERT INTO EXPENSES (event_ID, category, amount, description, expense_date)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stm = con.prepareStatement(query)) {
            stm.setInt(1, expense.getEvent_ID());
            stm.setString(2, expense.getCategory());
            stm.setBigDecimal(3, expense.getAmount());
            stm.setString(4, expense.getDescription());
            stm.setDate(5, java.sql.Date.valueOf(expense.getExpense_date()));

            stm.executeUpdate();
        }
    }

    public void getAllExpenses()
            throws SQLException {
        String query = """
                SELECT ex.id, e.event_ID, e.name AS event_name, ex.category, ex.amount, ex.description, ex.expense_date          
                FROM EXPENSES ex                        
                INNER JOIN EVENTS e                     
                ON ex.event_ID = e.event_id                         
                ORDER BY ex.expense_date;                                                                                                                                          
                """;
        try (Connection con = DBConnection.getConnection();
        PreparedStatement stm = con.prepareStatement(query);
        ResultSet rs = stm.executeQuery()) {
            while(rs.next()) {
                System.out.println("Event ID: " + rs.getInt("event_id"));
                System.out.println("Event Name: " + rs.getString("event_name"));
                System.out.println("Expense ID: " + rs.getInt("id"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Amount: " + rs.getBigDecimal("amount"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Expense_date: " + rs.getDate("expense_date"));
                System.out.println("---------------------------");
            }
        }
    }

    public void update(expenses expense)
            throws SQLException {
        String query = """
                UPDATE EXPENSES 
                SET event_id = ?, category = ?, amount = ?, description = ?, expense_date = ?
                WHERE id = ?
                """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stm = con.prepareStatement(query)) {
            stm.setInt(1, expense.getEvent_ID());
            stm.setString(2, expense.getCategory());
            stm.setBigDecimal(3, expense.getAmount());
            stm.setString(4, expense.getDescription());
            stm.setDate(5, java.sql.Date.valueOf(expense.getExpense_date()));
            stm.setInt(6, expense.getID());

            stm.executeUpdate();
        }
    }

    public void deleteExpense(expenses expense)
            throws SQLException {
        String query = """
                DELETE FROM EXPENSES
                WHERE id = ?
                """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stm = con.prepareStatement(query)) {
            stm.setInt(1, expense.getID());
            stm.executeUpdate();
        }
    }

    public BigDecimal getTotalSpent(int eventId)
            throws SQLException {
        String query = """
            SELECT COALESCE(SUM(amount), 0) AS total_spent
            FROM EXPENSES
            WHERE event_id = ?
            """;
        try (Connection con = DBConnection.getConnection();
        PreparedStatement stm = con.prepareStatement(query)) {
            stm.setInt(1, eventId);

            try (ResultSet rs = stm.executeQuery()) {
                if(rs.next()) {
                    return rs.getBigDecimal("total_spent");
                }
            }
        }
        return BigDecimal.ZERO;
    }

    public void getExpenseByID(int event_id)
        throws SQLException {
        String query = """
                 SELECT ex. id, ex.event_id, e.name, ex.category, ex.amount, ex.description, ex.expense_date 
                 FROM EXPENSES ex
                 LEFT JOIN EVENTS e
                 ON ex.event_id = e.event_id
                 WHERE ex.event_id = ?
             """;
        try (Connection con = DBConnection.getConnection();
        PreparedStatement stm = con.prepareStatement(query)) {
            stm.setInt(1, event_id);
            try (ResultSet rs = stm.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    if (!found) {
                        System.out.println(" ");
                        System.out.println("Event ID: " + rs.getInt("event_id"));
                        System.out.println("Event Name: " + rs.getString("name"));
                        System.out.println(" ");
                        found = true;
                    }
                    System.out.println("Expense ID: " + rs.getInt("id"));
                    System.out.println("Category: " + rs.getString("category"));
                    System.out.println("Amount: " + rs.getBigDecimal("amount"));
                    System.out.println("Description: " + rs.getString("description"));
                    System.out.println("Expense Date: " + rs.getDate("expense_date"));
                    System.out.println("----------------------------");
                }
                if (!found) {
                    System.out.println("No expenses found for this event.");
                }
            }
        }
    }

    public void overview(expenses expense)
        throws SQLException {
        String averageQuery = """
            SELECT AVG(amount) AS avg_expense
            FROM EXPENSES
            WHERE event_ID = ?
            """;

        String categoryQuery = """
            SELECT category, SUM(amount) AS total_amount
            FROM EXPENSES
            WHERE event_ID = ?
            GROUP BY category
            ORDER BY total_amount
            """;
        try (Connection con = DBConnection.getConnection()) {
            BigDecimal average;
            try (PreparedStatement stm = con.prepareStatement(averageQuery)) {
                stm.setInt(1, expense.getEvent_ID());

                try (ResultSet rs = stm.executeQuery()) {
                    rs.next();
                    average = rs.getBigDecimal("avg_expense");
                }
            }
            String minCategory = null;
            String maxCategory = null;
            BigDecimal minAmount = null;
            BigDecimal maxAmount = null;
            try (PreparedStatement stm = con.prepareStatement(categoryQuery)) {
                stm.setInt(1, expense.getEvent_ID());

                try (ResultSet rs = stm.executeQuery()) {
                    while (rs.next()) {
                        String category = rs.getString("category");
                        BigDecimal amount = rs.getBigDecimal("total_amount");
                        if (minAmount == null || amount.compareTo(minAmount) < 0) {
                            minAmount = amount;
                            minCategory = category;
                        }
                        if (maxAmount == null || amount.compareTo(maxAmount) > 0) {
                            maxAmount = amount;
                            maxCategory = category;
                        }
                    }
                }
            }
            System.out.println("Average expense: " + (average == null ? "N/A" : average));
            if (minAmount == null) {
                System.out.println("Minimum expense: N/A");
                System.out.println("Maximum expense: N/A");
            } else {
                System.out.println("Minimum expense: " + minCategory + " - " + minAmount);
                System.out.println("Maximum expense: " + maxCategory + " - " + maxAmount);
            }
        }
    }

}
