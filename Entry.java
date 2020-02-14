public class Entry {
    private String surname;
    private String initials;
    private String extension;

    public Entry(String surname, String initials, String extension) {
        if (extension.length() != 5) {
            // Tell the user which record is causing the issue and the location of the file causing the issue
            throw new IllegalExtensionException(String.format("The extension %s is of length %d. Extensions should be of length 5.", extension, extension.length()));
        } else {
            this.surname = surname;
            this.initials = initials;
            this.extension = extension;
        }
    }

    public String getSurname() {
        return surname;
    }

    public String getInitials() {
        return initials;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        if (extension.length() != 5) {
            // Tell the user which record is causing the issue and the location of the file causing the issue
            throw new IllegalExtensionException(String.format("The extension %s is of length %d. Extensions should be of length 5.", extension, extension.length()));
        } else {
            this.extension = extension;
        }
    }

    public String toString() {
        // Returns the attributes of the entry as a string in CSV format
        return String.format("%s,%s,%s", getSurname(), getInitials(), getExtension());
    }

    public String[] toArray(){
        // Returns the attributes of the entry in an Array
        String[] array = new String[3];
        array[0] = getSurname();
        array[1] = getInitials();
        array[2] = getExtension();
        return array;
    }

    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof Entry) {
            Entry anotherEntry = (Entry) anObject;
            // Compare the surnames of the two entries, if they are the same return true
            return this.surname.equals(anotherEntry.surname);
        }
        return false;
    }
}

