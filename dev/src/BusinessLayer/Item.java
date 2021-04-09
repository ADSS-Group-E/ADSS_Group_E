package BusinessLayer;

import java.time.LocalDateTime;

/**
 * This class represents specific item of each product that exist.
 * Each item has an id, and date time expiration.
 */
public class Item {
    private int id;
    private LocalDateTime expiration;

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
