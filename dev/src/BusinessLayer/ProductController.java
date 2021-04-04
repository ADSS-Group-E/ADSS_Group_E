package BusinessLayer;

import java.util.HashMap;

public class ProductController {
    private HashMap<Integer,Product> products;

    public Product getProduct(int pid){
        return products.get(pid);
    }

    public void addProduct(int pid, String name, String storageLocation, String storeLocation, int amountInStorage, int amountInStore, String manufacturer, double buyingPrice, double sellingPrice, int minAmount){
        products.put(pid, new Product(pid,name,storageLocation,storeLocation,amountInStorage,amountInStore,manufacturer,buyingPrice,sellingPrice,minAmount));
    }
}
