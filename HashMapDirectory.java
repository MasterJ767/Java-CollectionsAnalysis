import java.util.*;

public class HashMapDirectory implements Directory {
    private HashMap<String, Entry> surnameDirectory;
    private HashMap<String, Entry> extensionDirectory;

    public HashMapDirectory() {
        // Construct two empty HashMaps on initialisation of class
        surnameDirectory = new HashMap<>();
        extensionDirectory = new HashMap<>();
    }

    @Override
    public void insertEntry (Entry entry) {
        // Add entry to both HashMaps
        surnameDirectory.put(entry.getSurname(), entry);
        extensionDirectory.put(entry.getExtension(), entry);
    }

    @Override
    public void deleteEntryUsingName(String surname) {
        // Throw an error if there is an attempt to remove an entry from an empty directory
        if (surnameDirectory.size() == 0) {
            throw new EmptyDirectoryException("An attempt to remove an entry from an empty directory was made.");
        } else {
            // Find entry which contains the given surname
            if (surnameDirectory.containsKey(surname)) {
                // Remove entry from the directory where the surnames are the keys
                surnameDirectory.remove(surname);
                // Find entry which contains the given surname and extract its associated extension
                String number = surnameDirectory.get(surname).getExtension();
                // Remove entry from the directory where the extensions are the keys
                extensionDirectory.remove(number);
            }
            // If the given surname cannot be found within the directory throw an error
            try {
                throw new SurnameNotFoundException(String.format("The surname %s could not be found in this HashMapDirectory, nothing was deleted", surname));
            }
            catch(SurnameNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void deleteEntryUsingExtension(String number) {
        // Throw an error if there is an attempt to remove an entry from an empty directory
        if (surnameDirectory.size() == 0) {
            throw new EmptyDirectoryException("An attempt to remove an entry from an empty directory was made.");
        } else {
            // Find entry which contains the given extension
            if (extensionDirectory.containsKey(number)) {
                // Remove entry from the directory where the extensions are the keys
                extensionDirectory.remove(number);
                // Find entry which contains the given extension and extract its associated surname
                String surname = surnameDirectory.get(number).getSurname();
                // Remove entry from the directory where the surnames are the keys
                surnameDirectory.remove(surname);
            }
            // If the given extension cannot be found within the directory throw an error
            try {
                throw new ExtensionNotFoundException(String.format("The extension %s could not be found in this HashMapDirectory, nothing was deleted", number));
            }
            catch(ExtensionNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void updateExtensionUsingName(String surname, String newNumber) {
        if (newNumber.length() != 5) {
            // Tell the user the new extension is of illegal length
            throw new IllegalExtensionException(String.format("The extension %s is of length %d. Extensions should be of length 5.", newNumber, newNumber.length()));
        } else {
            // Find entry which contains the given surname
            if (surnameDirectory.containsKey(surname)) {
                // Change extension information
                surnameDirectory.get(surname).setExtension(newNumber);
            } else {
                // If the given surname cannot be found within the directory throw an error
                try {
                    throw new SurnameNotFoundException(String.format("The surname %s could not be found in this HashMapDirectory, nothing was updated", surname));
                } catch (SurnameNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public String lookupExtension(String surname) {
        // Find entry which contains the given surname
        if (surnameDirectory.containsKey(surname)) {
            // Return extension information
            return surnameDirectory.get(surname).getExtension();
        }
        // If the given surname cannot be found within the directory throw an error
        try {
            throw new SurnameNotFoundException(String.format("The surname %s could not be found in this HashMapDirectory", surname));
        }
        catch(SurnameNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Entry> toArrayList() {
        // Copy values from the surnameDirectory to an ArrayList and return
        ArrayList<Entry> listDirectory = new ArrayList<>();
        for (String key : surnameDirectory.keySet()) {
            listDirectory.add(surnameDirectory.get(key));
        }
        return listDirectory;
    }

    public String toString() {
        // Return a String with the contents of the directory formatted into a table
        String[] titles = {"Surname", "Initials", "Extension"};
        return TabularPrint.toTableString(titles, toArrayList());
    }
}
