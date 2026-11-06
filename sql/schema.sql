-- 1. CREATE AND SELECT DATABASE
CREATE DATABASE IF NOT EXISTS event_budget_planner;
USE event_budget_planner;

-- 2. CREATE EVENTS TABLE

CREATE TABLE IF NOT EXISTS events (
    event_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    event_date DATE NOT NULL,
    location VARCHAR(150) NOT NULL,
    total_budget DECIMAL(12,2) NOT NULL,

    CONSTRAINT chk_event_budget
    CHECK (total_budget > 0)
    );

-- 3. CREATE EXPENSES TABLE

CREATE TABLE IF NOT EXISTS expenses (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    event_ID BIGINT NOT NULL,
    category VARCHAR(50) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    description VARCHAR(255),
    expense_date DATE NOT NULL,

    CONSTRAINT chk_expense_amount
    CHECK (amount > 0),

    CONSTRAINT fk_expense_event
    FOREIGN KEY (event_ID)
    REFERENCES events(event_ID)
    ON DELETE CASCADE
    );