package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.Product;
import DataAccessLayer.Inventory.DBConnection;
import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the product controller.
 * All the products are in a Hash Map that contains the Product ID as the key and the rest of the fields as the value.
 */
public class ProductController {
    private final HashMap<Integer, Product> products;
    private final ProductDAO productDAO;
    private final CategoryController cCont;
    // Indicates all products have already been loaded so don't need to call selectAll again
    private boolean loadedAll = false;

    public ProductController(CategoryController cCont) {
        this.products = new HashMap<>();
        productDAO = new ProductDAO("jdbc:sqlite:module.db");
        this.cCont = cCont;
    }

    // Getter
    public Product getProduct(int pid){
        if (products.containsKey(pid))
            return products.get(pid);
        else{
            // Not in business layer, try to lazy load
            ProductDTO productDTO = productDAO.get(pid);
            Category category = cCont.getCategory(productDTO.getCategoryId());
            Product product = new Product(productDTO, category);
            products.put(pid,product);
            return product;
        }
    }

    // Adders
    public void addProduct(Product product){
        productDAO.insert(new ProductDTO(product));
        products.put(product.getPid(),product);
    }

    // Remover
    public void removeProduct (int pid) {

        if (products.containsKey(pid)) {
            products.remove(pid);
        }
        else {
            throw new IllegalArgumentException("Product pid does not exist");
        }
    }

    // More getters
    public ArrayList<Product> getList() {
        if (loadedAll)
            return new ArrayList<>(products.values());
        // Load not yet loaded products from database
        ArrayList<ProductDTO> productDTOs = productDAO.selectAll();
        for (ProductDTO productDTO:
             productDTOs) {
            if (!products.containsKey(productDTO.getPid())){
                Category category = cCont.getCategory(productDTO.getCategoryId());
                Product product = new Product(productDTO, category);
                products.put(productDTO.getPid(),product);
            }
        }
        loadedAll = true;
        return new ArrayList<>(products.values());
    }

    public ArrayList<Product> getAllProductsOfCategory(Category category){
        ArrayList<Product> productsOfCategory = new ArrayList<>();
        ArrayList<Product> allProducts = getList();
        allProducts.forEach((product)->{
            if(product.isInCategory(category)){
                productsOfCategory.add(product);
            }
        });
        return productsOfCategory;
    }
}