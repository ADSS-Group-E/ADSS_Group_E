package BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductController {
    private HashMap<Integer,Product> products;

    public ProductController() {
        this.products = new HashMap<>();
    }

    public Product getProduct(int pid){
        return products.get(pid);
    }

    public void addProduct(Product product){
        if (products.containsKey(product.getPid())) {
            throw new IllegalArgumentException("Product pid already exist");
        } else {
            products.put(product.getPid(), product);
        }
    }

    public void addProduct(int pid, String name, String storageLocation, String storeLocation, int amountInStorage, int amountInStore, String manufacturer, double buyingPrice, double sellingPrice, int minAmount, Category category, Category subCategory, Category subSubCategory) {
        if (products.containsKey(pid)) {
            throw new IllegalArgumentException("Product pid already exist");
        } else {
            products.put(pid, new Product(pid, name, storageLocation, storeLocation, amountInStorage, amountInStore, manufacturer, buyingPrice, sellingPrice, minAmount, category, subCategory, subSubCategory));
        }
    }

    public void removeProduct (int pid) {
        if (products.containsKey(pid)) {
            products.remove(pid);
        }
        else {
            throw new IllegalArgumentException("Product pid is not exist");
        }
    }

    public boolean isExist(int pid) {
        return products.containsKey(pid);
    }

    public ArrayList getList() {
        ArrayList<Product> list = new ArrayList<Product>(products.values());
        return list;
    }
}
// check, remove, list
