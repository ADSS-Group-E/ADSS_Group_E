package BusinessLayer;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class StockReport extends Report {

    public StockReport(int rid, ArrayList<Product> products) {
        super(rid);

        products.forEach(this::AddRecord);
    }

    private void AddRecord(Product product){
        records.add(formatAsRecord(product));
    }

    private static String formatAsRecord(Product product){
        return String.format("%-10d %-20s %-10d %-10d",product.getPid(),product.getName(), product.getAmountInStore(), product.getAmountInStorage());
    }

    @Override
    protected String headerRow() {
        return String.format("%-10s %-20s %-10s %-10s","PID","Name","In Store","In Storage");
    }

    @Override
    public String getType() {
        return "Stock";
    }


}
