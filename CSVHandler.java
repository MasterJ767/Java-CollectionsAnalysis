import java.io.*;
import java.util.*;

public class CSVHandler {
    public Boolean fileReadable(String location){
        // If the input file exists and the user has permission to read from it, return true
        File file = new File(location);
        return (file.exists() && file.canRead());
    }

    public boolean fileWritable(String location){
        // If the input file exists and the user has permission to write to it, return true
        File file = new File(location);
        return (file.exists()  && file.canWrite());
    }

    public String[] readCSV(String location) throws IOException{
        Scanner inFile = new Scanner(new FileReader(location));
        String[] data = inFile.nextLine().split(",");
        inFile.close();
        return data;
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

/*
    Directory newDirectory;
    String row;
        if (type == 0) {
                newDirectory = new ArrayDirectory();
                } else if (type == 1) {
                newDirectory = new ArrayListDirectory();
                } else {
                newDirectory = new HashMapDirectory();
                }

                BufferedReader csvReader = new BufferedReader(new FileReader(location));
                while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                Entry newEntry = new Entry(data[0], data[1], data[2]);
                if (type == 0) {
                newDirectory.insertEntry(newEntry);
                } else if (type == 1) {
                newDirectory.insertEntry(newEntry);
                } else {
                newDirectory.insertEntry(newEntry);
                }
                }
                csvReader.close();
                return newDirectory;*/
