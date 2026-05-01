package todolist;

import java.awt.Component;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TaskController {
	private static Task task; 
	public  static ArrayList<Task> tasks = new ArrayList<Task>();
	
	private TaskController() {
        // works like a singleton, but is not one formally
    }

	/**
	 * Validates the west form panel and creates a new Task from its values.
	 * The created task is saved as the current task and added to the in-memory list.
	 *
	 * @param westPanel form panel containing the new task input
	 * @return a TaskController instance when validation passes, otherwise null
	 */
	public static TaskController createTaskController(TaskFormPanelWest westPanel) {
		TaskController taskController = new TaskController();
		//System.out.println("Validation is: " + taskController.validationWestPanel(westPanel));
		if(validationWestPanel(westPanel)) {
			String title = westPanel.getTitleField();
			String description = westPanel.getDescriptionField();
			Priority p = westPanel.getSelectedPriority();
			Status s = westPanel.getSelectedStatus();
			List<Integer> deadline = Arrays.stream(westPanel.getDateField()).map(Integer::parseInt).toList();
			LocalDate ld = LocalDate.of(deadline.get(0), deadline.get(1), deadline.get(2)); // Formatting to localdate type
			if(ld.isBefore(LocalDate.now())) {
				s = Status.OVERDUE;
			}
			
			task = new Task(title, description, p, s, ld);
			tasks.add(task);
			return taskController;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Stores the task currently being created or edited.
	 *
	 * @param t task to mark as the current task
	 */
	public static void setTask(Task t) {
		task = t;
	}
	
	/**
	 * @return task currently being created or edited
	 */
	public static Task getTask() {
		return task;
	}
	
	/**
	 * Returns all tasks that have the selected status.
	 *
	 * @param s status used for filtering
	 * @return list of tasks matching the given status
	 */
	public static ArrayList<Task> getTasksByStatus(Status s) {
		ArrayList<Task> taskssorted = new ArrayList<Task>();
		for(Task t: tasks) {
			if(t.getTaskStatus().equals(s)) taskssorted.add(t); else continue;
		}
		return taskssorted;
	}

	/**
	 * Returns all tasks that have the selected priority.
	 *
	 * @param p priority used for filtering
	 * @return list of tasks matching the given priority
	 */
	public static ArrayList<Task> getTasksByPriority(Priority p) {
		ArrayList<Task> taskssorted = new ArrayList<Task>();
		for(Task t: tasks) {
			if(t.getTaskPriority().equals(p)) taskssorted.add(t); else continue;
		}
		return taskssorted;
	}
	
	/**
	 * Shows a simple message dialog attached to the given parent component.
	 *
	 * @param parent component used as the dialog parent
	 * @param message text shown to the user
	 */
	public static void messageJOptionPanel(Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message);
	}
	
	/**
	 * Finds a task by its visible id.
	 *
	 * @param id task id to search for
	 * @return first task whose id matches
	 */
	public static Task getTaskById(int id){
		return tasks.stream().filter(t -> t.getTaskId() == id).toList().get(0);
	}
	
	/**
	 * Updates an existing task with values from the west form panel.
	 *
	 * @param id id of the task being edited
	 * @param west form panel containing the new task values
	 */
	public static void editTaskById(int id, TaskFormPanelWest west) {
		Task pastTask = getTaskById(id);
		if(pastTask != null) {
			pastTask.setTitle(west.getTitleField());
			pastTask.setDescription(west.getDescriptionField());
			pastTask.setPriority(west.getSelectedPriority());
			List<Integer> dueDate = Arrays.stream(west.getDateField()).map(Integer::parseInt).toList();
			LocalDate editedDate = LocalDate.of(dueDate.get(0), dueDate.get(1), dueDate.get(2));
			Status editedStatus = west.getSelectedStatus();
			if(editedDate.isBefore(LocalDate.now())) {
				editedStatus = Status.OVERDUE;
			}
			pastTask.setStatus(editedStatus);
			pastTask.setTaskDueDate(editedDate);
		}
	}
	
	/**
	 * Removes a task from memory by id and resets the remaining task ids.
	 *
	 * @param id id of the task to remove
	 */
	public static void removeTaskById(int id){
		// Removes the task from the tasks arraylist, then resets the ids of the remaining tasks so they are in order for the user.
		tasks.removeIf(t -> t.getTaskId() == id);
		TaskController.resetIds();
	}
	
	/**
	 * Renumbers task ids after a deletion so the table displays sequential ids.
	 */
	public static void resetIds() {
		for(int i = 0; i < tasks.size();i ++) {
			tasks.get(i).setId(i+1);
		}
		Task.counter = tasks.size();
	}
	
	/**
	 * Validates title length, description length, and date format in the task form.
	 * Shows a dialog message when any value is invalid.
	 *
	 * @param westPanel form panel to validate
	 * @return true when the form contains valid task data
	 */
	public static boolean validationWestPanel(TaskFormPanelWest westPanel) {
		// here we validate title, description and deadline, since there is no possible way to fail in priorities and statuses.
		String title = westPanel.getTitleField();
		
		// Evaluate title, cannot be less than 4 chars or more than 16 chars
		if(title.length() < 4 || title.length() > 30) { TaskController.messageJOptionPanel(westPanel, "TITLE MUST BE AT LEAST 4 CHARS, AND MAX 30 CHARS, TRY AGAIN :)"); return false;}
		else {
				String description = westPanel.getDescriptionField();
				
				// Evaluate description cannot be less than 10 chars, or more than 200 chars
				if(description.length() < 10 || description.length() > 200) {TaskController.messageJOptionPanel(westPanel, "DESCRIPTION MUST BE AT LEAST 10 CHARS, AND MAX 200 CHARS, TRY AGAIN :)");  return false;}
				
				//Priority p = westPanel.getSelectedPriority();		
				Status s = westPanel.getSelectedStatus();
				
				// Priority and Status, there is no way to fail here, since there is already a valid default value chose.
				
				String[] deadline = westPanel.getDateField(); // cannot, we need to get it as the [] array
				if(TaskController.dateController(deadline, s)) {
					return true;
				}else {
					TaskController.messageJOptionPanel(westPanel, "INVALID FORMAT, READ THE DATE FORMAT AND TRY AGAIN :)"); 
					return false;
				}
		}
		
		
	}
	
	// Methods used inside the validation method
	
	/**
	 * Checks whether the date pieces can be parsed as a valid LocalDate.
	 *
	 * @param arrayDate date split into year, month, and day pieces
	 * @return true when the date has three parts and can become a LocalDate
	 */
	public static boolean dateController(String[] arrayDate, Status s) {
		if(s == Status.OVERDUE) {
			return true; // We can skip the date validation if the user selected overdue, since it will be automatically set when the user saves the task.
		}
		if (arrayDate.length == 3) {
				try {
					List<Integer> deadline = Arrays.stream(arrayDate).map(Integer::parseInt).toList(); // Using stream to learn ways to map a function to a group of elements and convert it to a list.
					LocalDate.of(deadline.get(0), deadline.get(1), deadline.get(2));
					return true;
				}catch(DateTimeException e) {
					System.out.println(e);
					e.getCause();
					return false;
				}
			
		}
		else {
			return false;
		}
		
	}

	/**
	 * Converts every task in memory into one combined string.
	 *
	 * @return text representation of all tasks
	 */
    public static String everythingToString() {
        StringBuffer superMegaHyperStringAI = new  StringBuffer();
        for(int i = 0; i < tasks.size(); i++) {
            superMegaHyperStringAI.append(tasks.get(i).toString());
        }
        return superMegaHyperStringAI.toString();
    }
}
