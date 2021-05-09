package PresentationLayer.Inventory.DataTransferObjects;

import BusinessLayer.Inventory.Category;

import java.time.LocalDateTime;

/**
 * This class represents the ItemDTO.
 * A DTO is an object that is used to encapsulate data and send it from one subsystem of an application to another.
 */

public class ItemDTO implements DataTransferObject{
    private final int id;
    private final LocalDateTime expiration;

    // Constructor
    public ItemDTO(int id, LocalDateTime expiration) {
        this.id = id;
        this.expiration = expiration;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }
}
