import java.io.*;
import java.util.*;

public class Launcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Convert records from the command line configuration into a format ready to be inserted into the directory
        List<Entry> dirtyData = FileInput.convertRecords(args);
        // Collect records from an input csv file and add to command line configuration records
        dirtyData.addAll(FileInput.userInput());

        // Remove duplicate Entry Objects
        List<Entry> cleanData = removeDuplicates(dirtyData);

        // Asks the user which Directory implementation they would like to use
        System.out.println("\nHow would you like to store your data?");
        String[] choices = {"ArrayDirectory", "ArrayListDirectory", "HashMapDirectory"};
        int directoryVariant = FileInput.listPrint(Arrays.asList(choices));

        // Put records into the user's chosen directory type
        Directory directory = createDirectory(cleanData,directoryVariant);

        // Print contents of directory as a table to screen
        System.out.println(directory.toString());

        // Write records to an output csv file
        FileOutput.userOutput(directory.toArrayList());

        String[] performanceResults = new String[7];

        // Performance testing
        String titleResults = "\nPerformance Tests:\n";
        performanceResults[0] = titleResults;
        System.out.println(titleResults);

        // Create stopwatch object
        StopWatch timer = new StopWatch();
        // Set number of tests which will be performed
        int testNumber = 10000;
        // Initialise a Directory of each type containing all of the data
        Directory testArrayDirectory = createDirectory(cleanData,1);
        Directory testArrayListDirectory = createDirectory(cleanData,2);
        Directory testHashMapDirectory = createDirectory(cleanData, 3);
        Directory[] testDirectoryArray = {testArrayDirectory, testArrayListDirectory, testHashMapDirectory};

        // Obtain test entry form the middle of the cleaned data ready for lookup tests
        int index = cleanData.size() / 2;
        Entry testLookupEntry = cleanData.get(index);
        // Create test entry ready for insertion tests
        Entry testInsertionEntry = new Entry("Smith","A.B","01234");

        // Run InsertEntry tests
        String insertEntryResults = Analysis.testInsertionEntryMethod(testDirectoryArray, choices, testNumber, timer, testInsertionEntry);
        performanceResults[1] = insertEntryResults;
        System.out.println(insertEntryResults);

        // Run DeleteEntryByName tests
        String deletionEntryByNameResults = Analysis.testDeletionEntryByNameMethod(testDirectoryArray, choices, testNumber, timer, cleanData);
        performanceResults[2] = deletionEntryByNameResults;
        System.out.println(deletionEntryByNameResults);

        // Run DeleteEntryByExtension tests
        String deletionEntryByExtensionResults = Analysis.testDeletionEntryByExtensionMethod(testDirectoryArray, choices, testNumber, timer, cleanData);
        performanceResults[3] = deletionEntryByExtensionResults;
        System.out.println(deletionEntryByExtensionResults);

        // Run UpdateEntryExtension tests
        String updateEntryExtensionResults = Analysis.testUpdateEntryExtensionMethod(testDirectoryArray, choices, testNumber, timer, testInsertionEntry);
        performanceResults[4] = updateEntryExtensionResults;
        System.out.println(updateEntryExtensionResults);

        // Run LookupExtension tests
        String lookupExtensionResults = Analysis.testLookupExtensionMethod(testDirectoryArray, choices, testNumber, timer, testLookupEntry);
        performanceResults[5] = lookupExtensionResults;
        System.out.println(lookupExtensionResults);

        // Run ToArrayList tests
        String toArrayListResults = Analysis.testToArrayListMethod(testDirectoryArray, choices, testNumber, timer);
        performanceResults[6] = toArrayListResults;
        System.out.println(toArrayListResults);

        // Write performance results to an output txt file
        FileOutput.performanceOutput(performanceResults);
    }

    static Directory createDirectory(List<Entry> data, int variant) {
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

    private static List<Entry> removeDuplicates(List<Entry> dirtyData) {
        // Remove duplicate entries from a list and return the list duplicate free
        List<Entry> cleanData = new ArrayList<>();
        for (Entry entry: dirtyData) {
            if (!(cleanData.contains(entry))) {
                cleanData.add(entry);
            }
        }
        return cleanData;
    }
}
