import java.util.*;

public class TabularPrint {
    public static String toTable(String[] titles, List<Entry> records) {

        String[] horizontal = new String[3];
        String dash = "-";
        horizontal[0] = dash.repeat(17);
        horizontal[1] = dash.repeat(11);
        horizontal[2] = dash.repeat(11);

        StringBuilder table = new StringBuilder();
        table.append(toTableRow(horizontal, "+","+","+"));
        table.append(toTableRow(titles, "¦ "," ¦ "," ¦"));
        table.append(toTableRow(horizontal, "+","+","+"));
        for (Entry entry: records) {
            table.append(toTableRow(entry.toArray(), "¦ "," ¦ "," ¦"));
        }
        table.append(toTableRow(horizontal, "+","+","+"));

        return table.toString();
    }

    private static String toTableRow(String[] values, String leftSeparator, String middleSeparator, String rightSeparator) {
        return String.format("%1$s%2$-15s%3$s%4$-9s%3$s%5$9s%6$s\n",leftSeparator, values[0], middleSeparator, values[1], values[2], rightSeparator);
    }

    public static void printtb(String[] titles, List<Entry> records){
        System.out.println(toTable(titles, records));
    }
}
