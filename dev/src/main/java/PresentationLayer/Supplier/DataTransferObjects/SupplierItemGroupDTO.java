package PresentationLayer.Supplier.DataTransferObjects;

import BusinessLayer.Supplier.DomainObjects.SupplierItemGroup;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.time.LocalDateTime;

public class SupplierItemGroupDTO extends DataTransferObject {
    private int pid;
    private int companyNumber;
    private int quantity;
    private final LocalDateTime expiration;

    public SupplierItemGroupDTO(int id,int pid,int companyNumber, int quantity, LocalDateTime expiration) {
        super(id);
        this.pid = pid;
        this.companyNumber = companyNumber;
        this.quantity = quantity;
        this.expiration = expiration;
    }

    public SupplierItemGroupDTO(SupplierItemGroup other) {
        super(other.getId());
        this.quantity = other.getQuantity();
        this.expiration = other.getExpiration();
    }

    public int getPid() {
        return pid;
    }

    public int getCompanyNumber() {
        return companyNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }
}
