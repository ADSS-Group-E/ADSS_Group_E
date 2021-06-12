package BusinessLayer.Supplier.DomainObjects;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import DataAccessLayer.Supplier.DataAccessObjects.OrderItemGroupDAO;
import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.OrderItemGroupDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Order extends DomainObject {
    private final int companyNumber;
    private final LocalDateTime date;
    private final boolean periodicDelivery;
    private final boolean needsDelivery;
    private Supplier supplier;

    private HashMap<Integer, OrderItemGroup> orderItems;

    public Order(int id, int companyNumber, LocalDateTime date, boolean periodicDelivery, boolean needsDelivery) {
        super(id);
        this.companyNumber = companyNumber;
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
        orderItems= new HashMap<>();
    }

    public Order(OrderDTO other){
        super(other.getId());
        this.companyNumber = other.getCompanyNumber();
        this.date = other.getDate();
        this.periodicDelivery = other.getPeriodicDelivery();
        this.needsDelivery = other.getNeedsDelivery();
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean getPeriodicDelivery() {
        return periodicDelivery;
    }

    public boolean getNeedsDelivery() {
        return needsDelivery;
    }

    public HashMap<Integer, OrderItemGroup> getOrderItems() {
        return orderItems;
    }

    public ArrayList<String> getOrderItemsToString(ArrayList<SupplierItemDTO> orderItems) {
        ArrayList<String> retItems = new ArrayList<>();
        for (SupplierItemDTO i : orderItems) {
            retItems.add(String.format("ID: %d\nName: %s\nPrice: %d\nQuantity: %d\nSupplier CN: %s",i.getId(), i.getName(), i.getPrice(), i.getQuantity(), i.getSupplierCN()));
        }
        return retItems;
    }


    // Calculates price based on sum of all items in the order
    public int getPrice() {
        int sum = 0;
        for (OrderItemGroup orderItemGroup : orderItems.values()) {
            sum += orderItemGroup.getQuantity() * orderItemGroup.getPriceBoughtAt();
        }
        return sum;
    }



    public void loadItems(HashMap<Integer, OrderItemGroup> orderItems) {
        this.orderItems = orderItems;
    }
}

