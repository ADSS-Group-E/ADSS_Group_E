package PresentationLayer.Supplier.DataTransferObjects;

import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.time.LocalDateTime;

public class OrderDTO extends DataTransferObject {
    private final int companyNumber;
    private final LocalDateTime date;
    private final boolean periodicDelivery;
    private final boolean needsDelivery;

    public OrderDTO(int companyNumber, LocalDateTime date, boolean periodicDelivery, boolean needsDelivery) {
        super(-1);
        this.companyNumber = companyNumber;
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
    }

    public OrderDTO(int id, int companyNumber, LocalDateTime date, boolean periodicDelivery, boolean needsDelivery) {
        super(id);
        this.companyNumber = companyNumber;
        this.date = date;
        this.periodicDelivery = periodicDelivery;
        this.needsDelivery = needsDelivery;
    }

    public int getCompanyNumber() {
        return companyNumber;
    }

    public boolean isPeriodicDelivery() {
        return periodicDelivery;
    }

    public boolean isNeedsDelivery() {
        return needsDelivery;
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
