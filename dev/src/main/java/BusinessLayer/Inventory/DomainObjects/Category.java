package BusinessLayer.Inventory.DomainObjects;

import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;

/**
 * This class represents the category of the product.
 * Each product belongs to a category, sub category, and optional super category.
 * Each category has a unique "CID" (which stands for category ID), and name.
 */
public class Category extends DomainObject{
    private final String name;
    private final Category superCategory;

    public Category(int cid, String name) {
        super(cid);
        this.name = name;
        this.superCategory = null;
    }

    public Category(CategoryDTO categoryDTO) {
        super(categoryDTO.getCid());
        this.name = categoryDTO.getName();
        this.superCategory = null;
    }

    public Category(int cid, String name, Category superCategory) {
        super(cid);
        this.name = name;
        this.superCategory = superCategory;
    }

    // Getters:

    public String getName() {
        return name;
    }

    public Category getSuperCategory() {
        return superCategory;
    }
}
