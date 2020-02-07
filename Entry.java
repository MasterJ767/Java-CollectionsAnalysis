public class Entry {
    private String surname;
    private String initials;
    private String extension;

    public Entry(String surname, String initials, String extension) {
        this.surname = surname;
        this.initials = initials;
        this.extension = extension;
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
        this.extension = extension;
    }

    public String toString() {
        return String.format("¦%s, %s, %s¦", this.getSurname(), this.getInitials(), this.getExtension());
    }
}

