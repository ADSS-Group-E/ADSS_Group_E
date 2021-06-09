package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.Controllers.CategoryController;
import BusinessLayer.Inventory.Controllers.DiscountController;
import BusinessLayer.Inventory.Controllers.ProductController;
import BusinessLayer.Inventory.DomainObjects.Discount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiscountControllerTest {

    private CategoryController categoryController;
    private DiscountController discountController;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        categoryController = new CategoryController();
        productController = new ProductController(categoryController);
        discountController = new DiscountController(productController);
    }
    @Test
    void addDiscount() {
        assertNull(discountController.getDiscount(1));
        Discount discount = new Discount(1,"Test Spring Discount", 0.1,
                LocalDateTime.of(2021,4,1,16,0),
                LocalDateTime.of(2021,5,1,16,0));
        //discountController.addDiscount(discount);
        assertEquals(discount,discountController.getDiscount(1));
    }

    @Test
    void removeDiscount() {
        assertNull(discountController.getDiscount(1));
        Discount discount =  new Discount(1,"Test Spring Discount", 0.1,
                LocalDateTime.of(2021,4,1,16,0),
                LocalDateTime.of(2021,5,1,16,0));
        //discountController.addDiscount(discount);
        assertEquals(discount,discountController.getDiscount(1));

        discountController.removeDiscount(1);
        assertNull(discountController.getDiscount(1));
    }
}