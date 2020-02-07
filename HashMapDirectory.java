import java.util.*;

public class HashMapDirectory implements Directory {
    private HashMap<String, Entry> surnameDirectory;
    private HashMap<String, Entry> extensionDirectory;

    public HashMapDirectory() {
        surnameDirectory = new HashMap<>();
        extensionDirectory = new HashMap<>();
    }

    public void insertEntry (Entry entry) {
        surnameDirectory.put(entry.getSurname(), entry);
        extensionDirectory.put(entry.getExtension(), entry);
    }

    public void deleteEntryUsingName(String surname) {
        // Obtain length of current array
        if (surnameDirectory.containsKey(surname)) {
            surnameDirectory.remove(surname);
            // remove from extensiondirectory too
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
