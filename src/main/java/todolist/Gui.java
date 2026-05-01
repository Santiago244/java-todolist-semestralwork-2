package todolist;
import com.formdev.flatlaf.FlatDarkLaf;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Gui {
	private LocalDate timedate;
	private JFrame frame;
	private ArrayList<JPanel> panels = new ArrayList<JPanel>();
	/**
	 * Starts the Swing application on the event dispatch thread.
	 * It also sets the FlatLaf theme and opens the SQLite connection.
	 */
	public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	    	/**
	    	 * Runs the application startup code on Swing's event dispatch thread.
	    	 */
	        public void run() {
	            try {
	            	FlatDarkLaf.setup();   // or FlatDarkLaf.setup()
	            	DatabaseManager.connect();
					User.printUsers();
					JFrame loginFrame = new JFrame();
					loginFrame.setBounds(10, 50,500, 400);
	            	LoginDialog loginDialog = new LoginDialog(loginFrame);
	            	loginDialog.setVisible(true);
	            	if (!loginDialog.isAuthenticated()) {
	            		DatabaseManager.disconnect();
	            		return;
	            	}
	            	//DatabaseManager.printAllTasks();
	                Gui window = new Gui();
	                window.frame.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}

	/**
	 * Creates the main GUI window and initializes all panels/listeners.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Builds the main frame, creates the panels, loads saved tasks from the
	 * database, and places every panel into the BorderLayout.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().setBackground(Theme.BG_PANEL);
		frame.setBounds(10, 100, 1300, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(1600, 800));
		// Options panel 
		// + New Task, edit, delete buttons - Search bar/n
		// All statuses dropDown menu\n All priorities dropDown menu\n Sort by: dropDown menu
		// Export CSV
		
		UpperOptionsPanel northPanel = new UpperOptionsPanel();
		
		// Here we set up new tasks: Title Description, priority, status, due date, tags
		TaskFormPanelWest westPanel = new TaskFormPanelWest();
		// Adding the components to its parent panel : taskFormPanelWest
		
		// Task table, here we display different columns: TITLE,  PRIORITY, DUE DATE, STATUS , TAGS
		ViewTasksCenterPanel centerPanel = new ViewTasksCenterPanel();
	
		// Load tasks from DB into ArrayList and table
		ArrayList<Task> savedTasks = TaskDBExecute.getAllTasks();
		for (Task t : savedTasks) {
		    TaskController.tasks.add(t);  // load into ArrayList
		    centerPanel.addTaskToTable(t);     // load into table
		}
		
		// Reset counter to match the number of loaded tasks so new tasks start with the correct ID
		Task.counter = savedTasks.size();
		OverviewBottomPanel bottomPanel = new OverviewBottomPanel();
		
		panels.add(northPanel);panels.add(westPanel);panels.add(centerPanel);panels.add(bottomPanel);
		// Upper panel options to call the necessary methods and classes
		upperOptionsPanelListeners(panels);
		
		// There should be a task selected, so I need to check how and what is the state of the table row when is selected, since I wanted to use a checkbox for this.
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		frame.getContentPane().add(westPanel, BorderLayout.WEST);
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);	
	}
	
	/**
	 * Connects the buttons, search bar, and filters from the upper panel to
	 * their actions in the task form (westPanel), table (center Panel), bottom overview (bottom Panel), and database (SQLITE database manager, TaskDBExecute).
	 *
	 * @param panels panels created by initialize(), ordered as upper, west,
	 *               center, and bottom
	 */
	private void upperOptionsPanelListeners(ArrayList<JPanel> panels) {
		
		UpperOptionsPanel upper = (UpperOptionsPanel) panels.get(0);
		TaskFormPanelWest west = (TaskFormPanelWest) panels.get(1);
		ViewTasksCenterPanel center = (ViewTasksCenterPanel) panels.get(2);
		OverviewBottomPanel bottom = (OverviewBottomPanel) panels.get(3);
		
		
		// Create csv with the information in the table
		upper.getCreateCSV().addActionListener(event -> {
			CSV.exportToCSV(center.getTasksTrackerTableModel(), "todolist.csv");
		});
		// Adding a task to the column table once the user has input all the values in the west task form panel
		upper.getCreateTask().addActionListener(event -> {
			if(TaskController.createTaskController(west) != null) {
				center.addTaskToTable(TaskController.getTask());
				TaskDBExecute.insertTask(TaskController.getTask()); // Insert task to the database (here is the change)
			}
			bottom.refreshStatuses(); // Update the down panel statuses
			TaskDBExecute.updateAllTasks(); // Update all tasks from the tasks in the database changed.
		});
		
		
		upper.getEditTask().addActionListener(event -> {
			Object[] taskAndIndexRow = center.getSelectedTaskFromTable();   
			// Get selected task and indexrow
			
			if(taskAndIndexRow != null) {							// Verify if task is not null and was selected properly,
				Task t = (Task) taskAndIndexRow[0]; int selectedRow = (int) taskAndIndexRow[1];
				if(t != null) {
					TaskController.setTask(t); 			// Set the taskcontroller task to be this selected one.
					west.displayDataToEdit(t);			// Display the info from the task directly in the west panel so the user can edit.
					upper.displaySaveEditButton();      // Display the save button for the user to be able to save the changes
					upper.blockOtherButtons(true);     // Block other buttons
			        for (ActionListener listener : upper.getSaveButton().getActionListeners()) { 
						// For actionlisteners not to stack up
			            upper.getSaveButton().removeActionListener(listener);
			        }
					upper.getSaveButton().addActionListener(event2 -> { // Once the user clicks on save button.
						center.editTaskFromTable(west, selectedRow); // Edits the task from the table and from the array of tasks
						center.refreshTable(); // refreshes the ids for the changes made
						west.cleanDataEdited(); // cleans the west  task form panel
						upper.blockOtherButtons(false); // unblock the buttons
						upper.getSaveButton().setVisible(false);
						
	                    bottom.refreshStatuses();
	                    TaskDBExecute.updateTask(TaskController.getTask());
	                    TaskDBExecute.updateAllTasks();
						// We update the the information from the table by the task id that couldnt be changed in the process of editing a task
					});
				}
			}
		});
		
		upper.getDeleteTask().addActionListener(event -> {
            // Removing the task from the database happens inside this method
			center.removeTaskFromTable();
            // We refresh the information since TaskController.tasks changed. inside the removeTaskFromTable method.
			center.refreshTable();
			bottom.refreshStatuses();
			TaskDBExecute.updateAllTasks();
		});
		
		upper.getSearchBar().getDocument().addDocumentListener(new DocumentListener() {
			private void updateSearchResults() {
				String query = upper.getSearchBar().getText();
				if(query == null || query.trim().isEmpty()) {
					center.refreshTable(TaskController.tasks);
				} else {
					center.refreshTable(center.searchTasks(query));
				}
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateSearchResults();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateSearchResults();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateSearchResults();
			}
		});
		
		upper.getStatusFilter().addItemListener(item -> {
			if(item.getStateChange() == ItemEvent.SELECTED) {
				if(item.getItem() == Status.ALL) {
					//System.out.println("default");
					center.refreshTable(TaskController.tasks); // uses the whole tasks array
				}else{
					Status s = (Status) item.getItem();
					//System.out.println(item.getItem());
					center.refreshTable(TaskController.getTasksByStatus(s));
				}
			}
		});
		
		upper.getPriorityFilter().addItemListener(item -> {
			if(item.getStateChange() == ItemEvent.SELECTED) {
				if(item.getItem() == Priority.ALL) {
					//System.out.println("default");
					center.refreshTable(TaskController.tasks);
				}else {
					Priority p = (Priority) item.getItem();
					//System.out.println(item.getItem());
					center.refreshTable(TaskController.getTasksByPriority(p));
				}
				
			}
		});

		upper.getStatusSort().addItemListener(item -> {
			if(item.getStateChange() == ItemEvent.SELECTED){
				System.out.println(upper.getStatusSort().getSelectedItem());
				if(upper.getStatusSort().getSelectedIndex() == 1) {center.sortVisibleTableByStatusAscending();}
				else if(upper.getStatusSort().getSelectedIndex() == 2) {center.sortVisibleTableByStatusDescending();}
				else if (upper.getStatusSort().getSelectedItem() == "ALL") {
					center.refreshTable(TaskController.tasks);
				}
			}
		});

		upper.getPrioritySort().addItemListener(item -> {
			if(item.getStateChange() == ItemEvent.SELECTED) {
				System.out.println(upper.getPrioritySort().getSelectedItem());
				if(upper.getPrioritySort().getSelectedIndex() == 1){center.sortVisibleTableByPriorityAscending();}
				else if(upper.getPrioritySort().getSelectedIndex() == 2){center.sortVisibleTableByPriorityDescending();}
				else if (upper.getPrioritySort().getSelectedItem() == "ALL") { center.refreshTable(TaskController.tasks); }
			}
		});

		upper.getStatisticsButton().addActionListener(event -> {
			JOptionPane.showMessageDialog(frame, bottom.getStatisticsSummary(), "Statistics", JOptionPane.INFORMATION_MESSAGE);
		});
	}

}
