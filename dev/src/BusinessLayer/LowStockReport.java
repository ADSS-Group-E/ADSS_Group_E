package BusinessLayer;

import java.util.ArrayList;

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
