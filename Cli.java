import java.io.*;
import java.util.*;

public class Cli {
    public static void main(String[] args) throws IOException {
        // Convert records from the command line configuration into a format ready to be inserted into the directory
        ArrayList<Entry> data = FileInput.convertRecords(args);
        // Collect records from an input csv file and add to command line configuration records
        data.addAll(FileInput.userInput());

        // Put records into An ArrayListDirectory
        Directory directory = createDirectory(data,1);

        System.out.println(directory.toString());

        FileOutput.userOutput(directory.toArrayList());
    }

    private static Directory createDirectory(ArrayList<Entry> dirtyData, int variant) {
        // Initialise a Directory and an Array of entries
        Directory newDirectory;

        // Remove duplicate Entry Objects
        ArrayList<Entry> cleanData = removeDuplicates(dirtyData);

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

    private static ArrayList<Entry> removeDuplicates(ArrayList<Entry> dirtyData) {
        ArrayList<Entry> cleanData = new ArrayList<>();
        for (Entry entry: dirtyData) {
            if (!(cleanData.contains(entry))) {
                cleanData.add(entry);
            }
        }
        return cleanData;
    }
}
