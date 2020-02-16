import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class FileOutput {
    private static void writeCSV(File location, List<Entry> records) throws IOException{
        // Open CSV file, convert entries to strings and write them to the CSV file
        PrintWriter outFile = new PrintWriter(location);
        for (Entry record: records) {
            outFile.println(record.toString());
        }
        outFile.close();
    }

    static void userOutput(List<Entry> records) throws IOException{
        // Initialise file choosing objects, in case it is necessary to use them later
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("COMMA SEPARATED VALUES FILE (*.csv)", ".csv", "csv"));
        File file = null;
        // Ask for user to input a file location
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the pathname of the file which you would like records to be written to:\n> ");
        String pathname = input.nextLine();
        // Bring up JFileChooser if pathname does not end with ".csv" it is nto a CSV file, so make user select file with JFileChooser
        if (pathname.endsWith(".csv")) {
            // Add records from file location to records from the command line configuration
            writeCSV(new File(pathname), records);
        } else {
            System.out.println("The filename you entered was not a *.csv file, please use the file chooser to select the correct file.\n");
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
            }
            // Add records from file location to records from the command line configuration
            writeCSV(file, records);
        }
    }
}
