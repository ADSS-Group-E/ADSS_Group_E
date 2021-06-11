package PresentationLayer.Supplier.DataTransferObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.time.LocalDateTime;

public class OrderDTO extends DataTransferObject {
    private final LocalDateTime date;
    private final boolean periodicDelivery;
    private final boolean needsDelivery;

    public OrderDTO(LocalDateTime date, boolean periodicDelivery, boolean needsDelivery) {
        super(-1);
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
    }

    public OrderDTO(int id, LocalDateTime date, boolean periodicDelivery, boolean needsDelivery) {
        super(id);
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
    }

    public boolean getPeriodicDelivery() {
        return periodicDelivery;
    }

    public boolean getNeedsDelivery() {
        return needsDelivery;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setId(int id) { this.id = id; }
}
