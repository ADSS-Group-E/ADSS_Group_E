package BusinessLayer.Inventory;

import BusinessLayer.Inventory.Controllers.DiscountController;
import BusinessLayer.Inventory.Discount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiscountControllerTest {

    private DiscountController discountController;

    @BeforeEach
    public void setUp() {
        discountController = new DiscountController();
    }
    @Test
    void addDiscount() {
        assertNull(discountController.getDiscount(1));
        Discount discount = Discount.DiscountForBuying(1,"Test Spring Discount", 0.1,
                LocalDateTime.of(2021,4,1,16,0),
                LocalDateTime.of(2021,5,1,16,0),
                new ArrayList<>());
        discountController.addDiscount(discount);
        assertEquals(discount,discountController.getDiscount(1));
    }

    @Test
    void removeDiscount() {
        assertNull(discountController.getDiscount(1));
        Discount discount = Discount.DiscountForBuying(1,"Test Spring Discount", 0.1,
                LocalDateTime.of(2021,4,1,16,0),
                LocalDateTime.of(2021,5,1,16,0),
                new ArrayList<>());
        discountController.addDiscount(discount);
        assertEquals(discount,discountController.getDiscount(1));

        discountController.removeDiscount(1);
        assertNull(discountController.getDiscount(1));
    }
}