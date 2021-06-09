package PresentationLayer.Inventory.DataTransferObjects;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.ItemGroup;

import java.time.LocalDateTime;

/**
 * This class represents the ItemGroupDTO.
 * A DTO is an object that is used to encapsulate data and send it from one subsystem of an application to another.
 */

public class ItemGroupDTO extends DataTransferObject{
    public ItemGroupDTO(ItemGroup other) {
        super(other.getId());
        this.quantity = other.getQuantity();
        this.priceBoughtAt = other.getPriceBoughtAt();
        this.expiration = other.getExpiration();
    }

    public enum Location{
        STORE,
        STORAGE
    }
    private int pid;
    private Location location;
    private int quantity;
    private double priceBoughtAt;
    private final LocalDateTime expiration;

    // Constructor
    public ItemGroupDTO(int id, int pid, Location location, int quantity, double priceBoughtAt, LocalDateTime expiration) {
        super(id);
        this.pid = pid;
        this.location = location;
        this.quantity = quantity;
        this.priceBoughtAt = priceBoughtAt;
        this.expiration = expiration;
    }

    public int getPid() {
        return pid;
    }

    public Location getLocation() {
        return location;
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

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
