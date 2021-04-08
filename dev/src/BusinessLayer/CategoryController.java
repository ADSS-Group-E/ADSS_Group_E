package BusinessLayer;

import java.util.HashMap;

public class CategoryController {
    private HashMap<Integer,Category> categories;

    public CategoryController() {
        this.categories = new HashMap<>();
    }

    public Category getCategory(int cid){
        return categories.get(cid);
    }
}
