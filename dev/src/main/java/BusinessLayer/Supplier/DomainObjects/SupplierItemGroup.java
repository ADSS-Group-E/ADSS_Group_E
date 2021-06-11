package BusinessLayer.Supplier.DomainObjects;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import PresentationLayer.Inventory.DataTransferObjects.ItemGroupDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemGroupDTO;

import java.time.LocalDateTime;

public class SupplierItemGroup extends DomainObject {
    private int quantity;
    private final LocalDateTime expiration;

    public SupplierItemGroup(int quantity, LocalDateTime expiration) {
        super(-1);
        this.quantity = quantity;
        this.expiration = expiration;
    }

    public SupplierItemGroup(SupplierItemGroupDTO other) {
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

    public void setId(int id){
        this.id = id;
    }


}
