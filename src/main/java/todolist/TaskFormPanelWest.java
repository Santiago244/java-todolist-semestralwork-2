package todolist;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TaskFormPanelWest extends JPanel {
	
	//private JPanel taskFormPanelWest = new JPanel(new GridBagLayout());
	private GridBagConstraints gbc = new GridBagConstraints();
	private JLabel staticTitle = new JLabel("Title");
	private JTextField taskFormTitle = new JTextField(20);
	private JLabel staticDescription = new JLabel("Description");
	private JScrollPane taskFormScrollPanel = new JScrollPane();
	private JTextArea taskFormDescription = new JTextArea(10, 10);
	private JLabel staticPriorities = new JLabel("Priorities: ");
	private static JComboBox<Priority> taskFormPriorities = new JComboBox<Priority>();
	private JLabel staticStatus = new JLabel("Status: ");
	private static JComboBox<Status> taskFormStatus = new JComboBox<Status>();
	private JLabel taskDeadline = new JLabel();
	private static JFormattedTextField taskDateField = new JFormattedTextField();

	
	
	/**
	 * Creates the left-side form used to enter or edit task details.
	 */
	public TaskFormPanelWest() {
		this.setLayout(new GridBagLayout());
		//this.setBackground(Theme.BG_PRIMARY);
		// this.setForeground(Theme.TEXT_PRIMARY);
		this.setPreferredSize(new Dimension(300, 700));
		
		gbc.insets = new Insets(4, 8, 4, 8); // padding spaces around every component top, left, bottom, right
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		
		// Row 0 - Task Details - Title and description of the task 
		gbc.gridy = 0;
		//staticTitle.setBackground(Theme.BG_PRIMARY);
		//staticTitle.setForeground(Theme.TEXT_PRIMARY);
		this.add(staticTitle, gbc);
		// Row 1: title input
		gbc.gridwidth = 4;
		gbc.gridy = 1;
		this.add(taskFormTitle, gbc);
		
		// Row 2: Description Label
		gbc.gridy = 2; gbc.weighty = 0.0; gbc.gridwidth = 0;
		//staticDescription.setBackground(Theme.BG_PRIMARY);
		//staticDescription.setForeground(Theme.TEXT_PRIMARY);
		this.add(staticDescription, gbc);
		
		// Row 3: scrollabel text area
		gbc.gridy = 3; gbc.fill = GridBagConstraints.BOTH; gbc.gridwidth = 4; gbc.weighty = 0.5;
		// Adding the description to its parent component: taskFormScrollPanel
		taskFormScrollPanel.setViewportView(taskFormDescription);
		this.add(taskFormScrollPanel, gbc);

		// Row 4 -- Both in horizontal, meaning weightx = 0.5
		gbc.weighty = 0.1;
		gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 2; gbc.gridx = 0;gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
		// staticPriorities.setForeground(Theme.TEXT_PRIMARY);
		this.add(staticPriorities, gbc);
		
		// Adding priority options
		gbc.gridwidth = 2;gbc.gridx = 1; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_END;
		taskFormPriorities.setPreferredSize(new Dimension(120, 30));
		taskFormPriorities.addItem(Priority.LOW);
		taskFormPriorities.addItem(Priority.MEDIUM);
		taskFormPriorities.addItem(Priority.HIGH);
		taskFormPriorities.addItem(Priority.CRITICAL);
		this.add(taskFormPriorities, gbc);
		
		gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 2; gbc.gridx = 0;gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;
		// staticStatus.setForeground(Theme.TEXT_PRIMARY);
		this.add(staticStatus, gbc);
		
		gbc.gridwidth = 2;gbc.gridx = 1; gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_END; 
		// Adding status options
		taskFormStatus.setPreferredSize(new Dimension(120, 30));
		taskFormStatus.addItem(Status.TODO);
		taskFormStatus.addItem(Status.INPROGRESS);
		taskFormStatus.addItem(Status.DONE);
		taskFormStatus.addItem(Status.OVERDUE);
		
		this.add(taskFormStatus, gbc);
		
		gbc.gridy = 6; gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START; gbc.gridx = 0;
		taskDeadline.setText("Date: YYYY-MM-DD: ");
		this.add(taskDeadline, gbc);
		gbc.gridwidth = 2;gbc.weightx = 0.5; gbc.anchor = GridBagConstraints.LINE_START;gbc.gridx = 1;
        taskDateField.setPreferredSize(new Dimension(100, 20));
        taskDateField.setColumns(10);
        taskDateField.setText(null);
		this.add(taskDateField, gbc);
		
		
	}
	
	/**
	 * @return text typed in the title field
	 */
	public String getTitleField() {
	    return taskFormTitle.getText();
	}

	/**
	 * @return text typed in the description area
	 */
	public String getDescriptionField() {
	    return taskFormDescription.getText();
	}

	/**
	 * Reads the selected priority from the priority combo box.
	 *
	 * @return selected Priority, or null if the selected item cannot be cast
	 */
	public Priority getSelectedPriority() {
		try {
			Priority p = (Priority) taskFormPriorities.getSelectedItem();
			return p;
		}catch(ClassCastException e ) {
			System.out.print(e);
			return null;
		}
		
	}
	
	/**
	 * Reads the selected status from the status combo box.
	 *
	 * @return selected Status, or null if the selected item cannot be cast
	 */
	public Status getSelectedStatus() { 
		try {
			Status s = (Status) taskFormStatus.getSelectedItem(); 
			return s;
		}catch(ClassCastException e ) {
			System.out.print(e);
			return null;
		}
	}

	/**
	 * Splits the date field into year, month, and day parts.
	 *
	 * @return date parts split by "-", or an empty string array when unavailable
	 */
	public String[] getDateField() { // Getter returns null if the dateField is empty
		String date = taskDateField.getText();
		String[] array = date.split("-");
		if(array != null) {
			return array;
		}
		else {
			return new String[]{""};
		}
		
	}


	/**
	 * Fills the form fields with an existing task so the user can edit it.
	 *
	 * @param task task whose data should be displayed
	 */
	public void displayDataToEdit(Task task) {
		// TODO Auto-generated method stub
		if(task != null) {
			taskFormTitle.setText(task.getTaskTitle());
			taskFormDescription.setText(task.getTaskDescription());
			taskFormPriorities.setSelectedItem(task.getTaskPriority());
			taskFormStatus.setSelectedItem(task.getTaskStatus());
			taskDateField.setText(task.getTaskDueDate().toString());
		}
	}	
	
	/**
	 * Clears the form and restores the default priority/status selections.
	 */
	public void cleanDataEdited() {
		
		// Default state of the westpanel
		taskFormTitle.setText("");
		taskFormDescription.setText("");
		taskFormPriorities.setSelectedItem(Priority.LOW);
		taskFormStatus.setSelectedItem(Status.TODO);
		taskDateField.setText("");
	}
	
}
