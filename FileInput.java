import java.io.*;
import java.util.*;

public class FileInput {
    public Boolean fileReadable(String location){
        // If the input file exists and the user has permission to read from it, return true
        File file = new File(location);
        return (file.exists() && file.canRead());
    }

    public static ArrayList<String[]> readCSV(String location) throws IOException{
        BufferedReader inFile = new BufferedReader(new FileReader(location));
        String row;
        ArrayList<String[]> records = new ArrayList<>();
        while ((row = inFile.readLine()) != null) {
            String[] data = row.split(",");
            records.add(data);
        }
        inFile.close();
        return records;
    }
}
