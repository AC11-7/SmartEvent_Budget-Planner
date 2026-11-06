package com.eventPlanner.model;
import java.math.BigDecimal;

public class summary {
    private BigDecimal budget;
    private BigDecimal spent;
    private BigDecimal remaining;

    public summary(BigDecimal budget, BigDecimal spent) {
        this.budget = budget;
        this.spent = spent;
        this.remaining = budget.subtract(spent);
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public BigDecimal getSpent() {
        return spent;
    }

    public BigDecimal getRemaining() {
        return remaining;
    }
}
