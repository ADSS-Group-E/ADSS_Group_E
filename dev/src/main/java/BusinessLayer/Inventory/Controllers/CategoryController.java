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
    private boolean loadedAll = false;

    public CategoryController() {
        this.categories = new HashMap<>();
        categoryDAO = new CategoryDAO();
    }

    // Getters
    public Category getCategory(int cid){
        if (categories.containsKey(cid)){
            return categories.get(cid);
        }
        else{
            // Not in business layer, try to lazy load
            CategoryDTO categoryDTO = categoryDAO.get(cid);
            if (categoryDTO == null){
                System.err.println( "Category id " + cid + " does not exist.");
                return null;
            }

            Category category = new Category(categoryDTO);
            categories.put(category.getCid(),category);
            return category;
        }
    }

    public ArrayList<Category> getList() {
        if (loadedAll)
            return new ArrayList<>(categories.values());
        // Load not yet loaded products from database
        ArrayList<CategoryDTO> categoryDTOS = categoryDAO.selectAll();
        for (CategoryDTO categoryDTO:
                categoryDTOS) {
            if (!categories.containsKey(categoryDTO.getCid())){
                Category category = new Category(categoryDTO);
                categories.put(category.getCid(),category);
            }
        }
        loadedAll = true;
        return new ArrayList<>(categories.values());
    }

    /**
     * Add a new category by object to the hash map
     * @param category to add
     */
    public void addCategory(Category category) {
        categoryDAO.insert(new CategoryDTO(category));
        categories.put(category.getCid(),category);
    }

    /**
     * Add a new category by cid and name to the hash map
     * @param cid of the category
     * @param name of the category
     */
    public void addCategory(int cid, String name) {
        Category category = new Category(cid, name);
        categoryDAO.insert(new CategoryDTO(category));
        categories.put(category.getCid(),category);
    }

    /**
     * Remove category by cid
     * @param cid the cid of the category to be removed
     */
    public void removeCategory (int cid) {
        categoryDAO.delete(cid);
        categories.remove(cid);
    }
}
