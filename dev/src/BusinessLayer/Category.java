package BusinessLayer;

import PresentationLayer.CategoryDTO;

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
