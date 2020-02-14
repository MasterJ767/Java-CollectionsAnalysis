import java.io.*;
import java.util.*;
import javax.swing.*;

public class Cli {
    public static void main(String[] args) throws IOException {
        Entry newEntry = new Entry("Prott", "A.B", "12345");
        ArrayList<String[]> data = FileInput.readCSV("test.csv");
        Directory directory2 = createDirectory(data, 2);
        System.out.println(directory2.toString());
        directory2.insertEntry(newEntry);
        System.out.println(directory2.toString());
        /*directory2.deleteEntryUsingName("Prott");
        System.out.println(directory2.toString());*/
        Directory directory1 = createDirectory(data, 1);
        System.out.println(directory1.toString());
        directory1.insertEntry(newEntry);
        System.out.println(directory1.toString());
        /*directory1.deleteEntryUsingName("Prott");
        System.out.println(directory1.toString());*/
        Directory directory = createDirectory(data, 0);
        System.out.println(directory.toString());
        directory.insertEntry(newEntry);
        System.out.println(directory.toString());
        /*directory.deleteEntryUsingName("Prott");
        System.out.println(directory.toString());*/


        //FileOutput.writeCSV("output_data.csv", directory.toArrayList());
    }

    private static Directory createDirectory(ArrayList<String[]> data, int variant) {
        // Initialise a Directory and an Array of entries
        Directory newDirectory;
        Entry[] records = new Entry[data.size()];
        // Convert String Arrays to Entry Objects
        for (int i = 0; i < data.size(); i++) {
            Entry entry = new Entry(data.get(i)[0], data.get(i)[1], data.get(i)[2]);
            records[i] = entry;
        }

        // Remove duplicate Entry Objects
        Entry[] cleanData = removeDuplicates(records);

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

    private static Entry[] removeDuplicates(Entry[] data) {
        ArrayList<Entry> cleanData = new ArrayList<>();
        for (Entry entry: data) {
            if (!(cleanData.contains(entry))) {
                cleanData.add(entry);
            }
        }
        Entry[] cleanDataArray = new Entry[cleanData.size()];
        for (int i = 0; i < cleanData.size(); i++) {
            cleanDataArray[i] = cleanData.get(i);
        }

        return cleanDataArray;
    }

    private static FileReader chooseFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileReader in;
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            in = new FileReader(selectedFile);
        } else {
            in = null;
        }
        return in;
    }
}
