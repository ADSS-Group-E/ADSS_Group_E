package PresentationLayer.Inventory.DataTransferObjects;

import BusinessLayer.Inventory.Category;

/**
 * This class represents the CategoryDTO.
 * A DTO is an object that is used to encapsulate data and send it from one subsystem of an application to another.
 */

public class CategoryDTO extends DataTransferObject{
    private final int cid;
    private final String name;
    private final int superCategoryId;

    // Constructors
    public CategoryDTO(int cid, String name) {
        this(cid,name,-1);
    }

    public CategoryDTO(int cid, String name, int superCategoryId) {
        super(cid);
        this.cid = cid;
        this.name = name;
        this.superCategoryId = superCategoryId;
    }

    public CategoryDTO (Category other) {
        super(other.getId());
        this.cid = other.getId();
        this.name = other.getName();
        if (other.getSuperCategory()==null){
            this.superCategoryId = -1;
        }
        else{
            this.superCategoryId = other.getSuperCategory().getId();
        }
    }

    // Getters
    public int getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public Integer getSuperCategoryId() {
        if (superCategoryId == -1)
            return null;
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
