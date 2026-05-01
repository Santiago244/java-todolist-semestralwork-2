package todolist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class ViewTasksCenterPanel extends JPanel {
	private static String[] columnNames = { "ID", "TITLE", "DESCRIPTION", "PRIORITY", "STATUS", "DUE_DATE"}; 
	private DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
		/**
		 * Keeps table cells read-only so tasks can only be changed through the form.
		 */
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private JTable tasksTrackerTable = new JTable(model) {
		@Override
		public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
			Component cell = super.prepareRenderer(renderer, row, column);
			if (!isRowSelected(row)) {
				if (column == 5) {
					Status statusValue = (Status) getValueAt(row, 4);
					if(Status.OVERDUE.equals(statusValue)) {
						cell.setBackground(Color.red.darker());
						cell.setForeground(Color.WHITE);
					}
					else if(Status.DONE.equals(statusValue)) {
						cell.setBackground(Color.green.darker());
						cell.setForeground(Color.WHITE);
					}
					else {
						cell.setBackground(getBackground());
						cell.setForeground(getForeground());
					}
				} else {
					cell.setBackground(getBackground());
					cell.setForeground(getForeground());
				}
			}
			return cell;
		}
	};
	JScrollPane centerScrollPanel = new JScrollPane();
	
	
	/**
	 * Creates the center table panel and configures the task table columns.
	 */
	public ViewTasksCenterPanel() {
		this.setLayout(new BorderLayout());
		// this.setBackground(Theme.BG_PRIMARY);
		// this.setForeground(Theme.TEXT_PRIMARY); 
		this.setPreferredSize(new Dimension(700, 0)); // Center, west, and east ,I only control the width 
		
		// Inside the center panel a scrollPane
		
		centerScrollPanel.setAutoscrolls(true);
		//centerScrollPanel.setBackground(Theme.BG_PRIMARY);
		//centerScrollPanel.setForeground(Theme.TEXT_PRIMARY);
		// Inside the scrollPane, we input a JTable
		//tasksTrackerTable.getTableHeader().setBackground(Theme.BG_PRIMARY);
		tasksTrackerTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER); 
		TableColumnModel columnModel = tasksTrackerTable.getColumnModel();
 		 // TITLE — fixed 150 px
		TableColumn id = columnModel.getColumn(0);
		id.setCellRenderer(centerRenderer);
		id.setMaxWidth(60);
		id.setMinWidth(60);
		id.setPreferredWidth(60);
		
        TableColumn title = columnModel.getColumn(1);
        title.setCellRenderer(centerRenderer);
        title.setMinWidth(150);
        title.setMaxWidth(150);
        title.setPreferredWidth(150);

        // DESCRIPTION — flexible, only a reasonable minimum
        TableColumn description = columnModel.getColumn(2);
        description.setMinWidth(250);
        description.setPreferredWidth(250);

        // PRIORITY — fixed 85 px
        TableColumn priority = columnModel.getColumn(3);
        priority.setCellRenderer(centerRenderer);
        priority.setMinWidth(85);
        priority.setMaxWidth(85);
        priority.setPreferredWidth(85);

        // STATUS — fixed 95 px
        TableColumn status = columnModel.getColumn(4);
        status.setCellRenderer(centerRenderer);
        status.setMinWidth(95);
        status.setMaxWidth(95);
        status.setPreferredWidth(95);

        // DUE_DATE — fixed 110 px (dates are short strings)
        TableColumn dueDate = columnModel.getColumn(5);
        dueDate.setCellRenderer(centerRenderer);
        dueDate.setMinWidth(140);
        dueDate.setMaxWidth(140);
        dueDate.setPreferredWidth(140);
		
		//tasksTrackerTable.getTableHeader().setForeground(Theme.TEXT_PRIMARY);
		// tasksTrackerTable.setGridColor(Theme.DANGER);
		//tasksTrackerTable.setBackground(Theme.BG_PRIMARY);
		//tasksTrackerTable.setForeground(Theme.TEXT_PRIMARY);
		tasksTrackerTable.setName("To-Do-List-Task");
		// Adding: tasksTrackerTable to its parent component: scrollPane
		centerScrollPanel.setViewportView(tasksTrackerTable);
		
		// Adding: centerScrollPanel to its parent panel : viewTasksCenterPanel
		this.add(centerScrollPanel, BorderLayout.CENTER);
	}
	
	// Instance methods, could be use if we would create another westpanel for something else, they technically should be static
	
	/**
	 * Adds one task as a new row in the table model.
	 *
	 * @param t task to display in the table
	 */
	public void addTaskToTable(Task t) {  
		model.addRow(new Object[] {t.getTaskId(), t.getTaskTitle(), t.getTaskDescription(), t.getTaskPriority(), t.getTaskStatus(), t.getTaskDueDate().toString()});
	}
	
	/**
	 * Returns the table model used by the task table.
	 *
	 * @return the DefaultTableModel containing the visible task rows
	 */
	public  DefaultTableModel  getTasksTrackerTableModel() {
		return model;
	}

	/**
	 * Returns the JTable component that displays the tasks.
	 *
	 * @return the task tracker table
	 */
	public  JTable getTasksTrackerTable() {
		return tasksTrackerTable;
	} 
	
	/**
	 * Removes the selected task from the table, the in-memory task list, and
	 * the database. Shows a message if no row is selected.
	 */
	public void removeTaskFromTable() { // Calling this when the user clicks on remove task
		int rowindex = tasksTrackerTable.getSelectedRow(); // get selected row
		if (rowindex != -1) { 
			// Get task ID before removing the row
			int taskId = (int) model.getValueAt(rowindex, 0);
			model.removeRow(rowindex);
			TaskController.removeTaskById(taskId);
			TaskDBExecute.deleteTask(taskId);
		} else {
			TaskController.messageJOptionPanel(tasksTrackerTable, "You need to select the row by clicking on any part of its row");
		};
	}

	/**
	 * Finds the selected row and returns both its Task object and row index.
	 *
	 * @return Object array containing the selected Task and row index, or null
	 *         when no row is selected
	 */
	public Object[] getSelectedTaskFromTable() { 
		// Get the selected row
		int rowIndex = tasksTrackerTable.getSelectedRow();
		// Ensure there is a selected row at least
		if(rowIndex != -1) {
			// Get the id from the task selected and return the first task that matches this id
			int id = (int) tasksTrackerTable.getValueAt(rowIndex, 0);
			return new Object[] {TaskController.getTaskById(id), rowIndex}; 
		}
		else {
			TaskController.messageJOptionPanel(tasksTrackerTable, "You need to select the row by clicking on any part of its row");
			return null;
		}
	}
	
	/**
	 * Updates an existing task using the values currently written in the west
	 * form panel, then writes those values back into the selected table row.
	 *
	 * @param west form panel containing the edited task values
	 * @param rowIndex selected row index in the table
	 */
	public void editTaskFromTable(TaskFormPanelWest west, int rowIndex) {
		TaskController.validationWestPanel(west); // validate the edited information
		if(rowIndex >= 0) { // if the rowindex is valid then we edit the task in the tasks array.
			int id = (int) tasksTrackerTable.getValueAt(rowIndex, 0);
			TaskController.editTaskById(id, west);
			Task t = TaskController.getTask(); 
			if( t != null) {
				model.setValueAt(t.getTaskId(), rowIndex, 0);
				model.setValueAt(t.getTaskTitle(), rowIndex, 1);
				model.setValueAt(t.getTaskDescription(), rowIndex, 2);
				model.setValueAt(t.getTaskPriority(), rowIndex, 3);
				model.setValueAt(t.getTaskStatus(), rowIndex, 4);
				model.setValueAt(t.getTaskDueDate(), rowIndex, 5);
			}
		}
	}
	
	/**
	 * Rebuilds the table using every task stored in TaskController.tasks.
	 */
	public void refreshTable() {
	    model.setRowCount(0); // clear all rows
	    for (Task t : TaskController.tasks) {
	        model.addRow(new Object[]{
	            t.getTaskId(),
	            t.getTaskTitle(),
	            t.getTaskDescription(),
	            t.getTaskPriority(),
	            t.getTaskStatus(),
	            t.getTaskDueDate()
	        });
	    }
	}

	/**
	 * Rebuilds the table using only the tasks passed to this method.
	 *
	 * @param tasks task list that should be visible in the table
	 */
	public void refreshTable(ArrayList<Task> tasks) {
		model.setRowCount(0);
		for (Task t: tasks) {
			model.addRow(new Object[] {t.getTaskId(), t.getTaskTitle(), t.getTaskDescription(), t.getTaskPriority(), t.getTaskStatus(), t.getTaskDueDate()});
		}
	}

	/**
	 * Finds the tasks whose visible fields match the search text.
	 *
	 * @param query text typed by the user in the search bar
	 * @return tasks containing the query in any visible field
	 */
	public ArrayList<Task> searchTasks(String query) {
		String normalizedQuery = query;
		if(normalizedQuery == null) {
			normalizedQuery = "";
		} else {
			normalizedQuery = query.trim().toLowerCase();
		}
		ArrayList<Task> matchedTasks = new ArrayList<Task>();
		
		if(normalizedQuery.isEmpty()) {
			matchedTasks.addAll(TaskController.tasks);
			return matchedTasks;
		}
		
		for(Task task : TaskController.tasks) {
			String idValue = String.valueOf(task.getTaskId()).toLowerCase();
			String titleValue = String.valueOf(task.getTaskTitle()).toLowerCase();
			String descriptionValue = String.valueOf(task.getTaskDescription()).toLowerCase();
			String priorityValue = String.valueOf(task.getTaskPriority()).toLowerCase();
			String statusValue = String.valueOf(task.getTaskStatus()).toLowerCase();
			String dueDateValue = String.valueOf(task.getTaskDueDate()).toLowerCase();
			
			if(idValue.contains(normalizedQuery) || titleValue.contains(normalizedQuery) || descriptionValue.contains(normalizedQuery) || priorityValue.contains(normalizedQuery) || statusValue.contains(normalizedQuery) || dueDateValue.contains(normalizedQuery)) {
				matchedTasks.add(task);
			}
		}
		return matchedTasks;
	}

	/**
	 * Sorts the rows currently visible in the table from TODO up to OVERDUE.
	 */
	
	public void sortVisibleTableByStatusAscending() {
		sortVisibleRows(Comparator.comparingInt((Object[] row) -> statusRank((Status) row[4])));
	}
	/**
	 * Sorts the rows currently visible in the table from OVERDUE down to TODO.
	 */
	public void sortVisibleTableByStatusDescending() {
		sortVisibleRows(Comparator.comparingInt((Object[] row) -> statusRank((Status) row[4])).reversed());
	}

	/**
	 * Sorts the rows currently visible in the table from CRITICAL down to LOW.
	 */
	public void sortVisibleTableByPriorityAscending() {
		// 1 -> 4 (LOW -> CRITICAL)
		sortVisibleRows(Comparator.comparingInt((Object[] row) -> priorityRank((Priority) row[3])));
	}
    /**
	 * Sorts the rows currently visible in the table from CRITICAL down to LOW.
	 */
	public void sortVisibleTableByPriorityDescending() {
		// 4 -> 1 (CRITICAL -> LOW)
		sortVisibleRows(Comparator.comparingInt((Object[] row) -> priorityRank((Priority) row[3])).reversed());
	}

	/**
	 * Copies the visible table rows, sorts them, and writes them back into the model.
	 *
	 * @param comparator ordering rule used for the current table rows
	 */
	private void sortVisibleRows(Comparator<Object[]> comparator) {
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		for (int row = 0; row < model.getRowCount(); row++) {
			Object[] rowData = new Object[model.getColumnCount()];
			for (int column = 0; column < model.getColumnCount(); column++) {
				rowData[column] = model.getValueAt(row, column);
			}
			rows.add(rowData);
		}

		rows.sort(comparator);
		model.setRowCount(0);
		for (Object[] rowData : rows) {
			model.addRow(rowData);
		}
	}

	/**
	 * Converts a status value into a number so the table can be sorted.
	 * Higher numbers are shown first.
	 *
	 * @param statusValue value stored in the STATUS column
	 * @return sorting weight for the status
	 */
	private int statusRank(Status statusValue) {
		String statusValueString = String.valueOf(statusValue);
		if(statusValueString.equals("OVERDUE")) return 4;
		else if (statusValueString.equals("DONE")) return 3;
		else if (statusValueString.equals("INPROGRESS")) return 2;
		else if (statusValueString.equals("TODO")) return 1;
		else return 0;
	}

	/**
	 * Converts a priority value into a number so the table can be sorted.
	 * Higher numbers are shown first.
	 *
	 * @param priorityValue value stored in the PRIORITY column
	 * @return sorting weight for the priority
	 */
	private int priorityRank(Priority priorityValue) {
		String priorityValueString = String.valueOf(priorityValue);
		if(priorityValueString.equals("CRITICAL")) return 4;
		else if (priorityValueString.equals("HIGH")) return 3;
		else if (priorityValueString.equals("MEDIUM")) return 2;
		else if (priorityValueString.equals("LOW")) return 1;
		else return 0;
	}



}
