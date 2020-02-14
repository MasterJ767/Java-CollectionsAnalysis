import java.util.*;

public class TabularPrint {
    public static String toTableString(String[] titles, List<Entry> records) {
        // Build the horizontal borders of the table
        String[] horizontal = new String[3];
        String dash = "-";
        horizontal[0] = dash.repeat(15);
        horizontal[1] = dash.repeat(9);
        horizontal[2] = dash.repeat(9);

        // Build table
        StringBuilder table = new StringBuilder();
        // Add border row to table
        table.append(toTableRow(horizontal, "+-","-+-","-+"));
        // Add title row to table
        table.append(toTableRow(titles, "¦ "," ¦ "," ¦"));
        // Add border row to table
        table.append(toTableRow(horizontal, "+-","-+-","-+"));
        // Add entry rows to table
        for (Entry entry: records) {
            table.append(toTableRow(entry.toArray(), "¦ "," ¦ "," ¦"));
        }
        // Add border row to table
        table.append(toTableRow(horizontal, "+-","-+-","-+"));

        // Return table as a long string
        return table.toString();
    }

    public static String[] toTableArray(String[] titles, List<Entry> records) {
        // Build the horizontal borders of the table
        String[] horizontal = new String[3];
        String dash = "-";
        horizontal[0] = dash.repeat(15);
        horizontal[1] = dash.repeat(9);
        horizontal[2] = dash.repeat(9);

        // Build table
        String[] table = new String[(records.size() + 4)];
        // Add border row to table
        table[0] = toTableRow(horizontal, "+-","-+-","-+");
        // Add title row to table
        table[1] = toTableRow(titles, "¦ "," ¦ "," ¦");
        // Add border row to table
        table[2] = toTableRow(horizontal, "+-","-+-","-+");
        // Add entry rows to table
        for (int i = 0; i < records.size(); i++) {
            table[(i + 3)] = toTableRow(records.get(i).toArray(), "¦ "," ¦ "," ¦");
        }
        // Add border row to table
        table[records.size() + 3] = toTableRow(horizontal, "+-","-+-","-+");

        // Return table as an array of strings, where each item in the array is a row in the table
        return table;
    }

    /**
     * Return a row of a table as a string
     *
     * Table rows are formatted as follows:
     * Left Separator, String of arbitrary length
     * Cell 1, Left-aligned String which can occupy up to 15 characters
     * Middle Separator, String of arbitrary length
     * Cell 2, Left-aligned String which can occupy up to 9 characters
     * Middle Separator, String of arbitrary length
     * Cell 3, Right-aligned String which can occupy up to 9 characters
     * Right Separator, String of arbitrary length
     *
     * @param values an array containing the values of the cells in the table row
     * @param leftSeparator a string specifying the characters which form the leftmost border of the table row
     * @param middleSeparator a string specifying the characters which form the internal borders of the table row
     * @param rightSeparator a string specifying the characters which form the rightmost border of the table row
     *
     * If strings are too long for their cell, the table still prints but it doesn't retain its neat formatting
     */
    private static String toTableRow(String[] values, String leftSeparator, String middleSeparator, String rightSeparator) {
        return String.format("%1$s%2$-15s%3$s%4$-9s%3$s%5$9s%6$s\n",leftSeparator, values[0], middleSeparator, values[1], values[2], rightSeparator);
    }
}
