package InventoryModule.BusinessLayer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This class represents the interface for a report.
 * There are three kinds of reports; Invalid report, Stock report and Low Stock Report.
 * Each report has an RID (which stands for Report ID), and local date-time of the created report.
 * It is used to track products that are invalid or about to run out of stack, and to prevent wasting products.
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
