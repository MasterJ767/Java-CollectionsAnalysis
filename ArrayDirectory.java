import java.util.*;

public abstract class ArrayDirectory implements Directory {
    private Entry[] directory;

    public ArrayDirectory() {
        this.directory = new Entry[0];
    }

    public void insertEntry (Entry entry) {
        // Obtain length of current directory
        int currentLength = directory.length;
        // Create new directory which is 1 greater in length
        Entry[] newDirectory = new Entry[currentLength + 1];
        // Copy the contents of the old array to the new array
        System.arraycopy(directory, 0, newDirectory, 0, currentLength);
        // Add the new entry to the end
        newDirectory[currentLength] = entry;
        // change the reference of this.directory to the new array
        directory = newDirectory;
    }

    public void deleteEntryUsingName(String surname) {
        // Obtain length of current array
        int currentLength = directory.length;
        // Create new directory which is 1 smaller in length
        Entry[] newDirectory = new Entry[currentLength - 1];
        // Find entry which contains the given surname
        for (int i = 0; i < currentLength; i++) {
            if (directory[i].getSurname().equals(surname)) {
                // Add all entries before the deleted entry to the new directory
                System.arraycopy(directory, 0, newDirectory, 0, i);
                /* If the entry is not the last element in the array,
                add all entries after the deleted entry to the new directory*/
                if (i < (currentLength - 1)) {
                    System.arraycopy(directory, i + 1, newDirectory, i, currentLength - (i + 1));
                }
                directory = newDirectory;
                break;
            }
        }
    }

    public void deleteEntryUsingExtension(String number) {
        // Obtain length of current array
        int currentLength = directory.length;
        // Create new directory which is 1 smaller in length
        Entry[] newDirectory = new Entry[currentLength - 1];
        // Find entry which contains the given surname
        for (int i = 0; i < currentLength; i++) {
            if (directory[i].getExtension().equals(number)) {
                // Add all entries before the deleted entry to the new directory
                System.arraycopy(directory, 0, newDirectory, 0, i);
                /* If the entry is not the last element in the array,
                add all entries after the deleted entry to the new directory*/
                if (i < (currentLength - 1)) {
                    System.arraycopy(directory, i + 1, newDirectory, i, currentLength - (i + 1));
                }
                directory = newDirectory;
                break;
            }
        }
    }

    public void updateExtensionUsingName(String surname, String newNumber) {
        ;
    }

    public String lookupExtension(String surname) {
        ;
    }

    public List<Entry> toArrayList() {
        ;
    }
}