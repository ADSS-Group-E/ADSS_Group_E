package BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportController {
    private HashMap<Integer,Report> reports;
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
        ArrayList<Report> list = new ArrayList<Report>(reports.values());
        return list;
    }
}
