package BusinessLayer;

import sun.reflect.generics.tree.Tree;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This class represents interface of report.
 * There is two kinds of report; Invalid report, and Stock report.
 * Each report has a rid (Stand for Report ID), and local date time of created report.
 * It's used to track products that are invalid or about to run out of stack, and to prevent wasting products.
 */

public abstract class Report {
    protected int rid;
    protected LocalDateTime created;
    protected ArrayList<String> records;

    public Report(int rid) {
        this.rid = rid;
        created = LocalDateTime.now();
        records = new ArrayList<>();
    }

    public String toString(){
        return "RID: " + rid + "     Created: " + created.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n" + headerRow() + "\n" + String.join("\n",records);
    }

    protected abstract String headerRow();

    public int getRid() {
        return rid;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public ArrayList<String> getRecords() {
        return records;
    }

    public abstract String getType();
}
