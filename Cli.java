import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Cli {
    public static void main(String[] args) throws IOException, InterruptedException {
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
        // Set number of tests which will be performed
        int testNumber = 10000;
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
        long[] testInsertionTime = new long[9];
        Arrays.fill(testInsertionTime, 0);

        // Create progress bar
        ProgressBar prog = new ProgressBar(testNumber*testDirectoryArray.length, "Progress", 100);

        // InsertEntry test
        for (int i = 0; i < testDirectoryArray.length; i++) {
            for (int j = 0; j < testNumber; j++) {
                timer.start();
                testDirectoryArray[i].insertEntry(testInsertionEntry);
                timer.stop();
                testInsertionTime[(i*3)+1] += timer.getElapsedTime();
                if (timer.getElapsedTime() < testInsertionTime[(i*3)]) {
                    testInsertionTime[(i*3)] = timer.getElapsedTime();
                } else if (timer.getElapsedTime() > testInsertionTime[(i*3)+2]) {
                    testInsertionTime[(i*3)+2] = timer.getElapsedTime();
                }
                TimeUnit.MILLISECONDS.sleep(1);
                timer.reset();
                testDirectoryArray[i].deleteEntryUsingName(testInsertionEntry.getSurname());
                prog.progress(1);
                prog.show();
            }
            // Calculate average
            testInsertionTime[(i*3)+1] /= testNumber;
        }

        prog.finish();

        // Fill deletion by name time results array with 0s
        long[] testDeletionByNameTime = new long[3];
        Arrays.fill(testDeletionByNameTime, 0);

        // DeleteEntryByName test
        for (int i = 0; i < testDirectoryArray.length; i++) {
            for (int j = 0; j < testNumber; j++) {
                timer.start();
                testDirectoryArray[i].deleteEntryUsingName(testDeletionEntry.getSurname());
                timer.stop();
                testDeletionByNameTime[i] += timer.getElapsedTime();
                timer.reset();
                testDirectoryArray[i].insertEntry(testDeletionEntry);
            }
            // Calculate average
            testDeletionByNameTime[i] /= testNumber;
        }

        // Fill deletion by extension time results array with 0s
        long[] testDeletionByExtensionTime = new long[3];
        Arrays.fill(testDeletionByExtensionTime, 0);

        // DeleteEntryByExtension test
        for (int i = 0; i < testDirectoryArray.length; i++) {
            for (int j = 0; j < testNumber; j++) {
                timer.start();
                testDirectoryArray[i].deleteEntryUsingExtension(testDeletionEntry.getExtension());
                timer.stop();
                testDeletionByExtensionTime[i] += timer.getElapsedTime();
                timer.reset();
                testDirectoryArray[i].insertEntry(testDeletionEntry);
            }
            // Calculate average
            testDeletionByExtensionTime[i] /= testNumber;
        }

        // Fill update extension time results array with 0s
        long[] testUpdateExtensionTime = new long[3];
        Arrays.fill(testUpdateExtensionTime, 0);

        // UpdateEntryExtension test
        for (int i = 0; i < testDirectoryArray.length; i++) {
            for (int j = 0; j < testNumber; j++) {
                testDirectoryArray[i].insertEntry(testInsertionEntry);
                timer.start();
                testDirectoryArray[i].updateExtensionUsingName(testInsertionEntry.getSurname(), "98765");
                timer.stop();
                testUpdateExtensionTime[i] += timer.getElapsedTime();
                timer.reset();
                testDirectoryArray[i].updateExtensionUsingName(testInsertionEntry.getSurname(), "01234");
            }
            // Calculate average
            testUpdateExtensionTime[i] /= testNumber;
        }

        // Fill lookup extension time results array with 0s
        long[] testLookupExtensionTime = new long[3];
        Arrays.fill(testLookupExtensionTime, 0);

        // LookupExtension test
        for (int i = 0; i < testDirectoryArray.length; i++) {
            for (int j = 0; j < testNumber; j++) {
                timer.start();
                testDirectoryArray[i].lookupExtension(testDeletionEntry.getSurname());
                timer.stop();
                testLookupExtensionTime[i] += timer.getElapsedTime();
                timer.reset();
            }
            // Calculate average
            testLookupExtensionTime[i] /= testNumber;
        }

        System.out.println("\nPerformance Tests:\n");
        System.out.println(String.format("\nEntry Insertion Times:\n\n%-18s = %11d ns (min), %6d ns (avg), %11d ns (max)\n%-18s = %11d ns (min), %6d ns (avg), %11d ns (max)\n%-18s = %11d ns (min), %6d ns (avg), %11d ns (max)\n", choices[0], testInsertionTime[0], testInsertionTime[1], testInsertionTime[2], choices[1], testInsertionTime[3], testInsertionTime[4], testInsertionTime[5], choices[2], testInsertionTime[6], testInsertionTime[7], testInsertionTime[8]));
        System.out.println(String.format("\nAverage Entry Deletion By Name Times:\n\n%-18s = %6d ns\n%-18s = %6d ns\n%-18s = %6d ns\n", choices[0], testDeletionByNameTime[0], choices[1], testDeletionByNameTime[1], choices[2], testDeletionByNameTime[2]));
        System.out.println(String.format("\nAverage Entry Deletion By Extension Times:\n\n%-18s = %6d ns\n%-18s = %6d ns\n%-18s = %6d ns\n", choices[0], testDeletionByExtensionTime[0], choices[1], testDeletionByExtensionTime[1], choices[2], testDeletionByExtensionTime[2]));
        System.out.println(String.format("\nAverage Update Extension Times:\n\n%-18s = %6d ns\n%-18s = %6d ns\n%-18s = %6d ns\n", choices[0], testUpdateExtensionTime[0], choices[1], testUpdateExtensionTime[1], choices[2], testUpdateExtensionTime[2]));
        System.out.println(String.format("\nAverage Lookup Extension Times:\n\n%-18s = %6d ns\n%-18s = %6d ns\n%-18s = %6d ns\n", choices[0], testLookupExtensionTime[0], choices[1], testLookupExtensionTime[1], choices[2], testLookupExtensionTime[2]));
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
        System.out.print("");
        while (true) {
            try {
                choice = input.nextInt();
                if ((choice < 1) || (choice > options.size())) {
                    throw new IndexOutOfBoundsException();
                }
            } catch (InputMismatchException|ClassCastException|IndexOutOfBoundsException e) {
                System.out.print("Please enter the number which corresponds to your choice.\n");
                input.next();
                continue;
            }
            break;
        }
        return choice;
    }
}
