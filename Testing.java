import java.util.*;

public class Testing {
    /** Running this program will check that all directory methods work as intended.
     *  If all 26 print statements == true, the functions work as intended
     */
    public static void main(String[] args) {
        // Test Entries
        Entry entry1 = new Entry("Mayow", "R.M", "98342");
        Entry entry2 = new Entry("Prott", "S.V.C.P","39869");
        Entry entry3 = new Entry("Whatsize", "K.B.W","98287");
        Entry entry4 = new Entry("Heliet","N.H","87047");
        Entry entry5 = new Entry("Sedgemond","D.Q.S","88875");
        Entry entry6 = new Entry("Skiggs","U.R.S","68342");

        // Test Duplicate (same name different initials and extension)
        Entry entryDuplicate = new Entry("Prott","R.M", "98342");

        // Create an ArrayList of test entries
        ArrayList<Entry> testEntries = new ArrayList<>();
        testEntries.add(entry1);
        testEntries.add(entry2);
        testEntries.add(entry3);
        testEntries.add(entry4);
        testEntries.add(entry5);

        // Test all methods on ArrayDirectory - should print True 8 times
        Directory testArrayDirectory = CollectionAnalysis.createDirectory(testEntries,1);
        correctnessTest("Array", testArrayDirectory, entry6, entryDuplicate);

        // Test all methods on ArrayListDirectory - should print True 8 times
        Directory testArrayListDirectory = CollectionAnalysis.createDirectory(testEntries,2);
        correctnessTest("ArrayList", testArrayListDirectory, entry6, entryDuplicate);

        // Test all methods on HashMapDirectory - should print True 8 times
        Directory testHashMapDirectory = CollectionAnalysis.createDirectory(testEntries,3);
        correctnessTest("HashMap", testHashMapDirectory, entry6, entryDuplicate);

        // Test extensions are the correct format when being created - should print true 2 times
        System.out.println("\nExtension Correctness Tests:");
        // Test Incorrect Extensions
        // Incorrect length
        try {
            Entry entryExtensionLength = new Entry("Mayow", "R.M", "983421");
        } catch (IllegalExtensionException i) {
            System.out.println(true); // Should print True
        }

        // Incorrect characters
        try {
            Entry entryExtensionCharacter = new Entry("Mayow", "R.M", "9834R");
        } catch (IllegalExtensionException e) {
            System.out.println(true); // Should print True
        }
    }

    private static void correctnessTest(String title, Directory testDirectory, Entry sampleEntry, Entry duplicateEntry) {
        System.out.println(String.format("\n%sDirectory Correctness Tests:",title));
        // Check the directory is the size that it is supposed to be
        System.out.println(testDirectory.toArrayList().size() == 5); // Should print True
        // Test the insertEntry method
        testDirectory.insertEntry(sampleEntry);
        // Directory should be one longer
        System.out.println(testDirectory.toArrayList().size() == 6); // Should print True
        // Test deleteEntryUsingName method
        testDirectory.deleteEntryUsingName("Skiggs");
        // Directory should no longer contain the inserted entry and the size of the directory should now be 5
        System.out.println(!testDirectory.toArrayList().contains(sampleEntry) && testDirectory.toArrayList().size() == 5); // Should print True
        // Add the same entry back and remove by extension this time
        testDirectory.insertEntry(sampleEntry);
        testDirectory.deleteEntryUsingExtension("68342");
        // Directory should not contain the sampleEntry and should still only contain 5 entries
        System.out.println(!testDirectory.toArrayList().contains(sampleEntry) && testDirectory.toArrayList().size() == 5); // Should print True
        // Test UpdateExtensionUsingName method
        testDirectory.updateExtensionUsingName("Mayow","12345");
        // Test LookupExtension method
        System.out.println(testDirectory.lookupExtension("Mayow").equals("12345")); // Should print True
        // Test when an Entry extension is updated the given entry is of the correct format
        try {
            testDirectory.updateExtensionUsingName("Mayow", "983421");
        } catch (IllegalExtensionException i) {
            System.out.println(testDirectory.lookupExtension("Mayow").equals("12345")); // Should print True
        }
        try {
            testDirectory.updateExtensionUsingName("Mayow", "9834R");
        } catch (IllegalExtensionException e) {
            System.out.println(testDirectory.lookupExtension("Mayow").equals("12345")); // Should print True
        }
        // Test that logically equivalent entries (duplicates) are not added to the directory
        testDirectory.insertEntry(duplicateEntry);
        // Directory should remain size 5
        System.out.println(testDirectory.toArrayList().size() == 5); // Should print True
    }
}
