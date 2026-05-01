package todolist;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.table.DefaultTableModel;

public class CSV {

    /**
     * Exports the current table model to a CSV file.
     * Column names are written first, then every row value is escaped and quoted.
     *
     * @param model table model containing the data to export
     * @param filePath path/name of the CSV file to create
     */
    public static void exportToCSV(DefaultTableModel model, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            // Write header
            for (int i = 0; i < model.getColumnCount(); i++) {
                writer.print("\"" + model.getColumnName(i) + "\"");
                if (i < model.getColumnCount() - 1) writer.print(",");
            }
            writer.println();

            // Write rows
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    String cell = value != null ? value.toString() : "";
                    writer.print("\"" + cell.replace("\"", "\"\"") + "\""); 
                    if (col < model.getColumnCount() - 1) writer.print(",");
                }
                writer.println();
            }

            System.out.println("CSV exported to: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
