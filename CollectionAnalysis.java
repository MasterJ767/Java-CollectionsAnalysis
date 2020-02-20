import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CollectionAnalysis {
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
        String insertEntryResults = testInsertionEntryMethod(testDirectoryArray, choices, testNumber, timer, testInsertionEntry);
        performanceResults[1] = insertEntryResults;
        System.out.println(insertEntryResults);

        // Run DeleteEntryByName tests
        String deletionEntryByNameResults = testDeletionEntryByNameMethod(testDirectoryArray, choices, testNumber, timer, cleanData);
        performanceResults[2] = deletionEntryByNameResults;
        System.out.println(deletionEntryByNameResults);

        // Run DeleteEntryByExtension tests
        String deletionEntryByExtensionResults = testDeletionEntryByExtensionMethod(testDirectoryArray, choices, testNumber, timer, cleanData);
        performanceResults[3] = deletionEntryByExtensionResults;
        System.out.println(deletionEntryByExtensionResults);

        // Run UpdateEntryExtension tests
        String updateEntryExtensionResults = testUpdateEntryExtensionMethod(testDirectoryArray, choices, testNumber, timer, testInsertionEntry);
        performanceResults[4] = updateEntryExtensionResults;
        System.out.println(updateEntryExtensionResults);

        // Run LookupExtension tests
        String lookupExtensionResults = testLookupExtensionMethod(testDirectoryArray, choices, testNumber, timer, testLookupEntry);
        performanceResults[5] = lookupExtensionResults;
        System.out.println(lookupExtensionResults);

        // Run ToArrayList tests
        String toArrayListResults = testToArrayListMethod(testDirectoryArray, choices, testNumber, timer);
        performanceResults[6] = toArrayListResults;
        System.out.println(toArrayListResults);

        // Write performance results to an output txt file
        FileOutput.performanceOutput(performanceResults);
    }

    static Directory createDirectory(ArrayList<Entry> data, int variant) {
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

    private static String testInsertionEntryMethod(Directory[] testDirectoryArray, String[] testDirectoryTypes, int testNumber, StopWatch timer, Entry testInsertionEntry) throws InterruptedException {
        // Create an array to time results of InsertEntry tests
        long[] testInsertionTime = new long[3*testDirectoryArray.length];

        // Create progress bar
        ProgressBar progress = new ProgressBar(testNumber * testDirectoryArray.length, "Test Progress", 100);

        // InsertEntry test
        for (int i = 0; i < testDirectoryArray.length; i++) {
            // Set best time to max long value and set average and worst times to 0
            testInsertionTime[(i * 3)] = Long.MAX_VALUE;
            testInsertionTime[(i * 3) + 1] = 0;
            testInsertionTime[(i * 3) + 2] = 0;
            for (int j = 0; j < testNumber; j++) {
                timer.start();
                testDirectoryArray[i].insertEntry(testInsertionEntry);
                timer.stop();
                /// Perform best, average and worst calculations
                long[]returnedTimes = timeCalculations(testInsertionTime, i, timer.getElapsedTime());
                System.arraycopy(returnedTimes, 0,testInsertionTime,0,3*testDirectoryArray.length);
                timer.reset();
                // Delete inserted entry so it may be added again in the next test
                testDirectoryArray[i].deleteEntryUsingName(testInsertionEntry.getSurname());
                // Add progress to progress bar
                progress.progress(1);
                progress.show();
            }
            // Calculate average
            testInsertionTime[(i * 3) + 1] /= testNumber;
        }

        // Print results of InsertEntry tests
        return String.format("\n\nEntry Insertion Test Times:\n\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n", testDirectoryTypes[0], testInsertionTime[0], testInsertionTime[1], testInsertionTime[2], testDirectoryTypes[1], testInsertionTime[3], testInsertionTime[4], testInsertionTime[5], testDirectoryTypes[2], testInsertionTime[6], testInsertionTime[7], testInsertionTime[8]);
    }

    private static String testDeletionEntryByNameMethod(Directory[] testDirectoryArray, String[] testDirectoryTypes, int testNumber, StopWatch timer, ArrayList<Entry> testData) throws InterruptedException {
        // Create an array to time results of DeleteEntryByName tests
        long[] testDeletionByNameTime = new long[3*testDirectoryArray.length];

        // Create progress bar
        ProgressBar progress = new ProgressBar(testNumber*testDirectoryArray.length, "Test Progress", 100);

        // DeleteEntryByName test
        for (int i = 0; i < testDirectoryArray.length; i++) {
            // Set best time to max long value and set average and worst times to 0
            testDeletionByNameTime[(i*3)] = Long.MAX_VALUE;
            testDeletionByNameTime[(i*3)+1] = 0;
            testDeletionByNameTime[(i*3)+2] = 0;
            for (int j = 0; j < testNumber; j++) {
                // Obtain an entry from the middle of the directories
                int index = testData.size() / 2;
                Entry testDeletionEntry = testData.get(index);
                timer.start();
                testDirectoryArray[i].deleteEntryUsingName(testDeletionEntry.getSurname());
                timer.stop();
                // Perform best, average and worst calculations
                long[]returnedTimes = timeCalculations(testDeletionByNameTime, i, timer.getElapsedTime());
                System.arraycopy(returnedTimes, 0,testDeletionByNameTime,0,3*testDirectoryArray.length);
                timer.reset();
                // Insert deleted entry so it may be removed again in next test
                testDirectoryArray[i].insertEntry(testDeletionEntry);
                // Remove test entry and re-add to the end of the data, so the code always tests the data at the middle of the Array/ArrayList directory
                testData.remove(testDeletionEntry);
                testData.add(testDeletionEntry);
                // Add progress to progress bar
                progress.progress(1);
                progress.show();
            }
            // Calculate average
            testDeletionByNameTime[(i*3)+1] /= testNumber;
        }

        return String.format("\n\nEntry Deletion By Name Test Times:\n\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n", testDirectoryTypes[0], testDeletionByNameTime[0], testDeletionByNameTime[1], testDeletionByNameTime[2], testDirectoryTypes[1], testDeletionByNameTime[3], testDeletionByNameTime[4], testDeletionByNameTime[5], testDirectoryTypes[2], testDeletionByNameTime[6], testDeletionByNameTime[7], testDeletionByNameTime[8]);
    }

    private static String testDeletionEntryByExtensionMethod(Directory[] testDirectoryArray, String[] testDirectoryTypes, int testNumber, StopWatch timer, ArrayList<Entry> testData) throws InterruptedException {
        // Create an array to time results of DeleteEntryByExtension tests
        long[] testDeletionByExtensionTime = new long[3*testDirectoryArray.length];

        // Create progress bar
        ProgressBar progress = new ProgressBar(testNumber*testDirectoryArray.length, "Test Progress", 100);

        // DeleteEntryByExtension test
        for (int i = 0; i < testDirectoryArray.length; i++) {
            // Set best time to max long value and set average and worst times to 0
            testDeletionByExtensionTime[(i*3)] = Long.MAX_VALUE;
            testDeletionByExtensionTime[(i*3)+1] = 0;
            testDeletionByExtensionTime[(i*3)+2] = 0;
            for (int j = 0; j < testNumber; j++) {
                // Obtain an entry from the middle of the directories
                int index = testData.size() / 2;
                Entry testDeletionEntry = testData.get(index);
                timer.start();
                testDirectoryArray[i].deleteEntryUsingExtension(testDeletionEntry.getExtension());
                timer.stop();
                // Perform best, average and worst calculations
                long[]returnedTimes = timeCalculations(testDeletionByExtensionTime, i, timer.getElapsedTime());
                System.arraycopy(returnedTimes, 0,testDeletionByExtensionTime,0,3*testDirectoryArray.length);
                timer.reset();
                // Insert deleted entry so it may be removed again in next test
                testDirectoryArray[i].insertEntry(testDeletionEntry);
                // Remove test entry and re-add to the end of the data, so the code always tests the data at the middle of the Array/ArrayList directory
                testData.remove(testDeletionEntry);
                testData.add(testDeletionEntry);
                // Add progress to progress bar
                progress.progress(1);
                progress.show();
            }
            // Calculate average
            testDeletionByExtensionTime[(i*3)+1] /= testNumber;
        }

        return String.format("\n\nEntry Deletion By Extension Test Times:\n\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n", testDirectoryTypes[0], testDeletionByExtensionTime[0], testDeletionByExtensionTime[1], testDeletionByExtensionTime[2], testDirectoryTypes[1], testDeletionByExtensionTime[3], testDeletionByExtensionTime[4], testDeletionByExtensionTime[5], testDirectoryTypes[2], testDeletionByExtensionTime[6], testDeletionByExtensionTime[7], testDeletionByExtensionTime[8]);
    }

    private static String testUpdateEntryExtensionMethod(Directory[] testDirectoryArray, String[] testDirectoryTypes, int testNumber, StopWatch timer, Entry testInsertionEntry) throws InterruptedException {
        // Create an array to time results of UpdateEntryExtension tests
        long[] testUpdateExtensionTime = new long[3*testDirectoryArray.length];

        // Create progress bar
        ProgressBar progress = new ProgressBar(testNumber*testDirectoryArray.length, "Test Progress", 100);

        // UpdateEntryExtension test
        for (int i = 0; i < testDirectoryArray.length; i++) {
            // Set best time to max long value and set average and worst times to 0
            testUpdateExtensionTime[(i*3)] = Long.MAX_VALUE;
            testUpdateExtensionTime[(i*3)+1] = 0;
            testUpdateExtensionTime[(i*3)+2] = 0;
            for (int j = 0; j < testNumber; j++) {
                testDirectoryArray[i].insertEntry(testInsertionEntry);
                timer.start();
                testDirectoryArray[i].updateExtensionUsingName(testInsertionEntry.getSurname(), "98765");
                timer.stop();
                // Perform best, average and worst calculations
                long[]returnedTimes = timeCalculations(testUpdateExtensionTime, i, timer.getElapsedTime());
                System.arraycopy(returnedTimes, 0,testUpdateExtensionTime,0,3*testDirectoryArray.length);
                timer.reset();
                testDirectoryArray[i].updateExtensionUsingName(testInsertionEntry.getSurname(), "01234");
                // Add progress to progress bar
                progress.progress(1);
                progress.show();
            }
            // Calculate average
            testUpdateExtensionTime[(i*3)+1] /= testNumber;
        }

        return String.format("\n\nUpdate Entry Extension Test Times:\n\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n", testDirectoryTypes[0], testUpdateExtensionTime[0], testUpdateExtensionTime[1], testUpdateExtensionTime[2], testDirectoryTypes[1], testUpdateExtensionTime[3], testUpdateExtensionTime[4], testUpdateExtensionTime[5], testDirectoryTypes[2], testUpdateExtensionTime[6], testUpdateExtensionTime[7], testUpdateExtensionTime[8]);
    }

    private static String testLookupExtensionMethod(Directory[] testDirectoryArray, String[] testDirectoryTypes, int testNumber, StopWatch timer, Entry testDeletionEntry) throws InterruptedException {
        // Create an array to time results of LookupExtension tests
        long[] testLookupExtensionTime = new long[3*testDirectoryArray.length];

        // Create progress bar
        ProgressBar progress = new ProgressBar(testNumber*testDirectoryArray.length, "Test Progress", 100);

        // LookupExtension test
        for (int i = 0; i < testDirectoryArray.length; i++) {
            // Set best time to max long value and set average and worst times to 0
            testLookupExtensionTime[(i*3)] = Long.MAX_VALUE;
            testLookupExtensionTime[(i*3)+1] = 0;
            testLookupExtensionTime[(i*3)+2] = 0;
            for (int j = 0; j < testNumber; j++) {
                timer.start();
                testDirectoryArray[i].lookupExtension(testDeletionEntry.getSurname());
                timer.stop();
                // Perform best, average and worst calculations
                long[]returnedTimes = timeCalculations(testLookupExtensionTime, i, timer.getElapsedTime());
                System.arraycopy(returnedTimes, 0,testLookupExtensionTime,0,3*testDirectoryArray.length);
                timer.reset();
                // Add progress to progress bar
                progress.progress(1);
                progress.show();
            }
            // Calculate average
            testLookupExtensionTime[(i*3)+1] /= testNumber;
        }


        return String.format("\n\nLookup Entry Extension Test Times:\n\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n", testDirectoryTypes[0], testLookupExtensionTime[0], testLookupExtensionTime[1], testLookupExtensionTime[2], testDirectoryTypes[1], testLookupExtensionTime[3], testLookupExtensionTime[4], testLookupExtensionTime[5], testDirectoryTypes[2], testLookupExtensionTime[6], testLookupExtensionTime[7], testLookupExtensionTime[8]);
    }

    private static String testToArrayListMethod(Directory[] testDirectoryArray, String[] testDirectoryTypes, int testNumber, StopWatch timer) throws InterruptedException {
        // Create an array to time results of LookupExtension tests
        long[] testToArrayListTime = new long[3*testDirectoryArray.length];

        // Create progress bar
        ProgressBar progress = new ProgressBar(testNumber*testDirectoryArray.length, "Test Progress", 100);

        // LookupExtension test
        for (int i = 0; i < testDirectoryArray.length; i++) {
            // Set best time to max long value and set average and worst times to 0
            testToArrayListTime[(i*3)] = Long.MAX_VALUE;
            testToArrayListTime[(i*3)+1] = 0;
            testToArrayListTime[(i*3)+2] = 0;
            for (int j = 0; j < testNumber; j++) {
                timer.start();
                testDirectoryArray[i].toArrayList();
                timer.stop();
                // Perform best, average and worst calculations
                long[]returnedTimes = timeCalculations(testToArrayListTime, i, timer.getElapsedTime());
                System.arraycopy(returnedTimes, 0,testToArrayListTime,0,3*testDirectoryArray.length);
                timer.reset();
                // Add progress to progress bar
                progress.progress(1);
                progress.show();
            }
            // Calculate average
            testToArrayListTime[(i*3)+1] /= testNumber;
        }


        return String.format("\n\nToArrayList Test Times:\n\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n%-18s = %6d ns (best), %6d ns (average), %9d ns (worst)\n", testDirectoryTypes[0], testToArrayListTime[0], testToArrayListTime[1], testToArrayListTime[2], testDirectoryTypes[1], testToArrayListTime[3], testToArrayListTime[4], testToArrayListTime[5], testDirectoryTypes[2], testToArrayListTime[6], testToArrayListTime[7], testToArrayListTime[8]);
    }

    private static long[] timeCalculations(long[] times, int i, long timeElapsed) throws InterruptedException{
        // Add time to running total in order to calculate average later
        times[(i*3)+1] += timeElapsed;
        // Compare time to previous minimum, if smaller replace
        if (timeElapsed < times[(i*3)]) {
            times[(i*3)] = timeElapsed;
        }
        // Compare time to previous maximum, if larger replace
        else if (timeElapsed > times[(i*3)+2]) {
            times[(i*3)+2] = timeElapsed;
        }
        // Force the computer to sleep otherwise some times come back as 0 ns
        TimeUnit.MILLISECONDS.sleep(1);
        return times;
    }
}
