package BusinessLayer.Supplier;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order extends DomainObject {
    private final String date;
    private final int periodicDelivery;
    private final int needsDelivery;
    private ArrayList<OrderItemGroup> orderItems;

    public Order(int id, String date, int periodicDelivery, int needsDelivery) {
        super(id);
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
        orderItems= new ArrayList<>();
    }

    public Order(OrderDTO other){
        super(other.getId());
        this.date = other.getDate();
        this.periodicDelivery = other.getPeriodicDelivery();
        this.needsDelivery = other.getNeedsDelivery();
    }

    public String getDate() {
        return date;
    }

    public int getPeriodicDelivery() {
        return periodicDelivery;
    }

    public int getNeedsDelivery() {
        return needsDelivery;
    }

    public ArrayList<OrderItemGroup> getOrderItems() {
        return orderItems;
    }

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

