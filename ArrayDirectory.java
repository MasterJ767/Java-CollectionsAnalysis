import java.util.*;

public abstract class ArrayDirectory implements Directory {
    private List<Entry> directory;

    public ArrayDirectory() {
        this.directory = new ArrayList<>();
    }

    public void insertEntry (Entry entry) {
        directory.add(entry);
    }

    public void deleteEntryUsingName(String surname) {
        for (Entry entry : directory) {
            if (entry.getSurname().equals(surname)) {
                directory.remove(entry);
                break;
            }
        }
    }

    public void deleteEntryUsingExtension(String number) {
        for (Entry entry : directory) {
            if (entry.getExtension().equals(number)) {
                directory.remove(entry);
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