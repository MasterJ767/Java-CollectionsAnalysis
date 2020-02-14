import java.io.IOException;
import java.util.*;

public class Cli {
    public static void main(String[] args) throws IOException {
        // ArrayList<String> directory = FileInput.readCSVDebug("test_data.csv");

                /*ArrayList<String[]> directory = FileInput.readCSV("test_data.csv");
                Entry[] records = new Entry[directory.size()];
                // Convert String Arrays to Entry Objects
                for (int i = 0; i < directory.size(); i++) {
                    Entry entry = new Entry(directory.get(i)[0], directory.get(i)[1], directory.get(i)[2]);
                    records[i] = entry;
                }
                // Remove duplicate Entry Objects
                Object[] cleanData = removeDuplicates(records);
                String[] printable = new String[cleanData.length];
                for (int i = 0; i < cleanData.length; i++) {
                    printable[i] = cleanData[i].toString();
                }
                Arrays.sort(printable);
                for (Object item: printable) {
                    System.out.println(item.toString());
                }
                System.out.println(String.format("%d, %d", cleanData.length, records.length));*/

        /*Directory directory = createDirectory(FileInput.readCSV("test_data.csv"), 2);
        System.out.println(directory.toString());
        FileOutput.writeCSV("output_data.csv", directory.toArrayList());*/
    }

    public static Directory createDirectory(ArrayList<String[]> data, int variant) {
        // Initialise a Directory and an Array of entries
        Directory newDirectory;
        Entry[] records = new Entry[data.size()];
        // Convert String Arrays to Entry Objects
        for (int i = 0; i < data.size(); i++) {
            Entry entry = new Entry(data.get(i)[0], data.get(i)[1], data.get(i)[2]);
            records[i] = entry;
        }

        // Remove duplicate Entry Objects
        Entry[] cleanData = (Entry[]) removeDuplicates(records);

        // Depending on code passed to the function, instantiate a Directory subclass object
        if (variant == 0) {
            newDirectory = new ArrayDirectory();
        } else if (variant == 1) {
            newDirectory = new ArrayListDirectory();
        } else {
            newDirectory = new HashMapDirectory();
        }

        // Fill directory with Entry Objects
        for (Entry newEntry : cleanData) {
            newDirectory.insertEntry(newEntry);
        }

        // Return Directory Object
        return newDirectory;
    }

    private static Object[] removeDuplicates(Entry[] data) {
        ArrayList<Entry> cleanData = new ArrayList<>();
        for (Entry entry: data) {
            if (!(cleanData.contains(entry))) {
                cleanData.add(entry);
            }
        }

        return cleanData.toArray();
    }
}
