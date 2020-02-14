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
                // Remove entry from the directory where the extensions are the keys
                extensionDirectory.remove(surnameDirectory.get(surname).getExtension());
                // Remove entry from the directory where the surnames are the keys
                surnameDirectory.remove(surname);

            } else {
                // If the given surname cannot be found within the directory throw an error
                try {
                    throw new SurnameNotFoundException(String.format("The surname %s could not be found in this HashMapDirectory, nothing was deleted", surname));
                } catch (SurnameNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void deleteEntryUsingExtension(String number) {
        // Throw an error if there is an attempt to remove an entry from an empty directory
        if (extensionDirectory.size() == 0) {
            throw new EmptyDirectoryException("An attempt to remove an entry from an empty directory was made.");
        } else {
            // Find entry which contains the given extension
            if (extensionDirectory.containsKey(number)) {
                // Remove entry from the directory where the surnames are the keys
                surnameDirectory.remove(extensionDirectory.get(number).getSurname());
                // Remove entry from the directory where the extensions are the keys
                extensionDirectory.remove(number);
            } else {
                // If the given extension cannot be found within the directory throw an error
                try {
                    throw new ExtensionNotFoundException(String.format("The extension %s could not be found in this HashMapDirectory, nothing was deleted", number));
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
            if (surnameDirectory.containsKey(surname)) {
                // Update extensionDirectory keys by deleting old k,v pair and reinserting entry with new extension as the key
                extensionDirectory.remove(surnameDirectory.get(surname).getExtension());
                extensionDirectory.put(newNumber, surnameDirectory.get(surname));
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
        // Convert directory values to a new array list and return
        return new ArrayList<>(surnameDirectory.values());
    }

    public String toString() {
        // Return a String with the contents of the directory formatted into a table
        String[] titles = {"Surname", "Initials", "Extension"};
        return TabularPrint.toTableString(titles, toArrayList());
    }
}
