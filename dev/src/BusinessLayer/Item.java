package BusinessLayer;

import java.time.LocalDateTime;

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
