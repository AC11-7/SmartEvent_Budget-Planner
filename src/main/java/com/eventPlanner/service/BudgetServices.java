package com.eventPlanner.service;
import com.eventPlanner.dao.ExpensesDAO;
import com.eventPlanner.model.events;
import com.eventPlanner.model.summary;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

public class BudgetServices {
    private final ExpensesDAO expenseDAO = new ExpensesDAO();

    // Calculate the budget summary for an event
    public summary getSummary(events event) throws SQLException {
        BigDecimal budget = event.getTotal_budget();
        BigDecimal spent = expenseDAO.getTotalSpent(
                event.getID()
        );
        return new summary(budget, spent);
    }

    // Check whether the budget has been exceeded
    public String getBudgetWarning(summary summary) {
        BigDecimal remaining = summary.getRemaining();
        BigDecimal spent = summary.getSpent();
        BigDecimal budget = summary.getBudget();
        BigDecimal percentage = budget.compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO : spent.divide(budget, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        if (remaining.compareTo(BigDecimal.ZERO) < 0) {
            return "WARNING: BUDGET EXCEEDED!";
        } else if (remaining.compareTo(BigDecimal.ZERO) == 0) {
            return "WARNING: BUDGET EXHAUSTED.";
        } else if (percentage.compareTo(BigDecimal.valueOf(80)) >= 0) {
            return "WARNING: 80% OR MORE USED";
        } else {
            return "Within Budget. " + "Percentage used: " + percentage;
        }
    }
}
