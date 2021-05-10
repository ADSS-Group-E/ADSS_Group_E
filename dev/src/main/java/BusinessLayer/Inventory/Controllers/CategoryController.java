package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.Product;
import DataAccessLayer.Inventory.DBConnection;
import DataAccessLayer.Inventory.DataAccessObjects.CategoryDAO;
import DataAccessLayer.Inventory.DataAccessObjects.ProductDAO;
import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the category controller.
 * All the categories are in a Hash Map that contains the CIDs as the keys and the rest of the fields as the values.
 */
public class CategoryController {
    private final HashMap<Integer, Category> categories;
    private final CategoryDAO categoryDAO;

    public CategoryController() {
        this.categories = new HashMap<>();

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
        categoryDAO = new CategoryDAO(db);
    }

    // Getters
    public Category getCategory(int cid){
        if (categories.containsKey(cid)){
            return categories.get(cid);
        }
        else{
            // Not in business layer, try to lazy load
            CategoryDTO categoryDTO = categoryDAO.get(cid);
            Category category = new Category(categoryDTO);
            categories.put(category.getCid(),category);
            return category;
        }
    }

    public ArrayList<Category> getList() {
        return new ArrayList<>(categories.values());
    }

    /**
     * Add a new category by object to the hash map
     * @param category to add
     */
    public void addCategory(Category category) {
        if (categories.containsKey(category.getCid())) {
            throw new IllegalArgumentException("Category cid already exist");
        } else {
            categories.put(category.getCid(), category);
        }
    }

    /**
     * Add a new category by cid and name to the hash map
     * @param cid of the category
     * @param name of the category
     */
    public void addCategory(int cid, String name) {
        if (categories.containsKey(cid)) {
            throw new IllegalArgumentException("Category cid already exist");
        } else {
            categories.put(cid, new Category(cid, name));
        }
    }

    /**
     * Remove category by cid
     * @param cid the cid of the category to be removed
     */
    public void removeCategory (int cid) {
        if (categories.containsKey(cid)) {
            categories.remove(cid);
        }
        else {
            throw new IllegalArgumentException("Category ID does not exist");
        }
    }
}
