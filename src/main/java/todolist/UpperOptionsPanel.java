package todolist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UpperOptionsPanel extends JPanel {
	
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private JButton createCSV = new JButton("Create CSV");
	private JButton createTask = new JButton();
	private JButton editTask = new JButton();
	private JButton deleteTask = new JButton();
	private JLabel searchLabel = new JLabel();
	private JTextField searchBar = new JTextField(20);
	private JButton saveEditButton = new JButton();
	private JLabel filtersJLabel = new JLabel("Filters:");
	private JComboBox<Status> statusesFilter = new JComboBox<Status>();
	private JComboBox<Priority> prioritiesFilter = new JComboBox<Priority>();
	private JLabel sortersJLabel = new JLabel("Sorters:");
	private JComboBox<String> statusesSort = new JComboBox<String>();
	private JComboBox<String> prioritiesSort = new JComboBox<String>();
	private JButton statisticsButton = new JButton("Statistics");
	
	/**
	 * Creates the top toolbar with action buttons, search bar, and filters.
	 */
	public UpperOptionsPanel() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));		
		// this.setBackground(Theme.BG_PRIMARY);
		// this.setForeground(Theme.TEXT_PRIMARY);
		this.setPreferredSize(new Dimension(0, 90));
		
		// Setting the text of the buttons, and textfield to be editable
		createCSV.setText("Create CSV"); buttons.add(createCSV);
		createTask.setText("+ Add Task"); buttons.add(createTask);
		editTask.setText("Edit Task"); buttons.add(editTask);
		deleteTask.setText("Delete Task"); buttons.add(deleteTask);
		searchLabel.setText("Search:");
		searchLabel.setAlignmentY(CENTER_ALIGNMENT);
		searchBar.setEditable(true);
		saveEditButton.setText("Save Changes");
		saveEditButton.setVisible(false); // By default
		
		// Filtering
		statusesFilter.addItem(Status.ALL); statusesFilter.addItem(Status.TODO);statusesFilter.addItem(Status.INPROGRESS);statusesFilter.addItem(Status.DONE);statusesFilter.addItem(Status.OVERDUE);
		prioritiesFilter.addItem(Priority.ALL); prioritiesFilter.addItem(Priority.LOW);prioritiesFilter.addItem(Priority.MEDIUM);prioritiesFilter.addItem(Priority.HIGH);prioritiesFilter.addItem(Priority.CRITICAL);
		// Sorting
		statusesSort.addItem("ALL"); statusesSort.addItem("TODO -> OVERDUE"); statusesSort.addItem("OVERDUE -> TODO");
		prioritiesSort.addItem("ALL"); prioritiesSort.addItem("LOW -> CRITICAL"); prioritiesSort.addItem("CRITICAL -> LOW");
		
		
		// CSV, Create, Edit (read, and update), Delete, Search
		this.add(createCSV);
		this.add(createTask);
		this.add(editTask);
		this.add(deleteTask);  
		this.add(searchLabel);
		this.add(searchBar, BorderLayout.EAST);
		this.add(saveEditButton);
		// Filters
		this.add(filtersJLabel);
		this.add(statusesFilter);
		this.add(prioritiesFilter);
		// Sorters
		this.add(sortersJLabel);
		this.add(statusesSort);
		this.add(prioritiesSort);
		this.add(statisticsButton);
		
	} // Empty private constructor
	
	/**
	 * @return button used to export the visible table to CSV
	 */
	public JButton getCreateCSV() {
		return createCSV;
	}
	
	/**
	 * @return button used to create a new task
	 */
	public JButton getCreateTask() {
	    return createTask;
	}

	/**
	 * @return button used to start editing the selected task
	 */
	public JButton getEditTask() {
	    return editTask;
	}

	/**
	 * @return button used to delete the selected task
	 */
	public JButton getDeleteTask() {
	    return deleteTask;
	}

	/**
	 * @return label displayed next to the search bar
	 */
	public JLabel getSearchLabel() {
	    return searchLabel;
	}

	/**
	 * @return text field used to search/filter tasks
	 */
	public JTextField getSearchBar() {
	    return searchBar;
	}
	
	/**
	 * @return button used to save edits after a task is selected
	 */
	public JButton getSaveButton() {
		return saveEditButton;
	}
	
	public JLabel getFiltersJLabel() {
		return filtersJLabel;
	}
	/**
	 * @return combo box used to filter tasks by priority
	 */
	public JComboBox<Priority> getPriorityFilter(){
		return prioritiesFilter;
	}
	
	/**
	 * @return combo box used to filter tasks by status
	 */
	public JComboBox<Status> getStatusFilter(){
		return statusesFilter;
	}

	/**
	 * @return combo box used to sort tasks by priority
	 */
	public JComboBox<String> getPrioritySort(){
		return prioritiesSort;
	}
	
	/**
	 * @return combo box used to sort tasks by status
	 */
	public JComboBox<String> getStatusSort(){
		return statusesSort;
	}

	/**
	 * @return button used to open the statistics dialog
	 */
	public JButton getStatisticsButton() {
		return statisticsButton;
	}

	
	/**
	 * Toggles the visibility of the save button when entering/leaving edit mode.
	 */
	public void displaySaveEditButton() {
		if(this.saveEditButton.isVisible()) {this.saveEditButton.setVisible(false);} else {this.saveEditButton.setVisible(true);}
	}
	
	/**
	 * Enables or disables the normal toolbar buttons while a task is being edited.
	 *
	 * @param canEdit true while the user is editing a task
	 */
	public void blockOtherButtons(boolean canEdit) {
		// TODO Auto-generated method stub
		for(JButton b: this.buttons) {
			b.setEnabled(!canEdit);
		}
	}
	
}
