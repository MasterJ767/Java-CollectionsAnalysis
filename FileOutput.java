import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.nio.file.*;

class FileOutput {
    private static void writeCSV(File location, List<Entry> records) throws IOException{
        // Open CSV file, convert entries to strings and write them to the CSV file
        PrintWriter outFile = new PrintWriter(location);
        for (Entry record: records) {
            outFile.println(record.toString());
        }
        outFile.close();
    }

    static void userOutput(List<Entry> records) throws IOException{
        /* Initialise file choosing objects, in case it is necessary to use them later
        JFileChooser defaults to current working directory and filters files for csv files*/
        JFileChooser chooser = new JFileChooser(Paths.get("").toAbsolutePath().toString());
        chooser.setFileFilter(new FileNameExtensionFilter("COMMA SEPARATED VALUES FILE (*.csv)", ".csv", "csv"));
        File file = null;
        // Ask for user to input a file location
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the pathname of the file which you would like records to be written to:\n> ");
        String pathname = input.nextLine();
        // Bring up JFileChooser if pathname does not end with ".csv" as it is not a CSV file, so make user select file with JFileChooser
        if (pathname.endsWith(".csv")) {
            // Convert entries to records and write into file location
            writeCSV(new File(pathname), records);
        } else {
            System.out.println("The filename you entered was not a *.csv file, please use the file chooser to select the correct file.");
            // Switch to default file if user presses cancel in the JFileChooser window
            try {
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                }
                // Convert entries to records and write into file location
                writeCSV(file, records);
            } catch (NullPointerException|IOException e) {
                System.out.println("You failed to select a valid file with JFileChooser, the program will default to using output_data.csv file.");
                // Convert entries to records and write into file location
                writeCSV(new File("output_data.csv"), records);
            }
        }
    }

    private static void writeTXT(File location, String[] information) throws IOException{
        // Open TEXT file, convert entries to strings and write them to the CSV file
        PrintWriter outFile = new PrintWriter(location);
        for (String line: information) {
            outFile.println(line);
        }
        outFile.close();
    }

    static void performanceOutput(String[] information) throws IOException {
        /* Initialise file choosing objects, in case it is necessary to use them later
        JFileChooser defaults to current working directory and filters files for csv files*/
        JFileChooser chooser = new JFileChooser(Paths.get("").toAbsolutePath().toString());
        chooser.setFileFilter(new FileNameExtensionFilter("TEXT FILE (*.txt, *.text)", ".txt", "txt", ".text", "text"));
        File file = null;
        // Ask for user to input a file location
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the pathname of the file which you would like performance results to be written to:\n> ");
        String pathname = input.nextLine();
        // Bring up JFileChooser if pathname does not end with ".txt" as it is not a TEXT file, so make user select file with JFileChooser
        if (pathname.endsWith(".txt") || pathname.endsWith(".text")) {
            // Convert entries to records and write into file location
            writeTXT(new File(pathname), information);
        } else {
            System.out.println("The filename you entered was not a *.txt file, please use the file chooser to select the correct file.");
            // Switch to default file if user presses cancel in the JFileChooser window
            try {
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                }
                // Convert entries to records and write into file location
                writeTXT(file, information);
            } catch (NullPointerException|IOException e) {
                System.out.println("You failed to select a valid file with JFileChooser, the program will default to using performance_report.txt file.");
                // Convert entries to records and write into file location
                writeTXT(new File("performance_report.txt"), information);
            }
        }
    }
}
