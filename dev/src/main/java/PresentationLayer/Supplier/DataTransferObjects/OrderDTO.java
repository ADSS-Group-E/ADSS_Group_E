package PresentationLayer.Supplier.DataTransferObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

public class OrderDTO extends DataTransferObject {
    private final String date;
    private final int periodicDelivery;
    private final int needsDelivery;

    public OrderDTO(String date, int periodicDelivery, int needsDelivery) {
        super(-1);
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
    }

    public OrderDTO(int id, String date, int periodicDelivery, int needsDelivery) {
        super(id);
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
    }

    public int getPeriodicDelivery() {
        return periodicDelivery;
    }

    public int getNeedsDelivery() {
        return needsDelivery;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) { this.id = id; }
}
