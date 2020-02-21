import java.time.*;

/**
 * A progress bar can be used to show progress through long running calculations.
 *
 * The progress bar displays the percentage of completion
 * (counter/total) and the real time taken by the calculation so far.
 */
public class ProgressBar {
    private int counter;
    private int total;
    private String title;
    private int width;
    private long start_time;

    /**
     * Constructs a progress bar of a specified length with
     * no progress in it so far.
     *
     * @param total maximum value of counter
     * @param title information to be displayed
     * @param width width of the display progress bar
     */
    public ProgressBar (int total, String title, int width) {
        this.counter = 0;
        this.total = total;
        this.title = title;
        this.width = width;
        this.start_time = Instant.now().getEpochSecond();
    }

    /**
     * Increase current counter by value
     */
    public void progress(int value) {
        counter += value;
    }

    /**
     * Display progress bar in its current state
     */
    public void show() {
        long sec = Instant.now().getEpochSecond() - start_time;
        float percent = 100 * counter / total;
        String title = String.format("%s (%.0f%% %02d:%02.0f)", this.title, percent, sec / 60, (float) sec % 60);
        if (title.length() >= this.width) {
            throw new IllegalArgumentException("Progress bar does not fit width. Shorten title of increase width.");
        }
        int barWidth = width - title.length() - 3;
        int fullWidth = barWidth * counter / total;
        int emptyWidth = barWidth - fullWidth;

        String full = "#";
        String empty = ".";

        System.out.print(String.format("\r%s{%s%s}", title, full.repeat(fullWidth), empty.repeat(emptyWidth)));
        System.out.flush();
    }

    /**
     * Hide progress bar
     */
    public void finish() {
        String width = " ";

        System.out.print(String.format("\r%s\r", width.repeat(this.width)));
        System.out.flush();
    }
}
