package com.eventPlanner.model;
import java.math.BigDecimal;
import java.time.LocalDate;
public class events {
    private int event_ID;
    private String name;
    private LocalDate event_date;
    private String location;
    private BigDecimal total_budget;

    public events(int event_ID, String name, LocalDate event_date, String location, BigDecimal total_budget) {
        this.event_ID = event_ID;
        this.name = name;
        this.event_date = event_date;
        this.location = location;
        this.total_budget = total_budget;
    }

    public int getID() {
        return event_ID;
    }

    public String getName() {
        return name;
    }

    public LocalDate getEvent_date() {
        return event_date;
    }

    public String getLocation() {
        return location;
    }

    public BigDecimal getTotal_budget() {
        return total_budget;
    }
    public void setTotal_budget(BigDecimal totalBudget) {
        if(totalBudget.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Budget must be greater than zero."
            );
        }
        this.total_budget = totalBudget;
    }
}

