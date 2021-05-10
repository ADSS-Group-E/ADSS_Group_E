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

    public ProductController(CategoryController cCont) {
        DBConnection db = () -> {
            // SQLite connection string
            Connection c = null;

            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:module.db");
                c.setAutoCommit(false);
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("Opened database successfully");
            return c;
        };
        this.products = new HashMap<>();
        productDAO = new ProductDAO(db); // TODO decide what to do about db
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