package PresentationLayer.Inventory.DataTransferObjects;

import BusinessLayer.Inventory.Category;

/**
 * This class represents the CategoryDTO.
 * A DTO is an object that is used to encapsulate data and send it from one subsystem of an application to another.
 */

public class CategoryDTO {
    private final int cid;
    private final String name;
    private final int superCategoryId;

    // Constructors
    public CategoryDTO(int cid, String name) {
        this(cid,name,-1);
    }

    public CategoryDTO(int cid, String name, int superCategoryId) {
        this.cid = cid;
        this.name = name;
        this.superCategoryId = superCategoryId;
    }

    public CategoryDTO (Category other) {
        this.cid = other.getCid();
        this.name = other.getName();
        if (other.getSuperCategory()==null){
            this.superCategoryId = -1;
        }
        else{
            this.superCategoryId = other.getSuperCategory().getCid();
        }
    }

    // Getters
    public int getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public int getSuperCategoryId() {
        return superCategoryId;
    }

    // Print
    public String toString() {
        String s =  "CID:                " + cid + "\n" +
                    "Name:               " + name + "\n";
        if (superCategoryId != -1) {
            return s +
                    "Super-category ID:  " + superCategoryId;
        }
        return s;
    }
}
