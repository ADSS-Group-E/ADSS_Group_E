package BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryController {
    private HashMap<Integer,Category> categories;

    public CategoryController() {
        this.categories = new HashMap<>();
    }

    public Category getCategory(int cid){
        return categories.get(cid);
    }

    public void addCategory(Category category) {
        if (categories.containsKey(category.getCid())) {
            throw new IllegalArgumentException("Category cid already exist");
        } else {
            categories.put(category.getCid(), category);
        }
    }

    public void addCategory(int cid, String name) {
        if (categories.containsKey(cid)) {
            throw new IllegalArgumentException("Category cid already exist");
        } else {
            categories.put(cid, new Category(cid, name));
        }
    }

    public ArrayList getList() {
        ArrayList<Category> list = new ArrayList<Category>(categories.values());
        return list;
    }

    public void removeCategory (int cid) {
        if (categories.containsKey(cid)) {
            categories.remove(cid);
        }
        else {
            throw new IllegalArgumentException("Product pid is not exist");
        }
    }
}
