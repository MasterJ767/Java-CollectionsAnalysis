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
        return String.format("%s,%s,%s", getSurname(), getInitials(), getExtension());
    }

    public String[] toArray(){
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
            int n = surname.length();
            if (n == anotherEntry.surname.length()) {
                char[] v1 = surname.toCharArray();
                char[] v2 = anotherEntry.surname.toCharArray();
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i])
                        return false;
                    i++;
                }
                return true;
            }
        }
        return false;
    }
}

