package todolist;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskDBExecute {

    /**
     * Inserts one task into the SQLite tasks table.
     *
     * @param task task to save in the database
     */
    public static void insertTask(Task task) {
        String sql = "INSERT INTO tasks (id, title, description, priority, status, due_date) VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement statementSQL = DatabaseManager.getConnection().prepareStatement(sql)) {
            statementSQL.setInt(1, task.getTaskId());
        	statementSQL.setString(2, task.getTaskTitle());
        	statementSQL.setString(3, task.getTaskDescription());
        	statementSQL.setString(4, task.getTaskPriority().toString());
        	statementSQL.setString(5, task.getTaskStatus().toString());
        	statementSQL.setString(6, task.getTaskDueDate().toString());
        	statementSQL.executeUpdate();
            System.out.println("Task inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads every saved task from SQLite and converts the rows back into Task objects.
     *
     * @return list of tasks loaded from the database
     */
    public static ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (Statement statement = DatabaseManager.getConnection().createStatement();
             ResultSet resultFromExecutedQuery = statement.executeQuery(sql)) {
            while (resultFromExecutedQuery.next()) {
            	String[] dueDateParts = resultFromExecutedQuery.getString("due_date").split("-");
            	if(!TaskController.dateController(dueDateParts, Status.valueOf(resultFromExecutedQuery.getString("status")))) {
            		continue;
            	}
            	List<Integer> list = Arrays.stream(dueDateParts).map(Integer::parseInt).toList();
            	LocalDate dueDateDB = LocalDate.of(list.get(0), list.get(1), list.get(2));
            	Status rowStatus = Status.valueOf(resultFromExecutedQuery.getString("status"));
            	if(dueDateDB.isBefore(LocalDate.now())) {
            		rowStatus = Status.OVERDUE;
            	}
                Task t = new Task(resultFromExecutedQuery.getString("title"),resultFromExecutedQuery.getString("description"),
                    Priority.valueOf(resultFromExecutedQuery.getString("priority")),
                    rowStatus, dueDateDB);
                tasks.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
    
    /**
     * Updates one existing database row with the current values of a task.
     *
     * @param task task whose database row should be updated
     */
    public static void updateTask(Task task) {
        String sql = "UPDATE tasks SET title=?, description=?, priority=?, status=?, due_date=? WHERE id=?;";
        try (PreparedStatement statement = DatabaseManager.getConnection().prepareStatement(sql)) {
        	statement.setString(1, task.getTaskTitle());
        	statement.setString(2, task.getTaskDescription());
        	statement.setString(3, task.getTaskPriority().toString());
        	statement.setString(4, task.getTaskStatus().toString());
        	statement.setString(5, task.getTaskDueDate().toString());
        	statement.setInt(6, task.getTaskId());
        	statement.executeUpdate();
            System.out.println("Task updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Deletes one task row from the database by id.
     *
     * @param id id of the task to delete
     */
    public static void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id=?";
        try (PreparedStatement statement = DatabaseManager.getConnection().prepareStatement(sql)) {
        	statement.setInt(1, id);
        	statement.executeUpdate();
           // System.out.println("Task deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * Attempts to update all saved tasks in the database.
     */
    public static void updateAllTasks(){
        editAllTasks();

    }

    /**
     * Writes every task currently in memory back to its database row.
     */
    public static void editAllTasks() {
    	for(Task t: TaskController.tasks) {
    		updateTask(t);
    	}
    }
}
