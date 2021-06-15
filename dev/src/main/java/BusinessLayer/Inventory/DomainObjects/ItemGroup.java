package BusinessLayer.Inventory.DomainObjects;

import PresentationLayer.Inventory.DataTransferObjects.ItemGroupDTO;

import java.time.LocalDateTime;

/**
 * This class represents specific items of each product that exists.
 * Each item has an ID, and date-time of expiration.
 */
public class ItemGroup {
    private int id;
    private int quantity;
    private double priceBoughtAt;
    private final LocalDateTime expiration;

    public ItemGroup(ItemGroupDTO other) {
        this.id = other.getId();
        this.quantity = other.getQuantity();
        this.priceBoughtAt = other.getPriceBoughtAt();
        this.expiration = other.getExpiration();
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceBoughtAt() {
        return priceBoughtAt;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public ItemGroup(int quantity, double priceBoughtAt, LocalDateTime expiration) {
        this.quantity = quantity;
        this.priceBoughtAt = priceBoughtAt;
        this.expiration = expiration;
    }

    public void setId(int id){
        this.id = id;
    }

    public boolean isExpired(){
        return (expiration.isBefore(LocalDateTime.now()));
    }
}
