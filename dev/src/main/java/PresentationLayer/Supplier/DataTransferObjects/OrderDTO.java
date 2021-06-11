package PresentationLayer.Supplier.DataTransferObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.util.ArrayList;

public class OrderDTO extends DataTransferObject {
    private final String date;
    private final int periodicDelivery;
    private final int needsDelivery;
    private ArrayList<SupplierItemDTO> orderItems;

    public OrderDTO(String date, int periodicDelivery, int needsDelivery, ArrayList<SupplierItemDTO> orderItems) {
        super(-1);
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
        this.orderItems = orderItems;
    }

    public OrderDTO(int id, String date, int periodicDelivery, int needsDelivery, ArrayList<SupplierItemDTO> orderItems) {
        super(id);
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
        this.orderItems = orderItems;
    }

    public int getPeriodicDelivery() {
        return periodicDelivery;
    }

    public int getNeedsDelivery() {
        return needsDelivery;
    }

    public ArrayList<SupplierItemDTO> getOrderItems() {
        return orderItems;
    }

    public String getDate() {
        return date;
    }

    public void setOrderItems(ArrayList<SupplierItemDTO> items) {
        this.orderItems = items;
    }

    public void setId(int id) { this.id = id; }
}
