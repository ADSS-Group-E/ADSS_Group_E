package PresentationLayer.Supplier.DataTransferObjects;

import BusinessLayer.Supplier.DomainObjects.SupplierItemGroup;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.time.LocalDateTime;

public class SupplierItemGroupDTO extends DataTransferObject {
    private int supplierProductId;
    private int quantity;
    private final LocalDateTime expiration;

    public SupplierItemGroupDTO(int id, int supplierProductId, int quantity, LocalDateTime expiration) {
        super(id);
        this.supplierProductId = supplierProductId;
        this.quantity = quantity;
        this.expiration = expiration;
    }

    public SupplierItemGroupDTO(SupplierItemGroup other) {
        super(other.getId());
        this.quantity = other.getQuantity();
        this.expiration = other.getExpiration();
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public int getSupplierProductId() {
        return supplierProductId;
    }

    public void setSupplierProductId(int supplierProductId) {
        this.supplierProductId = supplierProductId;
    }
}
