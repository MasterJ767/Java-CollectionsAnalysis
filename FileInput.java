import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class FileInput {
    private static ArrayList<String[]> readCSV(File location) throws IOException {
        // Convert CSV entries into records
        BufferedReader inFile = new BufferedReader(new FileReader(location));
        String row;
        ArrayList<String[]> records = new ArrayList<>();
        while ((row = inFile.readLine()) != null) {
            // Split lines from the file about the ',' character and place the line parts in a String Array
            String[] data = row.split(",");
            // Throw error if record does not split into three parts
            if (data.length != 3) {
                // Tell the user which record is causing the issue and the location of the file causing the issue
                throw new IllegalFormatException(String.format("The record %s is incorrectly formatted. It should take the form: 'surname,initials,extension' where the extension is 5 characters long. See '%s' to rectify this issue.", row, location.getName()));
            }
            // Throw error if extension is not 5 characters long
            if (data[2].length() != 5) {
                // Tell the user which record is causing the issue and the location of the file causing the issue
                throw new IllegalExtensionException(String.format("The extension %1$s is of length %2$d. Extensions should be of length 5, see entry '%3$s,%4$s,%1$s' in '%5$s' to rectify this issue.", data[2], data[2].length(), data[0], data[1], location.getName()));
            }
            // Add the String Array to an ArrayList
            records.add(data);
        }
        inFile.close();
        // Return the ArrayList
        return records;
    }

    static ArrayList<String[]> convertRecords(String[] args) {
        // Convert command line entries into records
        ArrayList<String[]> records = new ArrayList<>();
        for (String element: args) {
            String[] data = element.split(",");
            // Throw error if record does not split into three parts
            if (data.length != 3) {
                // Tell the user which record is causing the issue and that the issue is at the command line
                throw new IllegalFormatException(String.format("The record %s is incorrectly formatted. It should take the form: 'surname,initials,extension' where the extension is 5 characters long. See the command line to rectify this issue.", element));
            }
            // Throw error if extension is not 5 characters long
            if (data[2].length() != 5) {
                // Tell the user which record is causing the issue and that the issue is at the command line
                throw new IllegalExtensionException(String.format("The extension %1$s is of length %2$d. Extensions should be of length 5, see entry '%3$s,%4$s,%1$s' from command line to rectify this issue.", data[2], data[2].length(), data[0], data[1]));
            }
            // Add the String Array to an ArrayList
            records.add(data);
        }
        // Return the ArrayList
        return records;
    }

    static ArrayList<String[]> userInput() throws IOException {
        // Initialise file choosing objects, in case it is necessary to use them later
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("COMMA SEPARATED VALUES FILE (*.csv)", ".csv", "csv"));
        File file = null;
        // Ask for user to input a file location
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the pathname of the file which you would like records to be read from:\n> ");
        String pathname = input.nextLine();
        // Bring up JFileChooser if pathname does not end with ".csv" it is nto a CSV file, so make user select file with JFileChooser
        if (pathname.endsWith(".csv")) {
            // Add records from file location to records from the command line configuration
            return readCSV(new File(pathname));
        } else {
            System.out.println("The filename you entered was not a *.csv file, please use the file chooser to select the correct file.\n");
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
            }
            // Add records from file location to records from the command line configuration
            return readCSV(file);
        }
    }

    private static ArrayList<String[]> readCSVDebug(File location) throws IOException {
        // Open CSV file and read in contents
        BufferedReader inFile = new BufferedReader(new FileReader(location));
        String row;
        ArrayList<String[]> records = new ArrayList<>();
        while ((row = inFile.readLine()) != null) {
            String[] data = row.split(",");
            records.add(data);
            // Throw error if record does not split into three parts
            if (data.length != 3) {
                // Tell the user which record is causing the issue and the location of the file causing the issue
                throw new IllegalFormatException(String.format("The record %s is incorrectly formatted. It should take the form: 'surname,initials,extension' where the extension is 5 characters long. See '%s' to rectify this issue.", row, location.getName()));
            }
            // Throw error if extension is not 5 characters long
            if (data[2].length() != 5) {
                // Tell the user which record is causing the issue and the location of the file causing the issue
                throw new IllegalExtensionException(String.format("The extension %1$s is of length %2$d. Extensions should be of length 5, see entry '%3$s,%4$s,%1$s' in '%5$s' to rectify this issue.", data[2], data[2].length(), data[0], data[1], location.getName()));
            }
        }
        inFile.close();
        // Return the ArrayList
        return records;
    }
}
