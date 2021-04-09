package BusinessLayer;

import PresentationLayer.CategoryDTO;

/**
 * This class represents the category of the product.
 * Each product belong to category, sub category, and sub sub category.
 * Each category has a "cid" (stand for category ID), and a name.
 */
public class Category {
    private int cid;
    private String name;
    private Category superCategory;

    public Category(int cid, String name) {
        this.cid = cid;
        this.name = name;
        this.superCategory = null;
    }

    public Category(int cid, String name, Category superCategory) {
        this.cid = cid;
        this.name = name;
        this.superCategory = superCategory;
    }

    // Getters:
    public int getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public Category getSuperCategory() {
        return superCategory;
    }
}
