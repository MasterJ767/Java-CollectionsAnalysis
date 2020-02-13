import java.io.*;
import java.util.*;

public class FileInput {
    public Boolean fileReadable(String location){
        // If the input file exists and the user has permission to read from it, return true
        File file = new File(location);
        return (file.exists() && file.canRead());
    }

    public static ArrayList<String[]> readCSV(String location) throws IOException{
        // Open CSV file and read in contents
        BufferedReader inFile = new BufferedReader(new FileReader(location));
        String row;
        ArrayList<String[]> records = new ArrayList<>();
        while ((row = inFile.readLine()) != null) {
            // Split lines from the file about the ',' character and place the line parts in a String Array
            String[] data = row.split(",");
            // Add the String Array to an ArrayList
            records.add(data);
        }
        inFile.close();
        // Return the ArrayList
        return records;
    }
}
