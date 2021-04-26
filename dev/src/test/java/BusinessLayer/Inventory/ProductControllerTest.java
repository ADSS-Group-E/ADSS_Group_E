package BusinessLayer.Inventory;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.Controllers.ProductController;

import BusinessLayer.Inventory.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    private ProductController productController;

    @BeforeEach
    public void setUp() {
        productController = new ProductController();
    }

    @Test
    void addProduct() {
        assertNull(productController.getProduct(1));
        Product product = new Product(1, "Test Juice", "AB01","B13", "Test Company",10.5, 10.1, 5,new Category(1,"Juice"));
        productController.addProduct(product);
        assertEquals(product,productController.getProduct(1));
    }

    @Test
    void removeProduct() {
        assertNull(productController.getProduct(1));
        Product product = new Product(1, "Test Juice", "AB01","B13", "Test Company",10.5, 10.1, 5,new Category(1,"Juice"));
        productController.addProduct(product);
        assertEquals(product,productController.getProduct(1));
        productController.removeProduct(1);
        assertNull(productController.getProduct(1));
    }
}