package BusinessLayer.Supplier.DomainObjects;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;
import PresentationLayer.Inventory.DataTransferObjects.ItemGroupDTO;
import PresentationLayer.Supplier.DataTransferObjects.OrderItemGroupDTO;

import java.time.LocalDateTime;

public class OrderItemGroup {
    private int id; // Autoincrement?
    // Product ID
    private final int pid;
    private int quantity;
    private final double priceBoughtAt;
    private final LocalDateTime expiration;

    public OrderItemGroup(int pid, int quantity, double priceBoughtAt, LocalDateTime expiration) {
        this.pid = pid;
        this.quantity = quantity;
        this.priceBoughtAt = priceBoughtAt;
        this.expiration = expiration;
    }

    public OrderItemGroup(OrderItemGroupDTO other) {
        this.id = other.getId();
        this.pid = other.getPid();
        this.quantity = other.getQuantity();
        this.priceBoughtAt = other.getPriceBoughtAt();
        this.expiration = other.getExpiration();
    }

    public int getId() {
        return id;
    }

    public int getPid() {
        return pid;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPriceBoughtAt() {
        return priceBoughtAt;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setId(int id){
        this.id = id;
    }

}
