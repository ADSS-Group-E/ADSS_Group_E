package BusinessLayer;

import PresentationLayer.CategoryDTO;

public class Category {
    private int cid;
    private String name;
    private Category superCategory = null;

    public Category(int cid, String name) {
        this.cid = cid;
        this.name = name;
    }

    public Category(int cid, String name, Category superCategory) {
        this.cid = cid;
        this.name = name;
        this.superCategory = superCategory;
    }

    public Category(CategoryDTO other) {
        this.cid = other.getCid();
        this.name = other.getName();
        this.superCategory = other.getSuperCategory();
    }

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
