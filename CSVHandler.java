import java.io.*;
import java.util.ArrayList;

public class CSVHandler {
    public Directory input(String location, int type) throws IOException{
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
        return newDirectory;
    }

    public void output(String location, ArrayList<Entry> records) {
        
    }
}
