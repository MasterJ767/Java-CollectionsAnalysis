import java.io.*;
import java.util.*;

public class FileInput {
    public Boolean fileReadable(String location) {
        // If the input file exists and the user has permission to read from it, return true
        File file = new File(location);
        return (file.exists() && file.canRead());
    }

    public static ArrayList<String[]> readCSV(String location) throws IOException {
        // Open CSV file and read in contents
        BufferedReader inFile = new BufferedReader(new FileReader(location));
        String row;
        ArrayList<String[]> records = new ArrayList<>();
        while ((row = inFile.readLine()) != null) {
            // Split lines from the file about the ',' character and place the line parts in a String Array
            String[] data = row.split(",");
            // Throw error if extension is not 5 characters long
            if (data[2].length() != 5) {
                // Tell the user which record is causing the issue and the location of the file causing the issue
                throw new IllegalExtensionException(String.format("The extension %1$s is of length %2$d. Extensions should be of length 5, see entry '%3$s,%4$s,%1$s' in '%5$s' to rectify this issue.", data[2], data[2].length(), data[0], data[1], location));
            }
            // Add the String Array to an ArrayList
            records.add(data);
        }
        inFile.close();
        // Return the ArrayList
        return records;
    }

    static ArrayList<String> readCSVDebug(String location) throws IOException {
        // Open CSV file and read in contents
        BufferedReader inFile = new BufferedReader(new FileReader(location));
        String row;
        ArrayList<String> records = new ArrayList<>();
        while ((row = inFile.readLine()) != null) {
            records.add(row);
        }
        inFile.close();
        Collections.sort(records);
        // Return the ArrayList
        return records;
    }
}
