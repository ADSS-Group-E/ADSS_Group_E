package InventoryModule.BusinessLayer;

/**
 * This class represents the category of the product.
 * Each product belongs to a category, sub category, and optional super category.
 * Each category has a unique "CID" (which stands for category ID), and name.
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
