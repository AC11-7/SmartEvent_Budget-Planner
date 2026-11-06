# Smart Event Budget Planner

A console-based Event Budget Management System developed using **Java, MySQL, JDBC, and Object-Oriented Programming (OOP)**.

The application allows users to manage events, track expenses, monitor spending, and generate budget summaries.

## Features

- Add, view, update, and delete events
- Add, view, update, and delete expenses
- View expenses associated with a particular event
- Calculate total expenses and remaining budget
- Generate budget summaries and spending warnings
- Identify minimum and maximum expense categories
- Calculate average expenses
- Retrieve event and expense information using SQL JOINs

## Technologies Used

| Technology | Purpose |
|---|---|
| Java | Application development |
| MySQL | Relational database |
| JDBC | Database connectivity |
| Maven | Dependency management |
| IntelliJ IDEA | Development environment |

## Project Architecture

The project follows a layered architecture:

`Main → Service Layer → DAO Layer → JDBC → MySQL`

- **Model Layer:** Defines event, expense, and summary objects.
- **DAO Layer:** Performs database operations using SQL queries.
- **Service Layer:** Implements budget calculations and warnings.
- **Main Class:** Provides a menu-driven console interface.

## Database Design

The database contains two related tables:

**EVENTS**
- `event_id` (Primary Key)
- `name`
- `event_date`
- `location`
- `total_budget`

**EXPENSES**
- `id` (Primary Key)
- `event_ID` (Foreign Key)
- `category`
- `amount`
- `description`
- `expense_date`

The tables have a **one-to-many relationship**: one event can have multiple expenses, while each expense belongs to one event.

## How to Run

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Ensure Java, MySQL, and Maven are configured.
4. Execute `sql/schema.sql` to create the database tables.
5. Optionally execute `sql/sample_data.sql` to insert sample records.
6. Configure the database connection using the `DB_PASSWORD` environment variable.
7. Run `Main.java`.
8. Use the console menu to perform operations.

## Budget Management

The system calculates:

- **Total Spent:** Sum of all expenses associated with an event.
- **Remaining Budget:** Total Budget − Total Spent.
- **Budget Warning:** Alerts users when spending reaches or exceeds 80% of the budget.
- **Expense Analysis:** Displays average expenses and minimum/maximum spending categories.

## Future Enhancements

- Graphical user interface
- Data visualization and expense charts
- Exportable budget reports
- User authentication

## Author

Developed as an academic project to demonstrate Java OOP concepts, relational database management, JDBC connectivity, and SQL operations.
