import java.util.*;

public class TabularPrint {
    public static String toTable(String[] titles, List<Entry> records) {
        StringBuilder horizontal = new StringBuilder();
        String dash = "-";
        horizontal.ensureCapacity(32);
        horizontal.append("+");
        horizontal.append(dash.repeat(17));
        horizontal.append("+");
        horizontal.append(dash.repeat(11));
        horizontal.append("+");
        horizontal.append(dash.repeat(11));
        horizontal.append("+\n");

        StringBuilder table = new StringBuilder();
        table.append(horizontal);
        table.append(String.format("¦ %-15s ¦ %-9s ¦ %9s ¦\n", "Surname", "Initials", "Extension"));
        table.append(horizontal);
        for (Entry entry: records) {
            table.append(entry.toStringTabular());
        }
        table.append(horizontal);

        return table.toString();
    }

    public static void printtb(String[] titles, List<Entry> records){
        System.out.println(toTable(titles, records));
    }
}
