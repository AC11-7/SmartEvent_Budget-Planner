package com.eventPlanner;
import com.eventPlanner.dao.EventsDAO;
import com.eventPlanner.dao.ExpensesDAO;
import com.eventPlanner.model.events;
import com.eventPlanner.model.expenses;
import com.eventPlanner.model.summary;
import com.eventPlanner.service.BudgetServices;

import com.eventPlanner.database.*;
import java.sql.SQLException;
import java.sql.Statement;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EventsDAO eventsDAO = new EventsDAO();
        ExpensesDAO expensesDAO = new ExpensesDAO();
        BudgetServices budgetServices = new BudgetServices();
        int choice;
        do {
            System.out.println("\n================================");
            System.out.println("   SMART EVENT BUDGET PLANNER");
            System.out.println("================================");
            System.out.println("1. Add Event");
            System.out.println("2. View Particular Event");
            System.out.println("3. View All Events");
            System.out.println("4. Update Event");
            System.out.println("5. Delete Event");
            System.out.println("6. Add Expense");
            System.out.println("7. View Expenses of Particular Event");
            System.out.println("8. View All Expenses");
            System.out.println("9. Update Expense");
            System.out.println("10. Delete Expense");
            System.out.println("11. View Budget Summary");
            System.out.println("12. Expense Overview");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Enter a valid menu number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Event name: ");
                        String name = sc.nextLine();

                        System.out.print("Event date (YYYY-MM-DD): ");
                        LocalDate date = LocalDate.parse(sc.nextLine());

                        System.out.print("Location: ");
                        String location = sc.nextLine();

                        System.out.print("Total budget: ");
                        BigDecimal budget = new BigDecimal(sc.nextLine());

                        events event = new events(0, name, date, location, budget);
                        eventsDAO.addEvent(event);
                        System.out.println("Event added successfully.");
                    }

                    case 2 -> {
                        System.out.print("Enter Event ID: ");
                        int eventId = Integer.parseInt(sc.nextLine());

                        events event = eventsDAO.getEventById(eventId);
                        if (event == null) {
                            System.out.println("Event not found.");
                        } else {
                            System.out.println("\n--- EVENT DETAILS ---");
                            System.out.println("Event ID: " + event.getID());
                            System.out.println("Name: " + event.getName());
                            System.out.println("Date: " + event.getEvent_date());
                            System.out.println("Location: " + event.getLocation());
                            System.out.println("Total Budget: " + event.getTotal_budget());
                        }
                    }

                    case 3 -> {
                        eventsDAO.getAllEvents();
                    }

                    case 4 -> {
                        System.out.print("Event ID to update: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("New event name: ");
                        String name = sc.nextLine();

                        System.out.print("New event date (YYYY-MM-DD): ");
                        LocalDate date = LocalDate.parse(sc.nextLine());

                        System.out.print("New location: ");
                        String location = sc.nextLine();

                        System.out.print("New total budget: ");
                        BigDecimal budget = new BigDecimal(sc.nextLine());

                        events event = new events(id, name, date, location, budget);
                        eventsDAO.update(event);
                        System.out.println("Event updated.");
                    }

                    case 5 -> {
                        System.out.print("Event ID to delete: ");
                        int id = Integer.parseInt(sc.nextLine());

                        events event = new events(id, "", null, "", BigDecimal.ZERO);
                        eventsDAO.deleteEvent(event);
                        System.out.println("Delete request completed.");
                    }

                    case 6 -> {
                        System.out.print("Event ID: ");
                        int eventId = Integer.parseInt(sc.nextLine());

                        System.out.print("Category: ");
                        String category = sc.nextLine();

                        System.out.print("Amount: ");
                        BigDecimal amount = new BigDecimal(sc.nextLine());

                        System.out.print("Description: ");
                        String description = sc.nextLine();

                        System.out.print("Expense date (YYYY-MM-DD): ");
                        LocalDate date = LocalDate.parse(sc.nextLine());

                        expenses expense = new expenses(0, eventId, category, amount, description, date);
                        expensesDAO.addExpenses(expense);
                        System.out.println("Expense added successfully.");
                    }

                    case 7 -> {
                        System.out.print("Enter Event ID: ");
                        int eventId = Integer.parseInt(sc.nextLine());

                        expensesDAO.getExpenseByID(eventId);
                    }

                    case 8 -> {
                        expensesDAO.getAllExpenses();
                    }

                    case 9 -> {
                        System.out.print("Expense ID to update: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Event ID: ");
                        int eventId = Integer.parseInt(sc.nextLine());

                        System.out.print("Category: ");
                        String category = sc.nextLine();

                        System.out.print("Amount: ");
                        BigDecimal amount = new BigDecimal(sc.nextLine());

                        System.out.print("Description: ");
                        String description = sc.nextLine();

                        System.out.print("Expense date (YYYY-MM-DD): ");
                        LocalDate date = LocalDate.parse(sc.nextLine());

                        expenses expense = new expenses(id, eventId, category, amount, description, date);
                        expensesDAO.update(expense);
                        System.out.println("Expense updated.");
                    }

                    case 10 -> {
                        System.out.print("Expense ID to delete: ");
                        int id = Integer.parseInt(sc.nextLine());

                        expenses expense = new expenses(id, 0, "", BigDecimal.ZERO, "", null);
                        expensesDAO.deleteExpense(expense);
                        System.out.println("Delete request completed.");
                    }

                    case 11 -> {
                        System.out.print("Event ID: ");
                        int eventId = Integer.parseInt(sc.nextLine());

                        events event = eventsDAO.getEventById(eventId);
                        if (event == null) {
                            System.out.println("Event not found.");
                        } else {
                            summary result = budgetServices.getSummary(event);

                            System.out.println("\n--- BUDGET SUMMARY ---");
                            System.out.println("Event: " + event.getName());
                            System.out.println("Budget: " + result.getBudget());
                            System.out.println("Spent: " + result.getSpent());
                            System.out.println("Remaining: " + result.getRemaining());
                            System.out.println(budgetServices.getBudgetWarning(result));
                        }
                    }

                    case 12 -> {
                        System.out.print("Event ID: ");
                        int eventId = Integer.parseInt(sc.nextLine());

                        expenses eventExpenses = new expenses(0, eventId, "", BigDecimal.ZERO, "", null);
                        expensesDAO.overview(eventExpenses);
                    }

                    case 0 ->
                            System.out.println("Exiting Smart Event Budget Planner.");

                    default ->
                            System.out.println("Invalid choice. Try again.");
                }

            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Invalid date. Use YYYY-MM-DD.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 0);
        sc.close();
    }
}

