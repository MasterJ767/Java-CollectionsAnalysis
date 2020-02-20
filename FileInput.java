import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.nio.file.*;

class FileInput {
    private static ArrayList<Entry> readCSV(File location) throws IOException {
        // Convert CSV entries into records
        BufferedReader inFile = new BufferedReader(new FileReader(location));
        String row;
        ArrayList<Entry> records = new ArrayList<>();
        while ((row = inFile.readLine()) != null) {
            // Split lines from the file about the ',' character and place the line parts in a String Array
            String[] data = row.split(",");
            // Throw error if record does not split into three parts
            if (data.length != 3) {
                // Tell the user which record is causing the issue and the location of the file causing the issue
                throw new IllegalFormatException(String.format("The record %s is incorrectly formatted. It should take the form: 'surname,initials,extension' where the extension is 5 characters long and only contain numeric characters. See '%s' to rectify this issue.", row, location.getName()));
            }
            Entry entry = new Entry(data[0], data[1], data[2]);
            // Add the Entry to an ArrayList
            records.add(entry);
        }
        inFile.close();
        // Return the ArrayList
        return records;
    }

    static ArrayList<Entry> convertRecords(String[] args) {
        // Convert command line entries into records
        ArrayList<Entry> records = new ArrayList<>();
        for (String element: args) {
            String[] data = element.split(",");
            // Throw error if record does not split into three parts
            if (data.length != 3) {
                // Tell the user which record is causing the issue and that the issue is at the command line
                throw new IllegalFormatException(String.format("The record %s is incorrectly formatted. It should take the form: 'surname,initials,extension' where the extension is 5 characters long and only contains numeric characters. See the command line to rectify this issue.", element));
            }
            Entry entry = new Entry(data[0], data[1], data[2]);
            // Add the Entry to an ArrayList
            records.add(entry);
        }
        // Return the ArrayList
        return records;
    }

    static ArrayList<Entry> userInput() throws IOException{
        /* Initialise file choosing objects, in case it is necessary to use them later
        JFileChooser defaults to current working directory and filters files for csv files*/
        JFileChooser chooser = new JFileChooser(Paths.get("").toAbsolutePath().toString());
        chooser.setFileFilter(new FileNameExtensionFilter("COMMA SEPARATED VALUES FILE (*.csv)", ".csv", "csv"));
        File file = null;
        // Ask for user to input a file location
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the pathname of the file which you would like records to be read from:\n> ");
        String pathname = input.nextLine();
        // Bring up JFileChooser if user input file doesnt exist
        try {
            // Read in records from file location and convert them to entries
            return readCSV(new File(pathname));
        } catch (IOException ioe) {
            System.out.println("The filename you entered was not a *.csv file, please use the file chooser to select the correct file.");
            // Switch to default file if user presses cancel in the JFileChooser window
            try {
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                }
                // Read in records from file location and convert them to entries
                return readCSV(file);
            } catch (NullPointerException|IOException e) {
                System.out.println("You failed to select a valid file with JFileChooser, the program will default to using test_data.csv file.");
                // Read in records from file location and convert them to entries
                return readCSV(new File("test_data.csv"));
            }
        }
    }
}
