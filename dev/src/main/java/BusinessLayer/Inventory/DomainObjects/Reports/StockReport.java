package BusinessLayer.Inventory.DomainObjects.Reports;

import BusinessLayer.Inventory.DomainObjects.Product;

import java.util.ArrayList;

/**
 * This class extends the "Report" class, and represent the stock report.
 * Each Stock report has an RID (which stands for Report ID), and local date-time of the created report.
 * It is used to track products that are about to run out of stock, so that the super can be prepared as needed.
 */

public class StockReport extends Report {

    public StockReport(int rid, ArrayList<Product> products) {
        super(rid);
        this.products = products;
        products.forEach(this::AddRecord);
    }

    private void AddRecord(Product product){
        records.add(formatAsRecord(product));
    }

    private static String formatAsRecord(Product product){
        return String.format("%-10d %-20s %-15d %-15d %-15d %-15d",product.getId(),product.getName(), product.getAmountInStore(), product.getAmountInStorage(),product.getAmountInStore()+product.getAmountInStorage(),product.getMinAmount());
    }

    @Override
    protected String headerRow() {
        return String.format("%-10s %-20s %-15s %-15s %-15s %-15s","PID","Name","In Store","In Storage", "Total Stock", "Min Needed");
    }

    @Override
    public String getType() {
        return "Stock";
    }


}
