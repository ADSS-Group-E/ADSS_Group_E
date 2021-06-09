package BusinessLayer.Inventory.DomainObjects;

import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name) && Objects.equals(superCategory, category.superCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, superCategory);
    }
}
