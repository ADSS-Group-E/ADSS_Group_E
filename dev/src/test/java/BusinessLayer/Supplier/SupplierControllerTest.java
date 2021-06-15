package BusinessLayer.Supplier;

import PresentationLayer.Supplier.DataTransferObjects.DiscountStepDTO;
import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.QuantityWriterDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;
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
        controller = new ServiceController(new SupplierController());
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
        supplierItems.add(new String[]{11 + "", "Polaroid", 9000 + "", 500 + "", 1041 + ""});
        ArrayList<String[]> contacts = new ArrayList<>();
        contacts.add(new String[]{"Tzahi", "tzahi@apple.com"});
        HashMap<Integer, Integer> discounts = new HashMap<>();
        discounts.put(15000, 20);
        DiscountStepDTO stepDTO = new DiscountStepDTO(1, 15000, 20, 1);
        ArrayList<DiscountStepDTO> list = new ArrayList<>();
        ArrayList<Integer> supplyDays = new ArrayList<>();
        supplyDays.add(2);
        controller.register("Google", 50, "Google Pay", "69913/14", supplierItems, contacts, 10, 5000, discounts, supplyDays);
        String[] appleinfo = controller.getSuppliersInfo().get(0);
        assertEquals("Google", appleinfo[1]);
        assertEquals(appleinfo[0], 50 + "");
        assertEquals("Google Pay", appleinfo[2]);
        assertEquals("69913/14", appleinfo[3]);
        QuantityWriterDTO writer = controller.getQuantityWriter(50);
        assertEquals(writer.getId(), 1);
        assertEquals(writer.getCompanyNumber(), 50);
        assertEquals(writer.getRegularCostumerDiscount(), 10);
        assertEquals(writer.getMinPriceDiscount(), 5000);
    }

    @Test
    void registerWithoutQuantityWriter() {
        ArrayList<Integer> supplyDays = new ArrayList<>();
        supplyDays.add(2);
        ArrayList<String[]> supplierItems = new ArrayList<>();
        supplierItems.add(new String[]{10 + "", "Polaroid", 9000 + "", 500 + "", 1041 + ""});
        ArrayList<String[]> contacts = new ArrayList<>();
        contacts.add(new String[]{"Tzahi", "tzahi@apple.com"});
        controller.register("Apple", 941, "Apple Pay", "49913/14", supplierItems, contacts, supplyDays);
        String[] appleinfo = controller.getSuppliersInfo().get(0);
        assertEquals("Apple", appleinfo[1]);
        assertEquals(appleinfo[0], 941 + "");
        assertEquals("Apple Pay", appleinfo[2]);
        assertEquals("49913/14", appleinfo[3]);
    }

    @Test
    void createOrder() {
        ArrayList<String[]> supItems = new ArrayList<>();
        supItems.add(new String[]{10 + "", "Polaroid", 100 + "", 500 + "", 1041 + ""});
        ArrayList<SupplierItemDTO> items = new ArrayList<>();
        items.add(new SupplierItemDTO(10, "Polaroid", 100, 500, "1041", 941));
        controller.createOrder(50, true, true, supItems, 50, 1);
        OrderDTO order = controller.getOrderFromSupplier(1);
        assertEquals(1, order.getPeriodicDelivery());
        assertEquals(1, order.getNeedsDelivery());
    }

    @Test
    void getSpecificItem() {
        String[] item = controller.getSpecificItem(50,11);
        assertEquals(item[0], "11");
        assertEquals(item[1], "Polaroid");
        assertEquals(item[2], 9000 + "");
        assertEquals(item[3], 500 + "");
        assertEquals(item[4], 1041 + "");
    }

    @Test
    void updateSellerItemQuantity() {
        String[] item = controller.getSpecificItem(50, 11);
        assertEquals(item[3], 500 + "");
        controller.updateSellerItemQuantity(50, 11, 60);//ordering 60, remaining 440
        assertEquals(item[2], 440 + "");
    }

    @Test
    void updateOrderItemQuantity() {
        assertEquals(controller.getOrderFromSupplier(1).getOrderItems().get(0).getQuantity(), 10);
        controller.updateOrderItemQuantity(1, 1,0);
        assertEquals(controller.getOrderFromSupplier(1).getOrderItems().get(0).getQuantity(), 1);
    }

    @Test
    void deleteCostumerItem() {
        assertEquals(controller.getOrderFromSupplier(1).getOrderItems().get(0).getName(), "Dog");
        controller.deleteOrderItem(1, 1);
        assertEquals(controller.getOrderFromSupplier(1).getOrderItems().size(), 0);
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