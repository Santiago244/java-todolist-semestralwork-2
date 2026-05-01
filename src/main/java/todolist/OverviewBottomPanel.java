package todolist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class OverviewBottomPanel extends JPanel{
			// Color icon -- NUMBER - total, Color icon -- NUMBER -- done ... in progress ... overdue
            public HashMap<JLabel, JLabel> countersAndFields = new HashMap<JLabel, JLabel>();
			private JLabel numTotal = new JLabel(String.valueOf(TaskController.tasks.size()));
			private JLabel tasksTotal = new JLabel("Total");
			private JLabel numTodo = new JLabel(String.valueOf(TaskController.getTasksByStatus(Status.TODO).size()));
			private JLabel tasksTodo = new JLabel("Todo");
			private JLabel numDone = new JLabel(String.valueOf(TaskController.getTasksByStatus(Status.DONE).size()));
			private JLabel tasksDone = new JLabel("Done");
			private JLabel numInProgress = new JLabel(String.valueOf(TaskController.getTasksByStatus(Status.INPROGRESS).size()));
			private JLabel tasksInProgress = new JLabel("In progress");
			private JLabel numOverDue = new JLabel(String.valueOf(TaskController.getTasksByStatus(Status.OVERDUE).size()));
			private JLabel tasksOverdue = new JLabel("Overdue");
			
			/**
			 * Creates the bottom overview panel with counters for all task statuses.
			 */
			public OverviewBottomPanel() {
				this.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 5)); // Horizontal and vertical gap);
				// this.setBackground(Theme.BG_PRIMARY);
				this.setPreferredSize(new Dimension(0, 30)); // in north/south I only control the height. 
				
				// Text fields to appear in the button in flowlayout row type with a gap between them
				countersAndFields.put(numTotal, tasksTotal);
                countersAndFields.put(numTodo, tasksTodo);
                countersAndFields.put(numDone, tasksDone);
                countersAndFields.put(numInProgress, tasksInProgress);
                countersAndFields.put(numOverDue, tasksOverdue);

                for(JLabel counter: countersAndFields.keySet()) {
					// System.out.println(TaskController.getTasksByStatus(Status.INPROGRESS));
                    this.add(counter,FlowLayout.LEFT);
                    this.add(countersAndFields.get(counter), FlowLayout.CENTER);
                }
				// numTotal.setForeground(Theme.TEXT_SECONDARY);tasksTotal.setForeground(Theme.TEXT_PRIMARY);numDone.setForeground(Theme.TEXT_SECONDARY);tasksDone.setForeground(Theme.TEXT_PRIMARY);numInProgress.setForeground(Theme.TEXT_SECONDARY);tasksInProgress.setForeground(Theme.TEXT_PRIMARY);numOverDue.setForeground(Theme.TEXT_SECONDARY);tasksOverdue.setForeground(Theme.TEXT_PRIMARY
			}

			/**
			 * Recalculates the task counters from TaskController.tasks.
			 */
			public void refreshStatuses() {
                // Set all the fields to be the values that are in the TasksController.tasks (source of truth)
                numTotal = new JLabel(String.valueOf(TaskController.tasks.size()));
                numTodo = new JLabel(String.valueOf(TaskController.getTasksByStatus(Status.TODO).size()));
                numDone = new JLabel(String.valueOf(TaskController.getTasksByStatus(Status.DONE).size()));
                numInProgress = new JLabel(String.valueOf(TaskController.getTasksByStatus(Status.INPROGRESS).size()));
                numOverDue = new JLabel(String.valueOf(TaskController.getTasksByStatus(Status.OVERDUE).size()));

                // Update the hashmap with the new labels
                countersAndFields.put(numTotal, tasksTotal);
                countersAndFields.put(numTodo, tasksTodo);
                countersAndFields.put(numDone, tasksDone);
                countersAndFields.put(numInProgress, tasksInProgress);
                countersAndFields.put(numOverDue, tasksOverdue);
            }

			/**
			 * Builds a short readable summary of the bottom panel numbers.
			 *
			 * @return text that can be shown in a dialog
			 */
			public String getStatisticsSummary() {
				int total = TaskController.tasks.size();
				int todo = TaskController.getTasksByStatus(Status.TODO).size();
				int done = TaskController.getTasksByStatus(Status.DONE).size();
				int inProgress = TaskController.getTasksByStatus(Status.INPROGRESS).size();
				int overdue = TaskController.getTasksByStatus(Status.OVERDUE).size();

				StringBuilder summary = new StringBuilder();
				summary.append("Task Statistics\n\n");
				summary.append("Total tasks: ").append(total).append("\n");
				summary.append("Todo: ").append(todo).append("\n");
				summary.append("In progress: ").append(inProgress).append("\n");
				summary.append("Done: ").append(done).append("\n");
				summary.append("Overdue: ").append(overdue).append("\n");

				if(total > 0) {
					summary.append("\nIn progress percentage: ").append((inProgress * 100) / total).append("%");
					summary.append("\nDone percentage: ").append((done * 100) / total).append("%");
					summary.append("\nOverdue percentage: ").append((overdue * 100) / total).append("%");
					
				}

				return summary.toString();
			}
}
