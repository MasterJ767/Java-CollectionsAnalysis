import java.util.*;

public class HashMapDirectory implements Directory {
    private HashMap<String, Entry> surnameDirectory;
    private HashMap<String, Entry> extensionDirectory;

    public HashMapDirectory() {
        surnameDirectory = new HashMap<>();
        extensionDirectory = new HashMap<>();
    }

    @Override
    public void insertEntry (Entry entry) {
        surnameDirectory.put(entry.getSurname(), entry);
        extensionDirectory.put(entry.getExtension(), entry);
    }

    @Override
    public void deleteEntryUsingName(String surname) throws EmptyDirectoryException {
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
                throw new SurnameNotFoundException(String.format("The surname %s could not be found in the directory, therefore it was not deleted", surname));
            }
            catch(SurnameNotFoundException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteEntryUsingExtension(String number) throws EmptyDirectoryException{
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
                throw new ExtensionNotFoundException(String.format("The extension %s could not be found in the directory, therefore it was not deleted", number));
            }
            catch(ExtensionNotFoundException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateExtensionUsingName(String surname, String newNumber) {
        // Find entry which contains the given surname
        if (surnameDirectory.containsKey(surname)) {
            // Change extension information
            surnameDirectory.get(surname).setExtension(newNumber);
        }
        // If the given surname cannot be found within the directory throw an error
        try {
            throw new SurnameNotFoundException(String.format("The surname %s could not be found in the directory, therefore no extensions were updated", surname));
        }
        catch(SurnameNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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
            throw new SurnameNotFoundException(String.format("The surname %s could not be found in the directory", surname));
        }
        catch(SurnameNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Entry> toArrayList() {
        ArrayList<Entry> directory = new ArrayList<>();
        for (String key : surnameDirectory.keySet()) {
            directory.add(surnameDirectory.get(key));
        }
        return directory;
    }
}
