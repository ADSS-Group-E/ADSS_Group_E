package InventoryModule.BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the product controller.
 * All the products are in a Hash Map that contains the Product ID as the key and the rest of the fields as the value.
 */
public class ProductController {
    private final HashMap<Integer,Product> products;

    public ProductController() {
        this.products = new HashMap<>();
    }

    // Getter
    public Product getProduct(int pid){
        return products.get(pid);
    }

    // Adders
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
            products.put(pid, new Product(pid, name, storageLocation, storeLocation, manufacturer, buyingPrice, sellingPrice, minAmount, category, subCategory, subSubCategory));
        }
    }

    // Remover
    public void removeProduct (int pid) {
        if (products.containsKey(pid)) {
            products.remove(pid);
        }
        else {
            throw new IllegalArgumentException("Product pid is not exist");
        }
    }

    // Checks if the Product ID already exists
    public boolean isExist(int pid) {
        return products.containsKey(pid);
    }

    // More getters
    public ArrayList<Product> getList() {
        ArrayList<Product> list = new ArrayList<Product>(products.values());
        return list;
    }

    public ArrayList<Product> getAllProductsOfCategory(Category category){
        ArrayList<Product> productsOfCategory = new ArrayList<>();
        products.values().forEach((product)->{
            if(product.isInCategory(category)){
                productsOfCategory.add(product);
            }
        });
        return productsOfCategory;
    }
}