package todolist;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class Task {
	
	public static int counter = 0;
	private int id;
	private String title;
	private String description;
	private Priority priority;
	private Status status;
	private LocalDate dueDate;
	
	public static HashSet<String> titles = new HashSet<String>();
	public static HashMap<Status, ArrayList<Task>> statuses = new HashMap<Status, ArrayList<Task>>();
	public static HashMap<Priority, ArrayList<Task>> priorities = new HashMap<Priority, ArrayList<Task>>();
	
	/**
	 * Prevents creating an empty task without the required task fields.
	 */
	private Task() {} // We coded it but we are probably not using it.
	
	/**
	 * Creates a task with a new sequential id and stores its main data fields.
	 *
	 * @param title task title shown in the table
	 * @param description longer task description
	 * @param taskPriority priority assigned to the task
	 * @param taskStatus current status of the task
	 * @param taskDeadline due date for the task
	 */
	public Task(String title, String description, Priority taskPriority, Status taskStatus, LocalDate taskDeadline) {
		// Each time that we create a task and first add and then assign that we can start from 1. set title, and then add it to the titles hashSet
		// and also add the array to the hashmap of statuses, and priorities to track the quantity of tasks for each type of priority or status. 
		this.id = ++counter;
		this.title = title; titles.add(title); 
		this.description = description;
		this.priority = taskPriority; Task.getPriorities(taskPriority, TaskController.tasks);
		this.status = taskStatus; Task.getStatuses(status, TaskController.tasks);
		this.dueDate = taskDeadline;
	}
	
	/**
	 * Rebuilds the status tracking map from the provided task list.
	 *
	 * @param status status key used when storing the generated lists
	 * @param array task list used to create the status groups
	 */
	private static void getStatuses(Status status, ArrayList<Task> array) {
		ArrayList<Task> tasksTodo = new ArrayList<Task>(array.stream().filter( task -> task.getTaskStatus() == Status.TODO).toList());
		ArrayList<Task> tasksDone = new ArrayList<Task>(array.stream().filter( task -> task.getTaskStatus() == Status.DONE).toList());
		ArrayList<Task> tasksInProgress = new ArrayList<Task>(array.stream().filter( task -> task.getTaskStatus() == Status.INPROGRESS).toList());
		ArrayList<Task> tasksOverdue = new ArrayList<Task>(array.stream().filter( task -> task.getTaskStatus() == Status.OVERDUE).toList());

		statuses.put(status, tasksTodo);
		statuses.put(status, tasksInProgress);
		statuses.put(status, tasksDone);
		statuses.put(status, tasksOverdue);
	}
	
	/**
	 * Rebuilds the priority tracking map from the provided task list.
	 *
	 * @param priority priority key used when storing the generated lists
	 * @param array task list used to create the priority groups
	 */
	private static void getPriorities(Priority priority, ArrayList<Task> array) {
		ArrayList<Task> tasksLowPriority = new ArrayList<Task>(array.stream().filter((task) -> task.getTaskPriority() == Priority.LOW).toList());
		ArrayList<Task> tasksMediumPriority = new ArrayList<Task>(array.stream().filter( task -> task.getTaskPriority() == Priority.MEDIUM).toList());
		ArrayList<Task> tasksHighPriority = new ArrayList<Task>(array.stream().filter( task -> task.getTaskPriority() == Priority.HIGH).toList());
		ArrayList<Task> tasksCriticalPriority = new ArrayList<Task>(array.stream().filter( task -> task.getTaskPriority() == Priority.CRITICAL).toList());
		
		priorities.put(priority, tasksLowPriority);
		priorities.put(priority, tasksMediumPriority);
		priorities.put(priority, tasksHighPriority);
		priorities.put(priority, tasksCriticalPriority);
		
	}
	
	/**
	 * @param id new visible task id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param title new task title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param description new task description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param p new task priority
	 */
	public void setPriority(Priority p) {
		this.priority = p;
	}

	/**
	 * @param s new task status
	 */
	public void setStatus(Status s) {
		this.status = s;
	}

	/**
	 * @param dueDate new task due date
	 */
	public void setTaskDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
	/**
	 * @return visible task id
	 */
	public int getTaskId() {
		return this.id;
	}

	/**
	 * @return task title
	 */
	public String getTaskTitle(){
		return this.title;
	}

	/**
	 * @return task description
	 */
	public String getTaskDescription(){
		return this.description;
	}

	/**
	 * @return task priority
	 */
	public Priority getTaskPriority() {
		return this.priority;
	}

	/**
	 * @return task due date
	 */
	public LocalDate getTaskDueDate() {
		return this.dueDate;
	}

	/**
	 * @return task status
	 */
	public Status getTaskStatus() {
		return this.status;
	}

	/**
	 * @return readable multi-line representation of the task
	 */
	@Override
	public String toString() {
	    return "Task ID: " + id + "\n" +
	           "Title: " + title + "\n" +
	           "Description: " + description + "\n" +
	           "Priority: " + priority + "\n" +
	           "Status: " + status + "\n" +
	           "Due Date: " + dueDate + "\n";
	 }
}
