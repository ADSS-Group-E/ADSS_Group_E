package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.DomainObjects.Category;
import BusinessLayer.Inventory.DomainObjects.DomainObject;
import DataAccessLayer.Inventory.DataAccessObjects.CategoryDAO;
import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.util.ArrayList;

/**
 * This class represents the category controller.
 * All the categories are in a Hash Map that contains the CIDs as the keys and the rest of the fields as the values.
 */
public class CategoryController extends DomainController{

    public CategoryController() {
        super(new CategoryDAO());
    }

    // Getters
    public Category getCategory(int cid){
        return (Category) get(cid);
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        ArrayList<DomainObject> domainObjects = getList();

        for (DomainObject domainObject:
                domainObjects) {
            categories.add((Category) domainObject);
        }

        return categories;
    }

    /**
     * Add a new category by object to the hash map
     * @param category to add
     */
    public void addCategory(Category category) {
        add(category);
    }

    /**
     * Remove category by cid
     * @param cid the cid of the category to be removed
     */
    public void removeCategory (int cid) {
        remove(cid);
    }

    @Override
    protected DomainObject buildDomainObjectFromDto(DataTransferObject dataTransferObject) {
        return new Category((CategoryDTO) dataTransferObject);
    }

    @Override
    protected DataTransferObject buildDtoFromDomainObject(DomainObject domainObject) {
        return new CategoryDTO((Category) domainObject);
    }
}
