package PresentationLayer;

import BusinessLayer.Category;

public class CategoryDTO {
    private int cid;
    private String name;
    private Category superCategory = null;

    public CategoryDTO(int cid, String name) {
        this.cid = cid;
        this.name = name;
    }

    public CategoryDTO(int cid, String name, Category superCategory) {
        this.cid = cid;
        this.name = name;
        this.superCategory = superCategory;
    }

    public CategoryDTO (Category other) {
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

    public String toString() {
        String s = "CID:                " + cid + "\n" +
                   "Name:               " + name + "\n";
        if (superCategory != null) {
            return s += "Super category: " + superCategory;
        }
        return s;
    }
}
