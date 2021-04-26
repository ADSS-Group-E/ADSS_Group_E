package BusinessLayer.Inventory;

import java.time.LocalDateTime;

/**
 * This class represents specific items of each product that exists.
 * Each item has an ID, and date-time of expiration.
 */
public class Item {
    private final int id;
    private final LocalDateTime expiration;

    public int getId() {
        return id;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public Item(int id, LocalDateTime expiration) {
        this.id = id;
        this.expiration = expiration;
    }

    public boolean isExpired(){
        return (expiration.isBefore(LocalDateTime.now()));
    }
}
