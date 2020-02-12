import java.util.*;

public abstract class ArrayListDirectory implements Directory {
    private ArrayList<Entry> directory;

    public ArrayListDirectory() {
        directory = new ArrayList<>();
    }

    @Override
    public void insertEntry (Entry entry) {
        directory.add(entry);
    }

    @Override
    public void deleteEntryUsingName(String surname) throws EmptyDirectoryException{
        // Throw an error if there is an attempt to remove an entry from an empty directory
        if (directory.size() == 0) {
            throw new EmptyDirectoryException("An attempt to remove an entry from an empty directory was made.");
        } else {
            // Find entry which contains the given surname
            for (Entry entry: directory) {
                if (entry.getSurname().equals(surname)) {
                    // Delete entry from directory
                    directory.remove(entry);
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
        if (directory.size() == 0) {
            throw new EmptyDirectoryException("An attempt to remove an entry from an empty directory was made.");
        } else {
            // Find entry which contains the given extension
            for (Entry entry: directory) {
                if (entry.getExtension().equals(number)) {
                    // Delete entry from directory
                    directory.remove(entry);
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
        return directory;
    }
}
