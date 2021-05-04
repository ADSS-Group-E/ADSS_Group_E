package PresentationLayer.Supplier.DataTransferObjects;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class OrderDTO {
    private final int id;
    private final ZonedDateTime date;
    private final int periodicDelivery;
    private final int needsDelivery;
    private final ArrayList<SupplierItemDTO> orderItems;

    public OrderDTO(int id, ZonedDateTime date, int periodicDelivery, int needsDelivery, ArrayList<SupplierItemDTO> orderItems) {
        this.id = id;
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
        this.orderItems = orderItems;
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

    public ZonedDateTime getDate() {
        return date;
    }
}
