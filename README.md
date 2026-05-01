# TodoList Manager

<div align="center">

![Java](https://img.shields.io/badge/Java-25-ED8B00?style=flat-square&logo=java)
![Maven](https://img.shields.io/badge/Maven-4.0-C71A36?style=flat-square&logo=apache-maven)
![SQLite](https://img.shields.io/badge/SQLite-3-003B57?style=flat-square&logo=sqlite)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

A feature-rich desktop task management application built with Java Swing and SQLite. Organize your work with priorities, deadlines, and comprehensive filtering—all with a modern dark theme interface.

[Features](#features) • [Installation](#installation) • [Usage](#usage) • [Architecture](#architecture) • [Contributing](#contributing)

</div>

---

## 📋 Overview

TodoList Manager is a professional-grade desktop application designed for efficient personal task management. With an intuitive graphical interface, robust database backend, and powerful filtering capabilities, it provides a complete solution for organizing daily work and tracking project progress.

The application demonstrates core principles of object-oriented design, including MVC architecture, data persistence, user authentication, and comprehensive error handling.

## ✨ Features

### Core Task Management

- ✅ **Create Tasks** - Add tasks with title, description, priority, status, and due dates
- ✅ **Edit Tasks** - Modify existing tasks and automatically sync changes to database
- ✅ **Delete Tasks** - Remove tasks with cascading updates to the interface
- ✅ **Task Search** - Real-time search functionality across task titles and descriptions

### Organization & Filtering

- 🎯 **Priority Levels** - Categorize tasks as Low, Medium, High, or Critical
- 📊 **Status Tracking** - Manage task lifecycle with Todo, In Progress, Done, and Overdue statuses
- 🔍 **Advanced Filtering** - Filter by status, priority, or search terms
- 📈 **Dynamic Sorting** - Sort tasks by priority or status in ascending/descending order
- ⏰ **Automatic Overdue Detection** - Tasks past their due date automatically marked as Overdue

### Data Management

- 💾 **Persistent Storage** - All tasks saved to local SQLite database
- 📤 **CSV Export** - Export task data for external analysis or backup
- 📊 **Task Statistics** - View comprehensive statistics including task counts and completion percentages
- 🔐 **User Authentication** - Secure login with SHA-256 password hashing

### User Interface

- 🎨 **Modern Dark Theme** - Professional dark UI built with FlatLaf
- 📐 **Intuitive Layout** - BorderLayout design with clear separation of concerns
- ⌨️ **Keyboard Friendly** - Responsive controls and accessible input fields
- 📱 **Responsive Design** - Adaptive UI that scales to different screen sizes

## 🛠️ Tech Stack

| Component        | Technology      | Purpose                                    |
| ---------------- | --------------- | ------------------------------------------ |
| **Language**     | Java 25         | Core application logic                     |
| **Build Tool**   | Maven 4.0       | Dependency management and build automation |
| **UI Framework** | Swing           | Cross-platform desktop interface           |
| **Database**     | SQLite 3        | Lightweight, file-based data persistence   |
| **Theming**      | FlatLaf         | Modern look and feel                       |
| **Data Export**  | OpenCSV         | CSV file generation                        |
| **Date Picker**  | LGoodDatePicker | User-friendly date selection               |
| **JSON**         | Gson            | Potential data serialization               |

## 📁 Project Structure

```
todolist/
├── src/
│   ├── main/
│   │   ├── java/todolist/
│   │   │   ├── Gui.java                      # Main application window & event orchestration
│   │   │   ├── Task.java                     # Task domain model
│   │   │   ├── TaskController.java           # Task business logic & filtering
│   │   │   ├── DatabaseManager.java          # SQLite connection & schema
│   │   │   ├── TaskDBExecute.java            # Task CRUD operations
│   │   │   ├── AuthManager.java              # User authentication
│   │   │   ├── LoginDialog.java              # Login UI component
│   │   │   ├── User.java                     # User model
│   │   │   ├── CSV.java                      # CSV export utility
│   │   │   ├── Priority.java                 # Priority enum
│   │   │   ├── Status.java                   # Task status enum
│   │   │   ├── Theme.java                    # UI styling constants
│   │   │   ├── TaskFormPanelWest.java        # Task input form panel
│   │   │   ├── ViewTasksCenterPanel.java     # Task table view
│   │   │   ├── UpperOptionsPanel.java        # Top toolbar panel
│   │   │   └── OverviewBottomPanel.java      # Statistics panel
│   │   └── resources/
│   └── test/
│       └── java/
├── target/                                    # Compiled output
├── pom.xml                                    # Maven configuration
└── todolist.db                                # SQLite database (generated)
```

### Component Responsibilities

| Class               | Responsibility                                                    |
| ------------------- | ----------------------------------------------------------------- |
| **Gui**             | Application lifecycle, window management, event orchestration     |
| **Task**            | Task domain model with getters/setters and string representation  |
| **TaskController**  | Task CRUD, validation, filtering, in-memory collection management |
| **DatabaseManager** | Database connection, table schema initialization                  |
| **TaskDBExecute**   | SQL operations: insert, update, delete, select                    |
| **AuthManager**     | User authentication, password hashing (SHA-256)                   |
| **CSV**             | Export task data to CSV format                                    |
| **UI Panels**       | Modular view components (form, table, options, statistics)        |

## 🚀 Installation

### Prerequisites

- **Java Development Kit (JDK) 25+** - [Download from Oracle](https://www.oracle.com/java/technologies/downloads/)
- **Maven 4.0+** - [Download from Apache Maven](https://maven.apache.org/download.cgi)
- **Git** (optional, for cloning the repository)

### Setup Steps

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourusername/todolist-manager.git
   cd todolist-manager/todolist
   ```

2. **Build the project**

   ```bash
   mvn clean install
   ```

3. **Run the application**

   ```bash
   # Option 1: From source
   mvn exec:java -Dexec.mainClass="todolist.Gui"

   # Option 2: From JAR
   java -jar target/todolist-version-1-0-0.jar
   ```

## 📖 Usage

### Getting Started

1. **Launch the Application** - Run the JAR file or use Maven exec
2. **Create User Account** - Click "Create User" and set your credentials (or use default demo account)
3. **Login** - Enter your username and password
4. **Start Managing Tasks** - Use the form panel on the left to create your first task

### Main Operations

#### Creating a Task

1. Fill in the task form on the left panel:
   - **Title** (4-30 characters required)
   - **Description** (10-200 characters required)
   - **Priority** - Select from Low, Medium, High, Critical
   - **Status** - Select from Todo, In Progress, Done (Overdue auto-assigned if date passed)
   - **Due Date** - Use date picker (format: YYYY-MM-DD)
2. Click **"New Task"** to add the task

#### Editing a Task

1. Click on a task in the center table to select it
2. Click **"Edit Task"** button
3. Modify the fields in the form panel
4. Click **"Save"** to persist changes

#### Deleting a Task

1. Select a task from the table
2. Click **"Delete Task"**
3. Confirm the deletion

#### Filtering & Searching

- **Search Bar** - Type to filter tasks by title/description (real-time)
- **Status Filter** - Dropdown to show only tasks with specific status
- **Priority Filter** - Dropdown to show only tasks with specific priority
- **Sort Options** - Sort by status or priority in ascending/descending order

#### Exporting Data

- Click **"Export CSV"** to save current table view to `todolist.csv`
- CSV file includes: ID, Title, Description, Priority, Status, Due Date

#### Viewing Statistics

- Click **"Statistics"** to see:
  - Total task count
  - Breakdown by status (Todo, In Progress, Done, Overdue)
  - Completion and progress percentages

## 🏗️ Architecture

### Design Patterns

**Model-View-Controller (MVC)**

- **Model**: `Task`, `User` - Domain objects with data and business rules
- **View**: UI panels (`TaskFormPanelWest`, `ViewTasksCenterPanel`, etc.) - Presentation layer
- **Controller**: `TaskController`, `AuthManager` - Business logic orchestration

**Singleton Pattern**

- `TaskController`, `AuthManager`, `DatabaseManager` - Utilities with private constructors

**Observer Pattern**

- Event listeners for button clicks, text changes, and UI updates

### Data Flow

```
User Input (UI)
    ↓
Event Handler (ActionListener)
    ↓
TaskController (Validation & Business Logic)
    ↓
TaskDBExecute (Database Operations)
    ↓
SQLite Database (Persistence)
    ↓
UI Update (Refresh Table, Counter, etc.)
```

### Database Schema

```sql
-- Tasks Table
CREATE TABLE tasks (
    id          INTEGER PRIMARY KEY,
    title       TEXT NOT NULL,
    description TEXT,
    priority    TEXT,
    status      TEXT,
    due_date    TEXT
);

-- Users Table
CREATE TABLE users (
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL  -- SHA-256 hashed
);
```

## 🔐 Security Considerations

- **Password Security**: All passwords hashed using SHA-256 algorithm
- **Input Validation**: Comprehensive validation of task and user input
- **SQL Injection Prevention**: PreparedStatements used for all database queries
- **Local Storage**: Data stored locally with SQLite (no external connections)

## 🐛 Known Limitations

- Single-user per session (application closes when user logs out)
- No task assignment or collaboration features
- No cloud synchronization
- SQLite file located in relative path (may differ across IDEs)

## 📝 Development

### Building from Source

```bash
# Clone repository
git clone https://github.com/yourusername/todolist-manager.git
cd todolist-manager/todolist

# Build JAR
mvn clean package

# Run tests (if available)
mvn test

# Clean build artifacts
mvn clean
```

### Project Statistics

- **Lines of Code**: ~2,500+
- **Classes**: 17
- **Methods**: 100+
- **Database Tables**: 2

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch** (`git checkout -b feature/amazing-feature`)
3. **Make your changes** with clear, descriptive commits
4. **Test thoroughly** - Ensure all existing features still work
5. **Submit a Pull Request** with a detailed description of your changes

### Areas for Contribution

- [ ] Unit and integration tests
- [ ] Database migration system
- [ ] Recurring task support
- [ ] Task categories/tags
- [ ] Due date notifications
- [ ] Dark/Light theme toggle
- [ ] Multi-user support
- [ ] Cloud synchronization

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**Santiago GS**

- GitHub: [@yourusername](https://github.com/yourusername)
- Email: your.email@example.com

## 🙏 Acknowledgments

- [FlatLaf](https://www.formdev.com/flatlaf/) - Modern Look and Feel for Java Swing
- [OpenCSV](http://opencsv.sourceforge.net/) - CSV parsing library
- [LGoodDatePicker](https://github.com/LGoodDatePicker/LGoodDatePicker) - Date picker component
- Java Swing documentation and community examples

## 📞 Support

For issues, questions, or suggestions:

1. Check existing [GitHub Issues](https://github.com/yourusername/todolist-manager/issues)
2. Create a new issue with detailed description
3. Include steps to reproduce any bugs

## 🗺️ Roadmap

### Version 1.1 (Planned)

- [ ] Task categories/tags
- [ ] Recurring tasks
- [ ] Task reminders/notifications
- [ ] Dark/Light theme toggle

### Version 2.0 (Future)

- [ ] Multi-user collaboration
- [ ] Cloud synchronization
- [ ] Mobile companion app
- [ ] Team workspaces

## Task States And Priorities

Available priorities:

- LOW
- MEDIUM
- HIGH
- CRITICAL

Available statuses:

- TODO
- INPROGRESS
- DONE
- OVERDUE

## Data Files

- `todolist.db` - SQLite database used by the application
- `todolist.csv` - CSV export generated from the table view

## Requirements

- Java 16 or newer
- Maven 3.x

## Build

From the project root:

```bash
mvn clean compile
```

## Run

The main launcher is `todolist.Gui`. You can run it from your IDE or configure a Maven/IDE run target for that class.

If you want to run from the command line, first compile the project and then launch the `todolist.Gui` class from your preferred Java runtime or IDE run configuration.

## Notes

- `Main.java` is currently an empty entry point.
- The application uses the GUI class as the real startup class.
- The search field is present in the interface, but its behavior is limited in the current codebase.

## Screenshots

Add project screenshots here if you want to extend the documentation.
