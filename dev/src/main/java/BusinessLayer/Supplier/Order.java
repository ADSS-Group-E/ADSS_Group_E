package BusinessLayer.Supplier;

import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    ArrayList<String> getOrderItemsToString(ArrayList<SupplierItemDTO> orderItems) {
        ArrayList<String> retItems = new ArrayList<>();
        for (SupplierItemDTO i : orderItems) {
            retItems.add(String.format("ID: %d\nName: %s\nPrice: %d\nQuantity: %d\nSupplier CN: %s",i.getId(), i.getName(), i.getPrice(), i.getQuantity(), i.getSupplierCN()));
        }
        return retItems;
    }

    int getPrice(OrderDTO order) {
        int sum = 0;
        for (SupplierItemDTO supplierItemDTO : order.getOrderItems()) {
            sum += supplierItemDTO.getQuantity() * supplierItemDTO.getPrice();
        }
        return sum;
    }
}

