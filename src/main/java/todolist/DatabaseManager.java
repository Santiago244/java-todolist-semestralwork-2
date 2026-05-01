package todolist;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
	// Attributes for connecting the sqlite database.
    private static final String DB_URL = "jdbc:sqlite:todolist.db";
    private static Connection connection;

    /**
     * Opens the SQLite connection and creates the required tables if needed.
     */
    public static void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            // System.out.println("SQLite connected.");
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the SQLite connection when it is open.
     */
    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                // System.out.println("SQLite disconnected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return active SQLite connection used by database helper classes
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Creates the tasks table schema used for CRUD operations.
     */
    private static void createTables() {
        String tasksSql = """
                CREATE TABLE IF NOT EXISTS tasks (
                    id          INTEGER PRIMARY KEY, 
                    title       TEXT    NOT NULL,
                    description TEXT,
                    priority    TEXT,
                    status      TEXT,
                    due_date    TEXT
                );
                """;

        String usersSql = """
                CREATE TABLE IF NOT EXISTS users (
                    id       INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL
                );
                """;
        // asking if I should use autoincrement or the counter from the tasks for the source of the id.
        // The statement creates a link to sql language so that we are able to execute sql statements.
        try (Statement statementForCreation = DatabaseManager.getConnection().createStatement()) {
        	statementForCreation.execute(tasksSql);
            statementForCreation.execute(usersSql);
            // System.out.println("Table ready.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints all saved tasks to the console for debugging database contents.
     */
    public static void printAllTasks() {
        String sql = "SELECT * FROM tasks;";
        try (Statement statementForCreation = DatabaseManager.getConnection().createStatement();
             ResultSet resultFromExecuted = statementForCreation.executeQuery(sql)) {
            while (resultFromExecuted.next()) {
                System.out.println(
                	resultFromExecuted.getInt("id") + " | " +
                	resultFromExecuted.getString("title") + " | " +
                	resultFromExecuted.getString("description") + " | " +
                	resultFromExecuted.getString("priority") + " | " +
                	resultFromExecuted.getString("status") + " | " +
                	resultFromExecuted.getString("due_date")
                );
            }
            System.out.println("There is next? in database: " +  resultFromExecuted.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
