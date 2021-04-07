package BusinessLayer;

import java.time.LocalDateTime;

public class Item {
    private int id;
    private LocalDateTime expiration;

    public Item(int id, LocalDateTime expiration) {
        this.id = id;
        this.expiration = expiration;
    }
}
