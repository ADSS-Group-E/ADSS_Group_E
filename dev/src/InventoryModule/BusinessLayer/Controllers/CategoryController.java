package InventoryModule.BusinessLayer.Controllers;

import InventoryModule.BusinessLayer.Category;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the category controller.
 * All the categories are in a Hash Map that contains the CIDs as the keys and the rest of the fields as the values.
 */
public class CategoryController {
    private final HashMap<Integer, Category> categories;

    public CategoryController() {
        this.categories = new HashMap<>();
    }

    // Getters
    public Category getCategory(int cid){
        return categories.get(cid);
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
