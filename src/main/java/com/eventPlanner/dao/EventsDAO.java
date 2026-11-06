package com.eventPlanner.dao;
import com.eventPlanner.database.DBConnection;
import com.eventPlanner.model.events;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventsDAO {
    public void addEvent(events event)
        throws SQLException {
        String query = """
                INSERT INTO EVENTS (name, event_date, location, total_budget)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection con = DBConnection.getConnection();
        PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, event.getName());
            stm.setDate(2, java.sql.Date.valueOf(event.getEvent_date()));
            stm.setString(3, event.getLocation());
            stm.setBigDecimal(4, event.getTotal_budget());

            stm.executeUpdate();
        }
    }

    public void getAllEvents()
        throws SQLException {
        String query = """
                SELECT * FROM EVENTS
                """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stm = con.prepareStatement(query);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                System.out.println("Event ID: " + rs.getInt("event_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Date: " + rs.getDate("event_date"));
                System.out.println("Location: " + rs.getString("location"));
                System.out.println("Budget: " + rs.getBigDecimal("total_budget"));
                System.out.println("---------------------------");
            }
        }
    }

    public events getEventById(int event_id) throws SQLException {
        String query = """    
            SELECT * FROM EVENTS
            WHERE event_id = ?
            """;
        try (Connection con = DBConnection.getConnection();
        PreparedStatement stm = con.prepareStatement(query)){
            stm.setInt(1, event_id);

            try (ResultSet rs = stm.executeQuery()) {
                if(rs.next()) {
                    return new events (
                        rs.getInt("event_id"),
                        rs.getString("name"),
                        rs.getDate("event_date") == null ? null : rs.getDate("event_date").toLocalDate(),
                        rs.getString("location"),
                        rs.getBigDecimal("total_budget")
                    );
                }
            }
        }
        return null;
    }


    public void update(events event)
        throws SQLException {
        String query = """
                UPDATE EVENTS
                SET name = ?, event_date = ?, location = ?, total_budget = ?
                WHERE event_id= ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stm = con.prepareStatement(query)) {

            stm.setString(1, event.getName());
            stm.setDate(2, java.sql.Date.valueOf(event.getEvent_date()));
            stm.setString(3, event.getLocation());
            stm.setBigDecimal(4, event.getTotal_budget());
            stm.setInt(5, event.getID());

            stm.executeUpdate();
        }
    }

    public void deleteEvent(events event)
        throws SQLException {
        String query = """
                DELETE FROM EVENTS
                WHERE event_ID = ?
                """;
        try (Connection con = DBConnection.getConnection();
        PreparedStatement stm = con.prepareStatement(query)) {
            stm.setInt(1, event.getID());
            stm.executeUpdate();
        }
    }
}
