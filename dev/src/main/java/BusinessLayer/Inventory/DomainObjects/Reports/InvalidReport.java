package BusinessLayer.Inventory.DomainObjects.Reports;

import BusinessLayer.Inventory.DomainObjects.ItemGroup;
import BusinessLayer.Inventory.DomainObjects.Product;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This class extends the "Report" class, and represents invalid reports.
 * Each Invalid report has an RID (which stands for Report ID), and local date time of the created report.
 * It is used to track products that are invalid and to prevent wasting products.
 */
public class InvalidReport extends Report{

    public InvalidReport(int rid, ArrayList<Product> products) {
        super(rid);

        this.products = products;

        products.forEach((product) -> product.getExpiredItems().forEach(itemGroup -> AddRecord(itemGroup,product)));
    }

    // Adder
    private void AddRecord(ItemGroup itemGroup, Product product){
        records.add(formatAsRecord(itemGroup, product));
    }

    // Print methods
    private static String formatAsRecord(ItemGroup itemGroup, Product product){
        return String.format("%-10d %-15d %-20s %-20s", itemGroup.getId(),product.getId(), product.getName(), itemGroup.getExpiration().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
    }

    @Override
    protected String headerRow() {
        return String.format("%-10s %-15s %-20s %-20s","ItemGroup ID","Product PID","Product Name","Expiration");
    }

    @Override
    public String getType() {
        return "Invalids";
    }
}
