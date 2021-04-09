package BusinessLayer;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This class extends the "Report" class, and represent invalid report.
 * Each Invalid report has rid (stand for Report ID), and local date time of created report.
 * It's used to track products that are invalid, and to prevent wasting products.
 */
public class InvalidReport extends Report{

    public InvalidReport(int rid, ArrayList<Product> products) {
        super(rid);

        products.forEach((product) -> {
            product.getExpiredItems().forEach(item -> AddRecord(item,product));
        });
    }

    // Adder
    private void AddRecord(Item item, Product product){
        records.add(formatAsRecord(item, product));
    }

    // Print methods
    private static String formatAsRecord(Item item, Product product){
        return String.format("%-10d %-15d %-20s %-20s",item.getId(),product.getPid(), product.getName(), item.getExpiration().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
    }

    @Override
    protected String headerRow() {
        return String.format("%-10s %-15s %-20s %-20s","Item ID","Product PID","Product Name","Expiration");
    }

    @Override
    public String getType() {
        return "Invalids";
    }
}
