package BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportController {
    private HashMap<Integer,Report> reports;
    private int rid;

    public StockReport generateStockReport(ArrayList<Product> products){
        StockReport report = new StockReport(rid, products);
        reports.put(rid,report);
        rid++;
        return report;
    }

    public Report getReport(int rid){
        return reports.get(rid);
    }

    public ReportController() {
        this.rid = 1;
        reports = new HashMap<>();
    }
}
