package InventoryModule.BusinessLayer;

import java.util.ArrayList;

/**
 * This class extends the "Report" class, and represent the low stock report.
 * Each Stock report has an RID (which stands for Report ID), and local date-time of the created report.
 * It is used to track products that are about to run out of stock, so that the super can be prepared as needed.
 */

public class LowStockReport extends StockReport{
    public LowStockReport(int rid, ArrayList<Product> products) {
        super(rid, getLowStockProducts(products));
    }

    private static ArrayList<Product> getLowStockProducts(ArrayList<Product> products){
        ArrayList<Product> lowStockProducts = new ArrayList<>();
        products.forEach((product) -> {
            if (product.getMinAmount()>(product.getAmountInStorage()+product.getAmountInStore())){
                lowStockProducts.add(product);
            }
        });
        return lowStockProducts;
    }

    @Override
    public String getType() {
        return "Low Stock";
    }
}
