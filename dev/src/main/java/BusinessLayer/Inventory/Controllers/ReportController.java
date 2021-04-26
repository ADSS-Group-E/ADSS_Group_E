package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the report controller.
 * All the reports are in a Hash Map that contains the Report ID as the key and the rest of the fields as the value.
 */

public class ReportController {
    private final HashMap<Integer, Report> reports;
    private int nextRid;

    public StockReport generateStockReport(ArrayList<Product> products){
        StockReport report = new StockReport(nextRid, products);
        reports.put(nextRid,report);
        nextRid++;
        return report;
    }

    public Report getReport(int rid){
        return reports.get(rid);
    }

    public ReportController() {
        this.nextRid = 1;
        reports = new HashMap<>();
    }

    public ArrayList<Report> getList() {
        return new ArrayList<>(reports.values());
    }

    public Report generateInvalidsReport(ArrayList<Product> products) {
        InvalidReport report = new InvalidReport(nextRid, products);
        reports.put(nextRid,report);
        nextRid++;
        return report;
    }

    public void removeReport(int rid) {
        reports.remove(rid);
    }

    public Report generateLowStockReport(ArrayList<Product> products) {
        LowStockReport report = new LowStockReport(nextRid, products);
        reports.put(nextRid,report);
        nextRid++;
        return report;
    }
}
