import java.util.*;

public class ArrayListDirectory implements Directory {
    private ArrayList<Entry> directory;

    public ArrayListDirectory() {
        // Construct an empty ArrayList on initialisation of class
        directory = new ArrayList<>();
    }

    @Override
    public void insertEntry (Entry entry) {
        // Add entry to the directory ArrayList
        directory.add(entry);
    }

    @Override
    public void deleteEntryUsingName(String surname) {
        // Throw an error if there is an attempt to remove an entry from an empty directory
        if (directory.size() == 0) {
            throw new EmptyDirectoryException("An attempt to remove an entry from an empty directory was made.");
        } else {
            boolean found = false;
            // Find entry which contains the given surname
            for (Entry entry: directory) {
                if (entry.getSurname().equals(surname)) {
                    // Delete entry from directory
                    directory.remove(entry);
                    found = true;
                    break;
                }
                found = false;
            }
            if (!found) {
                // If the given surname cannot be found within the directory throw an error
                try {
                    throw new SurnameNotFoundException(String.format("The surname %s could not be found in this ArrayListDirectory, nothing was deleted", surname));
                } catch (SurnameNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void deleteEntryUsingExtension(String number) {
        // Throw an error if there is an attempt to remove an entry from an empty directory
        if (directory.size() == 0) {
            throw new EmptyDirectoryException("An attempt to remove an entry from an empty directory was made.");
        } else {
            boolean found = false;
            // Find entry which contains the given extension
            for (Entry entry: directory) {
                if (entry.getExtension().equals(number)) {
                    // Delete entry from directory
                    directory.remove(entry);
                    found = true;
                    break;
                }
                found = false;
            }
            if (!found) {
                // If the given extension cannot be found within the directory throw an error
                try {
                    throw new ExtensionNotFoundException(String.format("The extension %s could not be found in this ArrayListDirectory, nothing was deleted", number));
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
                    throw new SurnameNotFoundException(String.format("The surname %s could not be found in this ArrayListDirectory, nothing was updated", surname));
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
            throw new SurnameNotFoundException(String.format("The surname %s could not be found in this ArrayListDirectory", surname));
        }
        catch(SurnameNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Entry> toArrayList() {
        // The directory is already an ArrayList so no conversion is necessary
        return directory;
    }

    public String toString() {
        // Return a String with the contents of the directory formatted into a table
        String[] titles = {"Surname", "Initials", "Extension"};
        return TabularPrint.toTableString(titles, directory);
    }
}
