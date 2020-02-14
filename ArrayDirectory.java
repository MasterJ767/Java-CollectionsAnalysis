import java.util.*;

public class ArrayDirectory implements Directory {
    private Entry[] directory;

    public ArrayDirectory() {
        // Construct an empty Array on initialisation of class
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
    public void deleteEntryUsingName(String surname) {
        // Throw an error if there is an attempt to remove an entry from an empty directory
        if (directory.length == 0) {
            throw new EmptyDirectoryException("An attempt to remove an entry from an empty directory was made.");
        } else {
            boolean found = false;
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
                    found = true;
                    break;
                }
                found = false;
            }
            if (!found) {
                // If the given surname cannot be found within the directory throw an error
                try {
                    throw new SurnameNotFoundException(String.format("The surname %s could not be found in this ArrayDirectory, nothing was deleted", surname));
                } catch (SurnameNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void deleteEntryUsingExtension(String number){
        // Throw an error if there is an attempt to remove an entry from an empty directory
        if (directory.length == 0) {
            throw new EmptyDirectoryException("An attempt to remove an entry from an empty directory was made.");
        } else {
            boolean found = false;
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
                    found = true;
                    break;
                }
                found = false;
            }
            if (!found) {
                // If the given extension cannot be found within the directory throw an error
                try {
                    throw new ExtensionNotFoundException(String.format("The extension %s could not be found in this ArrayDirectory, nothing was deleted", number));
                } catch (ExtensionNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void updateExtensionUsingName(String surname, String newNumber) {
        if (newNumber.length() != 5) {
            // Tell the user the new extension is of illegal length
            throw new IllegalExtensionException(String.format("The extension %s is of length %d. Extensions should be of length 5.", newNumber, newNumber.length()));
        } else {
            boolean found = false;
            // Find entry which contains the given surname
            for (Entry entry : directory) {
                if (entry.getSurname().equals(surname)) {
                    // Change extension information
                    entry.setExtension(newNumber);
                    found = true;
                    break;
                }
                found = false;
            }
            if (!found) {
                // If the given surname cannot be found within the directory throw an error
                try {
                    throw new SurnameNotFoundException(String.format("The surname %s could not be found in this ArrayDirectory, nothing was updated", surname));
                } catch (SurnameNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
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
            throw new SurnameNotFoundException(String.format("The surname %s could not be found in this ArrayDirectory", surname));
        }
        catch(SurnameNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Entry> toArrayList() {
        // Copy contents of the directory to an ArrayList and return
        ArrayList<Entry> listDirectory = new ArrayList<>();
        Collections.addAll(listDirectory, directory);
        return listDirectory;
    }

    public String toString() {
        // Return a String with the contents of the directory formatted into a table
        String[] titles = {"Surname", "Initials", "Extension"};
        return TabularPrint.toTableString(titles, toArrayList());
    }
}