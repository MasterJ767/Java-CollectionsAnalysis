import java.util.*;
import java.util.concurrent.TimeUnit;

public class Analysis {
    static String testInsertionEntryMethod(Directory[] testDirectoryArray, String[] testDirectoryTypes, int testNumber, StopWatch timer, Entry testInsertionEntry) throws InterruptedException {
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

    static String testDeletionEntryByNameMethod(Directory[] testDirectoryArray, String[] testDirectoryTypes, int testNumber, StopWatch timer, List<Entry> testData) throws InterruptedException {
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

    static String testDeletionEntryByExtensionMethod(Directory[] testDirectoryArray, String[] testDirectoryTypes, int testNumber, StopWatch timer, List<Entry> testData) throws InterruptedException {
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

    static String testUpdateEntryExtensionMethod(Directory[] testDirectoryArray, String[] testDirectoryTypes, int testNumber, StopWatch timer, Entry testInsertionEntry) throws InterruptedException {
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

    static String testLookupExtensionMethod(Directory[] testDirectoryArray, String[] testDirectoryTypes, int testNumber, StopWatch timer, Entry testDeletionEntry) throws InterruptedException {
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

    static String testToArrayListMethod(Directory[] testDirectoryArray, String[] testDirectoryTypes, int testNumber, StopWatch timer) throws InterruptedException {
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
