package BusinessLayer.Supplier;

import PresentationLayer.Supplier.ServiceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SupplierControllerTest {

    private ServiceController controller;
    @BeforeEach
    void setUp() {
        controller = ServiceController.getInstance();
        controller.initialize();
    }

    @Test
    void getSuppliersInfo() {
        ArrayList<String[]> info = controller.getSuppliersInfo();
        ArrayList<String[]> realInfo = new ArrayList<>();
        realInfo.add(new String[]{"Amazon", 10 + "", "Cheque", "8145441/24"});
        realInfo.add(new String[]{"Google", 54 + "", "Paypal", "15144/455"});
        assertArrayEquals(info.get(0), realInfo.get(0));
        assertArrayEquals(info.get(1), realInfo.get(1));
    }

    @Test
    void register() {
        ArrayList<String[]> supplierItems = new ArrayList<>();
        supplierItems.add(new String[]{"Polaroid", 9000 + "", 500 + "", 1041 + ""});
        ArrayList<String[]> contacts = new ArrayList<>();
        contacts.add(new String[]{"Tzahi", "tzahi@apple.com"});
        HashMap<Integer, Integer> discounts = new HashMap<>();
        discounts.put(15000, 20);
        controller.register("Apple", 941, "Apple Pay", "49913/14", supplierItems, contacts, 10, 5000, discounts);
        String[] appleinfo = controller.getSuppliersInfo().get(2);
        assertEquals("Apple", appleinfo[0]);
        assertEquals(appleinfo[1], 941 + "");
        assertEquals("Apple Pay", appleinfo[2]);
        assertEquals("49913/14", appleinfo[3]);
        QuantityWriter writer = controller.getQuantityWriter(2);
        assertEquals(writer, new QuantityWriter(discounts, 10, 5000));
    }

    @Test
    void registerWithoutQuantityWriter() {
        ArrayList<String[]> supplierItems = new ArrayList<>();
        supplierItems.add(new String[]{"Polaroid", 9000 + "", 500 + "", 1041 + ""});
        ArrayList<String[]> contacts = new ArrayList<>();
        contacts.add(new String[]{"Tzahi", "tzahi@apple.com"});
        controller.register("Apple", 941, "Apple Pay", "49913/14", supplierItems, contacts);
        String[] appleinfo = controller.getSuppliersInfo().get(2);
        assertEquals("Apple", appleinfo[0]);
        assertEquals(appleinfo[1], 941 + "");
        assertEquals("Apple Pay", appleinfo[2]);
        assertEquals("49913/14", appleinfo[3]);
    }

    @Test
    void createOrder() {
        ArrayList<String[]> supItems = new ArrayList<>();
        supItems.add(new String[]{"Dog", 3000 + "", 10 + "", 5041 + ""});
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("Dog", 3000, 10, 5041));
        controller.createOrder(0, true, true, supItems);
        assertTrue(controller.getOrderFromSupplier(0, 1).isConstantDelivery());
        assertTrue(controller.getOrderFromSupplier(0, 1).isNeedsDelivery());
        assertEquals(controller.getOrderFromSupplier(0, 1).getOrderItems(), items);
    }

    @Test
    void getSpecificItem() {
        String[] item = controller.getSpecificItem(1,0);
        assertEquals(item[0], "Hot Dog");
        assertEquals(item[1], 10 + "");
        assertEquals(item[2], 1500 + "");
        assertEquals(item[3], 100 + "");
    }

    @Test
    void updateSellerItemQuantity() {
        assertEquals(controller.getSpecificItem(0, 1)[2], 500 + "");
        controller.updateSellerItemQuantity(0, 1, 60);//ordering 60, remaining 440
        assertEquals(controller.getSpecificItem(0, 1)[2], 440 + "");
    }

    @Test
    void updateOrderItemQuantity() {
        assertEquals(controller.getOrderFromSupplier(1, 0).getOrderItems().get(0).getQuantity(), 10);
        controller.updateOrderItemQuantity(1,0,0,1);
        assertEquals(controller.getOrderFromSupplier(1, 0).getOrderItems().get(0).getQuantity(), 1);
    }

    @Test
    void deleteCostumerItem() {
        assertEquals(controller.getOrderFromSupplier(1, 0).getOrderItems().get(0).getName(), "Dog");
        controller.deleteOrderItem(1,0,0);
        assertEquals(controller.getOrderFromSupplier(1, 0).getOrderItems().size(), 0);
    }

    @Test
    void contains() {
        String[] supplierName = new String[]{"Amazon", 10 + "", "Cheque", "8145441/24"};
        String[] supplierName2 = new String[]{"Apple", 5555 + "", "Apple Pay", "49913/14"};
        ArrayList<String[]> supplierInfo = controller.getSuppliersInfo();
        assertTrue(controller.contains(supplierName, supplierInfo));
        assertFalse(controller.contains(supplierName2, supplierInfo));
    }

    @Test
    void getMaxDiscount() {
        assertEquals(controller.getMaxDiscount(), 100);
    }
}