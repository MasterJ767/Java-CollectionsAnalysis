import java.io.*;
import java.util.*;

public class Cli {
    public static void main(String[] args) throws IOException {
        // Convert records from the command line configuration into a format ready to be inserted into the directory
        ArrayList<Entry> dirtyData = FileInput.convertRecords(args);
        // Collect records from an input csv file and add to command line configuration records
        dirtyData.addAll(FileInput.userInput());

        // Remove duplicate Entry Objects
        ArrayList<Entry> cleanData = removeDuplicates(dirtyData);

        // Asks the user which Directory implementation they would like to use
        System.out.println("\nHow would you like to store your data?");
        String[] choices = {"ArrayDirectory", "ArrayListDirectory", "HashMapDirectory"};
        int directoryVariant = listPrint(Arrays.asList(choices));

        // Put records into the user's chosen directory type
        Directory directory = createDirectory(cleanData,directoryVariant);

        // Print contents of directory as a table to screen
        System.out.println(directory.toString());

        // Write records to an output csv file
        FileOutput.userOutput(directory.toArrayList());

        // Performance testing
        StopWatch timer = new StopWatch();
        // Initialise a Directory of each type containing all of the data
        Directory testArrayDirectory = createDirectory(cleanData,1);
        Directory testArrayListDirectory = createDirectory(cleanData,2);
        Directory testHashMapDirectory = createDirectory(cleanData, 3);
        Directory[] testDirectoryArray = new Directory[3];
        testDirectoryArray[0] = testArrayDirectory;
        testDirectoryArray[1] = testArrayListDirectory;
        testDirectoryArray[2] = testHashMapDirectory;

        // Obtain test entry form the middle of the cleaned data ready for deletion tests
        int index = cleanData.size() / 2;
        Entry testDeletionEntry = cleanData.get(index);
        // Create test entry ready for insertion tests
        Entry testInsertionEntry = new Entry("Smith","A.B","01234");

        // Fill insertion time results array with 0s
        long[] testInsertionTime = new long[3];
        Arrays.fill(testInsertionTime, 0);

        // InsertEntry test
        for (int i = 0; i < testDirectoryArray.length; i++) {
            for (int j = 0; j < 10000; j++) {
                timer.start();
                testDirectoryArray[i].insertEntry(testInsertionEntry);
                timer.stop();
                testInsertionTime[i] += timer.getElapsedTime();
                timer.reset();
                testDirectoryArray[i].deleteEntryUsingName(testInsertionEntry.getSurname());
            }
            testInsertionTime[i] /= 10000;
        }

        System.out.println(String.format("\nAvergae Insertion Times:\n\nArrayDirectory = %d ns\nArrayListDirectory = %d ns\nHashMapDirectory = %d ns", testInsertionTime[0], testInsertionTime[1], testInsertionTime[2]));

    }

    private static Directory createDirectory(ArrayList<Entry> data, int variant) {
        // Initialise a Directory and an Array of entries
        Directory newDirectory;

        // Depending on code passed to the function, instantiate a Directory subclass object
        if (variant == 1) {
            newDirectory = new ArrayDirectory();
        } else if (variant == 2) {
            newDirectory = new ArrayListDirectory();
        } else {
            newDirectory = new HashMapDirectory();
        }

        // Fill directory with Entry Objects
        for (Entry newEntry : data) {
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

    private static int listPrint(List<String> options) {
        ListIterator<String> iter = options.listIterator();
        while (iter.hasNext()) {
            System.out.println((iter.nextIndex()+1) + ". " + iter.next());
        }
        int choice;
        Scanner input = new Scanner(System.in);
        System.out.print("> ");
        while (true) {
            try {
                choice = input.nextInt();
                if ((choice < 1) || (choice > options.size())) {
                    throw new IndexOutOfBoundsException();
                }
            } catch (InputMismatchException|ClassCastException|IndexOutOfBoundsException e) {
                System.out.print("Please enter the number which corresponds to your choice.\n> ");
                input.next();
                continue;
            }
            break;
        }
        return choice;
    }

}
