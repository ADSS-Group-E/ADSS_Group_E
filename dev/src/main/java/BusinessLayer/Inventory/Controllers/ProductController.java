package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.Product;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the product controller.
 * All the products are in a Hash Map that contains the Product ID as the key and the rest of the fields as the value.
 */
public class ProductController {
    private final HashMap<Integer, Product> products;

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

    // Remover
    public void removeProduct (int pid) {
        if (products.containsKey(pid)) {
            products.remove(pid);
        }
        else {
            throw new IllegalArgumentException("Product pid is not exist");
        }
    }

    // More getters
    public ArrayList<Product> getList() {
        return new ArrayList<>(products.values());
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