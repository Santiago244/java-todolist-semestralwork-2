# To-Do List Application Documentation

## 1. Project Overview

The To-Do List application is a desktop task manager developed in Java using Swing. It allows users to create, edit, delete, filter, and export tasks. The application stores task data in a local SQLite database and also supports exporting the current table view to a CSV file.

The user interface is organized into multiple panels so that task input, task browsing, and summary information are separated into clear sections. A dark theme is applied through FlatLaf.

## 2. Main Purpose

The purpose of this project is to provide a simple but functional task management tool with persistent storage. It is designed for users who want to:

- Track personal or academic tasks.
- Assign priorities and statuses to tasks.
- Set due dates for future follow-up.
- Keep data saved locally between application sessions.

## 3. Main Features

The application currently includes the following features:

- Create new tasks with title, description, priority, status, and due date.
- Edit selected tasks and save the changes.
- Delete selected tasks from the interface and database.
- Filter tasks by status.
- Filter tasks by priority.
- Export the current table data to a CSV file.
- Display a summary of tasks by status in the bottom panel.
- Load saved tasks from the SQLite database when the application starts.

## 4. Technologies Used

- Java 16
- Swing for the graphical user interface
- Maven for project management and dependencies
- SQLite for persistent storage
- FlatLaf for the dark theme
- OpenCSV for CSV export support
- LGoodDatePicker as a date-related dependency
- Gson as an included dependency in the build

## 5. Application Structure

The code is organized into the following main classes:

- `Gui.java` - main application window and event wiring.
- `Task.java` - task data model.
- `TaskController.java` - task creation, validation, filtering, and in-memory task list management.
- `DatabaseManager.java` - database connection and table creation.
- `TaskDBExecute.java` - insert, update, delete, and load operations for tasks in SQLite.
- `CSV.java` - CSV export helper.
- `TaskFormPanelWest.java` - task entry form on the left side of the UI.
- `ViewTasksCenterPanel.java` - central task table.
- `UpperOptionsPanel.java` - top action bar with buttons, filters, and search input.
- `OverviewBottomPanel.java` - status summary panel at the bottom.
- `Theme.java` - shared UI colors and style values.

## 6. User Interface Layout

The application window is divided into four main areas:

### Top Panel

Contains actions and filters:

- Create CSV
- Add Task
- Edit Task
- Delete Task
- Search field
- Save Changes button
- Status filter dropdown
- Priority filter dropdown

### Left Panel

Contains the task form where the user enters:

- Title
- Description
- Priority
- Status
- Due date

### Center Panel

Displays the task table with the following columns:

- ID
- Title
- Description
- Priority
- Status
- Due date

### Bottom Panel

Shows task counters for:

- Total
- Todo
- In progress
- Done
- Overdue

## 7. Task Data Model

Each task contains the following fields:

- ID
- Title
- Description
- Priority
- Status
- Due date

Task priorities supported by the application:

- LOW
- MEDIUM
- HIGH
- CRITICAL

Task statuses supported by the application:

- TODO
- INPROGRESS
- DONE
- OVERDUE

## 8. Data Storage

The application uses a local SQLite database file named `todolist.db`.

When the application starts, it connects to the database and creates the `tasks` table if it does not already exist. The table structure is:

- `id` INTEGER PRIMARY KEY
- `title` TEXT NOT NULL
- `description` TEXT
- `priority` TEXT
- `status` TEXT
- `due_date` TEXT

## 9. Runtime Behavior

At startup, the application does the following:

1. Applies the FlatDarkLaf theme.
2. Opens a connection to the SQLite database.
3. Creates the `tasks` table if needed.
4. Loads saved tasks from the database.
5. Displays the tasks in the central table.

When the user creates a task:

1. The form input is validated.
2. A new task object is created.
3. The task is added to the in-memory task list.
4. The task is inserted into the database.
5. The table view is updated.

When the user edits a task:

1. The selected task is loaded into the form.
2. The user changes the fields.
3. The updated values are saved.
4. The table and database are both updated.

When the user deletes a task:

1. The selected row is removed from the table.
2. The task is removed from the in-memory list.
3. The task is deleted from the database.

## 10. Validation Rules

The current validation logic checks the following:

- Title must be between 4 and 30 characters.
- Description must be between 10 and 200 characters.
- Due date must follow the expected date format.

The priority and status fields use dropdown selectors, so they always have valid values selected.

## 11. CSV Export

The application can export the current table content to `todolist.csv`.

The export includes the column headers and all visible rows from the task table.

## 12. Build Instructions

To compile the project with Maven, run the following command from the project root:

```bash
mvn clean compile
```

## 13. Running the Application

The current startup class is `todolist.Gui`.

You can run the application from an IDE by launching the `Gui` class. The `Main.java` file currently exists but does not contain the actual launcher logic.

## 14. Current Files Generated by the Application

- `todolist.db` - SQLite database file.
- `todolist.csv` - CSV export file.

## 15. Limitations and Notes

- The `Main.java` class is currently empty.
- The search field exists in the UI, but its behavior is limited in the current codebase.
- Some methods in the project are still simple or partially implemented, especially around search and summary refresh behavior.

## 16. Possible Future Improvements

- Implement full-text search across tasks.
- Improve task summary refresh logic.
- Add sorting options for the task table.
- Improve date validation and input handling.
- Add more polished status indicators in the interface.
- Extend the documentation with screenshots of the application.

## 17. Conclusion

This project demonstrates a desktop task manager built with Java Swing, SQLite, and Maven. It provides the core operations needed for managing tasks locally and can be expanded with additional filtering, search, and reporting features.
