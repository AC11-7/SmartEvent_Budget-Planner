package com.eventPlanner.model;
import java.math.BigDecimal;
import java.time.LocalDate;
public class expenses {
    private int ID;
    private int event_ID;
    private String category;
    private BigDecimal amount;
    private String description;
    private LocalDate expense_date;

    public expenses(int ID, int event_ID, String category, BigDecimal amount, String description, LocalDate expense_date) {
        this.ID = ID;
        this.event_ID = event_ID;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.expense_date = expense_date;
    }

    public int getID() {
        return ID;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getExpense_date() {
        return expense_date;
    }

    public int getEvent_ID() {
        return event_ID;
    }

    public void setAmount(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be positive."
            );
        }
        this.amount = amount;
    }
}
