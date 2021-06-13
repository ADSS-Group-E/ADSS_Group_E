package PresentationLayer.Supplier.DataTransferObjects;

import java.util.ArrayList;

public class OrderDTO {
    private int id;
    private final String date;
    private final int periodicDelivery;
    private final int needsDelivery;
    private ArrayList<SupplierItemDTO> orderItems;
    private int weight;
    private boolean isDelivered;

    public OrderDTO(String date, int periodicDelivery, int needsDelivery, ArrayList<SupplierItemDTO> orderItems, int weight) {
        this.id = -1;
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
        this.orderItems = orderItems;
        this.weight = weight;
        this.isDelivered = false;
    }

    public OrderDTO(int id, String date, int periodicDelivery, int needsDelivery, ArrayList<SupplierItemDTO> orderItems, int weight) {
        this.id = id;
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
        this.orderItems = orderItems;
        this.weight = weight;
        this.isDelivered = false;
    }

    public OrderDTO(int id, String date, int periodicDelivery, int needsDelivery, ArrayList<SupplierItemDTO> orderItems, int weight, boolean isDelivered) {
        this.id = id;
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
        this.orderItems = orderItems;
        this.weight = weight;
        this.isDelivered = isDelivered;
    }

    public int getId() {
        return id;
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

    public int getWeight() { return weight; }

    public boolean getIsDelivered() { return isDelivered; }
}
