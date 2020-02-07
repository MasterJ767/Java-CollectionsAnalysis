import java.util.*;

public abstract class ArrayListDirectory implements Directory {
    private ArrayList<Entry> directory;

    public ArrayListDirectory() {
        directory = new ArrayList<>();
    }

    public void insertEntry (Entry entry) {
        directory.add(entry);
    }

    public void deleteEntryUsingName(String surname) {
        // Obtain length of current array
        for (Entry entry: directory) {
            if (entry.getSurname().equals(surname)) {
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

    public void deleteEntryUsingExtension(String number) {
        // Obtain length of current array
        for (Entry entry: directory) {
            if (entry.getExtension().equals(number)) {
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

    public List<Entry> toArrayList() {
        return directory;
    }
}
