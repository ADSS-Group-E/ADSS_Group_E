package PresentationLayer.Supplier.DataTransferObjects;

import BusinessLayer.Supplier.DomainObjects.OrderItemGroup;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.time.LocalDateTime;

public class OrderItemGroupDTO extends DataTransferObject {
    private int pid;
    private int orderId;
    private int quantity;
    private final double priceBoughtAt;
    private final LocalDateTime expiration;

    public OrderItemGroupDTO(int id, int pid, int orderId, int quantity, double priceBoughtAt, LocalDateTime expiration) {
        super(id);
        this.pid = pid;
        this.orderId = orderId;
        this.quantity = quantity;
        this.priceBoughtAt = priceBoughtAt;
        this.expiration = expiration;
    }

    public OrderItemGroupDTO(OrderItemGroup other) {
        super(other.getId());
        this.pid = other.getPid();
        this.quantity = other.getQuantity();
        this.priceBoughtAt = other.getPriceBoughtAt();
        this.expiration = other.getExpiration();
    }

    public int getPid() {
        return pid;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public double getPriceBoughtAt() {
        return priceBoughtAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
