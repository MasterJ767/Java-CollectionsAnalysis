import java.util.*;

public abstract class ArrayDirectory implements Directory {
    private Entry[] directory;

    public ArrayDirectory() {
        directory = new Entry[0];
    }

    @Override
    public void insertEntry (Entry entry) {
        // Obtain length of current directory
        int currentLength = directory.length;
        // Create new directory which is 1 greater in length
        Entry[] newDirectory = new Entry[currentLength + 1];
        // Copy the contents of the old array to the new array
        System.arraycopy(directory, 0, newDirectory, 0, currentLength);
        // Add the new entry to the end
        newDirectory[currentLength] = entry;
        // Change the reference of this.directory to the new array
        directory = newDirectory;
    }

    @Override
    public void deleteEntryUsingName(String surname) throws EmptyDirectoryException{
        // Throw an error if there is an attempt to remove an entry from an empty directory
        if (directory.length == 0) {
            throw new EmptyDirectoryException("An attempt to remove an entry from an empty directory was made.");
        } else {
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
                    // Change the reference of this.directory to the new array
                    directory = newDirectory;
                    break;
                }
            }
            // If the given surname cannot be found within the directory throw an error
            try {
                throw new SurnameNotFoundException(String.format("The surname %s could not be found in the directory, therefore it was not deleted", surname));
            }
            catch(SurnameNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void deleteEntryUsingExtension(String number) throws EmptyDirectoryException{
        // Throw an error if there is an attempt to remove an entry from an empty directory
        if (directory.length == 0) {
            throw new EmptyDirectoryException("An attempt to remove an entry from an empty directory was made.");
        } else {
            // Obtain length of current array
            int currentLength = directory.length;
            // Create new directory which is 1 smaller in length
            Entry[] newDirectory = new Entry[currentLength - 1];
            // Find entry which contains the given extension
            for (int i = 0; i < currentLength; i++) {
                if (directory[i].getExtension().equals(number)) {
                    // Add all entries before the deleted entry to the new directory
                    System.arraycopy(directory, 0, newDirectory, 0, i);
                    /* If the entry is not the last element in the array,
                    add all entries after the deleted entry to the new directory*/
                    if (i < (currentLength - 1)) {
                        System.arraycopy(directory, i + 1, newDirectory, i, currentLength - (i + 1));
                    }
                    // Change the reference of this.directory to the new array
                    directory = newDirectory;
                    break;
                }
            }
            // If the given extension cannot be found within the directory throw an error
            try {
                throw new ExtensionNotFoundException(String.format("The extension %s could not be found in the directory, therefore it was not deleted", number));
            }
            catch(ExtensionNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void updateExtensionUsingName(String surname, String newNumber) {
        // Find entry which contains the given surname
        for (Entry entry : directory) {
            if (entry.getSurname().equals(surname)) {
                // Change extension information
                entry.setExtension(newNumber);
                break;
            }
        }
        // If the given surname cannot be found within the directory throw an error
        try {
            throw new SurnameNotFoundException(String.format("The surname %s could not be found in the directory, therefore no extensions were updated", surname));
        }
        catch(SurnameNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String lookupExtension(String surname) {
        // Find entry which contains the given surname
        for (Entry entry : directory) {
            if (entry.getSurname().equals(surname)) {
                // Return extension information
                return entry.getExtension();
            }
        }
        // If the given surname cannot be found within the directory throw an error
        try {
            throw new SurnameNotFoundException(String.format("The surname %s could not be found in the directory", surname));
        }
        catch(SurnameNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Entry> toArrayList() {
        return Arrays.asList(directory);
    }
}