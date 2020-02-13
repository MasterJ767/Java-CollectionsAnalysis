import java.io.*;
import java.util.*;

public class FileOutput {
    public boolean fileWritable(String location){
        // If the input file exists and the user has permission to write to it, return true
        File file = new File(location);
        return (file.exists()  && file.canWrite());
    }

    public void writeCSV(String location, ArrayList<Entry> records) throws IOException{
        PrintWriter outFile = new PrintWriter(location);
        String[] data = new String[records.size()];
        for (int i = 0; i < records.size(); i++) {
            data[i] = records.get(i).toString();
        }
        for (String record: data) {
            outFile.println(record);
        }
        outFile.close();
    }
}
