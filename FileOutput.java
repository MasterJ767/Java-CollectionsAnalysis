import java.io.*;
import java.util.*;

public class FileOutput {
    public boolean fileWritable(String location){
        // If the input file exists and the user has permission to write to it, return true
        File file = new File(location);
        return (file.exists()  && file.canWrite());
    }

    public static void writeCSV(String location, List<Entry> records) throws IOException{
        // Open CSV file, convert entries to strings and write them to the CSV file
        PrintWriter outFile = new PrintWriter(location);
        for (Entry record: records) {
            outFile.println(record.toString());
        }
        outFile.close();
    }
}
