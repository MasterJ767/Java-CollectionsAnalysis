import java.io.IOException;
import java.util.ArrayList;

public class Cli {
    public static void main(String[] args) throws IOException {
        Directory directory = createDirectory(FileInput.readCSV("test_data.csv"), 1);
        System.out.println(directory.toString());
    }

    public static Directory createDirectory(ArrayList<String[]> data, int variant) {
        Directory newDirectory;
        Entry[] records = new Entry[data.size()];
        for (int i = 0; i < data.size(); i++) {
            Entry entry = new Entry(data.get(i)[0], data.get(i)[1], data.get(i)[2]);
            records[i] = entry;
        }
        if (variant == 0) {
            newDirectory = new ArrayDirectory();
        } else if (variant == 1) {
            newDirectory = new ArrayListDirectory();
        } else {
            newDirectory = new HashMapDirectory();
        }
        for (Entry newEntry : records) {
            newDirectory.insertEntry(newEntry);
        }
        return newDirectory;
    }
}
