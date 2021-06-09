package BusinessLayer.Inventory;

import BusinessLayer.Inventory.DomainObjects.Category;
import BusinessLayer.Inventory.Controllers.CategoryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {

    private CategoryController categoryController;

    @BeforeEach
    public void setUp() {
        categoryController = new CategoryController();
    }

    @Test
    void addCategory() {
        assertNull(categoryController.getCategory(1));
        Category category = new Category(1,"Juice");
        categoryController.addCategory(category);
        assertEquals(category,categoryController.getCategory(1));
    }

    @Test
    void removeCategory() {
        assertNull(categoryController.getCategory(1));
        Category category = new Category(1,"Juice");
        categoryController.addCategory(category);
        assertEquals(category,categoryController.getCategory(1));

        categoryController.removeCategory(1);
        assertNull(categoryController.getCategory(1));
    }
}